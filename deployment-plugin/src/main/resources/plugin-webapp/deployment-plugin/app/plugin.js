ngDefine('cockpit.plugin.deployment-plugin', function(module) {

  var DeploymentResource = function ($resource, Uri) {
    return $resource(Uri.appUri(
        'engine://engine/:engine/deployment/:deploymentId',
        {deploymentId: '@id', before: '@before', after: '@after'}
    ));
  };
  module.factory('DeploymentResource', DeploymentResource);

  var ProcessInstanceCountResource = function ($resource, Uri) {
    return $resource(Uri.appUri(
        'engine://engine/:engine/process-instance/count',
        {processDefinitionId: '@processDefinitionId'}
    ));
  };
  module.factory('ProcessInstanceCountResource', ProcessInstanceCountResource);

  var ProcessDefinitionDeploymentResource = function($resource, Uri) {
    return $resource(Uri.appUri(
        'engine://engine/:engine/process-definition', 
        {deploymentId: '@deploymentId'}
    ));
  };
  module.factory('ProcessDefinitionDeploymentResource', ProcessDefinitionDeploymentResource);

  var DashboardController = function($scope, $http, Uri, 
      DeploymentResource, ProcessDefinitionDeploymentResource, ProcessInstanceCountResource, $modal) {

    // prepare the datepicker
    var today = new Date();
    console.log(today);
    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];
    var deployedAfter = new Date();
    deployedAfter.setDate(today.getDate()-30);
    console.log(deployedAfter);
    $scope.deployedAfter = deployedAfter;
    $scope.deployedBefore = today;
    $scope.open = function($event, opened) {
      $event.preventDefault();
      $event.stopPropagation();
      $scope[opened] = true;    	  
    };

    $http.get(Uri.appUri("engine://engine/:engine/deployment/count"))
    .success(function(data) {
      $scope.deploymentCounter = data;
    });

    $scope.deployments = null;

    $scope.findDeployments = function() {
      var deployedAfter = new Date($scope.deployedAfter);
      deployedAfter.setUTCHours(0, 0, 0);
      var deployedBefore = new Date($scope.deployedBefore);
      deployedBefore.setUTCHours(23, 59, 59);
      console.log("dates for search: " + deployedAfter.toISOString() + " " + deployedBefore.toISOString());
      var deployments = DeploymentResource.query(
          {before: deployedBefore.toISOString(),
           after: deployedAfter.toISOString(),
           sortBy: "deploymentTime",
           sortOrder: "asc"
        });
      console.log(deployments);
      deployments.$promise.then(function () {
        deployments.forEach(function(deployment, i) {
          console.log(deployment.id + " " + deployment.name);
          deployment.runningInstances = 0;
          ProcessDefinitionDeploymentResource.query({deploymentId: deployment.id})
          .$promise.then(function (response) {
            console.log(response);
            var processDefinitions = response;
            deployment.processDefinitions = processDefinitions;
            processDefinitions.forEach(function(processDefinition, i) {
              console.log(processDefinition);
              ProcessInstanceCountResource.get({processDefinitionId: processDefinition.id})
              .$promise.then(function (processInstanceCount) {
                console.log(processInstanceCount);
                processDefinition.runningInstances = processInstanceCount.count;
                deployment.runningInstances = deployment.runningInstances + processInstanceCount.count;
              }); 
            });
          });
        });  
      });
      $scope.deployments = deployments;
    };
    
    $scope.openDeleteDeploymentDialog = function () {
      var deployment = this.deployment;
      console.log("open delete deployment dialog " + deployment.id);
      $modal.open({
        resolve: {
          deployment: function() { return deployment; },
        },
        controller: 'DeleteDeploymentController',
        templateUrl: require.toUrl(Uri.appUri('plugin://deployment-plugin/static/app/delete-deployment-dialog.html'))
      });
    }; 

  };


  DashboardController.$inject = ["$scope", "$http", "Uri", 
                                 "DeploymentResource", 
                                 "ProcessDefinitionDeploymentResource", 
                                 "ProcessInstanceCountResource",
                                 "$modal"];


  var Configuration = function Configuration(ViewsProvider) {

    ViewsProvider.registerDefaultView('cockpit.dashboard', {
      id: 'deployment-plugin',
      label: 'Deployments',
      url: 'plugin://deployment-plugin/static/app/dashboard.html',
      controller: DashboardController,

      // make sure we have a higher priority than the default plugin
      priority: 1
    });
  };

  Configuration.$inject = ['ViewsProvider'];

  module.config(Configuration);

  module.controller('DeleteDeploymentController', ['$scope', '$location', 'Notifications', 'DeploymentResource', '$modalInstance', 'deployment', 
                                           function($scope,   $location,   Notifications,   DeploymentResource,   $modalInstance,   deployment) {

    console.log("in delete controller " + deployment.id);
    
    $scope.deployment = deployment;

    var BEFORE_DELETE = 'beforeDelete',
    PERFORM_DELETE = 'performDelete',
    DELETE_SUCCESS = 'deleteSuccess',
    DELETE_FAILED = 'deleteFailed',
    LOADING_FAILED = 'loadingFailed';

    $scope.status = BEFORE_DELETE;
    console.log("status is BEFORE_DELETE");

    $scope.$on('$routeChangeStart', function () {
      $modalInstance.close($scope.status);
    });

//    deployment.observe(function () {
//
//    });

    $scope.deleteDeployment = function () {
      $scope.status = PERFORM_DELETE;
      console.log($scope.deployment);
      console.log("delete deployment " + deployment + " with id " + deployment.id);
      $scope.deployment.$delete({deploymentId: deployment.id}, function () {
        
        // success
        console.log("deleted");
        $scope.status = DELETE_SUCCESS;
        Notifications.addMessage({'status': 'Deleted', 'message': 'The deployment was successfully deleted.'});

      }, function () {
        // failure
        console.log("not deleted: " + $scope.deployment);
        $scope.status = DELETE_FAILED;
        Notifications.addError({'status': 'Failed', 'message': 'The deployment could not be deleted.', 'exclusive': ['type']});
      });
    };

    $scope.close = function (status) {
      $modalInstance.close(status);

      // reload deployments
      if (status === DELETE_SUCCESS) {
//        $location.url('/process-definition/' + processInstance.definitionId);
//        $location.replace();
        console.log("refresh deployments");
      }
    };
  }]);  return module;

});
