define(['angular'], function(angular) {
  
  var ngModule = angular.module('cockpit.plugin.version-migration', []);
  
//  var ProcessDefinitionResource = function($resource, Uri) {
//    return $resource(Uri.appUri(
//        'engine://engine/:engine/process-definition/:processDefinitionId',
//        {processDefinitionId: '@id'}
//    ));
////    ,{get: {isarray: false}}
//  };
//  ngModule.factory('ProcessDefinitionResource', ProcessDefinitionResource);
  
  var VersionMigrationController = function($scope, $http, Uri) {

    console.log('Process instance id', $scope.processInstance.id);
    console.log('process definition id', $scope.processInstance.definitionId);

    $http.get(Uri.appUri("engine://engine/:engine/process-definition/"+$scope.processInstance.definitionId))
      .success(function(processDefinition) {
        $scope.processDefinition = processDefinition;
      });
    
    $scope.migrateVersion = function() {
      console.log('migrate the version to ' + $scope.newVersion + ' now.');
      var newVersion = {};
      newVersion.version = $scope.newVersion;
      console.log(newVersion);
      console.log($scope.processInstance.id);
      $http.post(Uri.appUri("plugin://version-migration/:engine/process-instance-migration/" + 
          $scope.processInstance.id), newVersion);
    }
  };

  VersionMigrationController.$inject = ["$scope", "$http", "Uri"];

  var Configuration = function Configuration(ViewsProvider) {

    ViewsProvider.registerDefaultView('cockpit.processInstance.runtime.tab', {
      id: 'version-migration',
      label: 'Version Migration',
      url: 'plugin://version-migration/static/app/dashboard.html',
      controller: VersionMigrationController,

      // make sure we have a higher priority than the default plugin
      priority: 1
    });
  };

  Configuration.$inject = ['ViewsProvider'];

  
  ngModule.config(Configuration);
  ngModule.controller('VersionMigrationController', [ '$scope' ]);

  return ngModule;
});
