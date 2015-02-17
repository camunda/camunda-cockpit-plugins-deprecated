ngDefine('cockpit.plugin.rest-client', function(module) {
	
  var RestClientController = function($scope, $http, Uri) {
  	$scope.restRequest = {url: "http://localhost:8080/engine-rest/engine", method: "GET"};
    $scope.executeRestRequest = function() {
    	 $http.post(Uri.appUri("plugin://rest-client/default"), $scope.restRequest)
         .success(function(data) {
                $scope.restResponse = data;
       });
    };

    $scope.queryMessages = function() {
    	 $http.get(Uri.appUri("plugin://rest-client/default/info/message"))
         .success(function(data) {
                $scope.messages = data;
       });
    };
    $scope.queryMessages();
    $scope.selectMessage = function(messageName) {
    	$scope.restRequest = {
    		url: "http://localhost:8080/engine-rest/engine/default/message",
    		method: "POST",
    		payload: JSON.stringify( {
    				messageName: messageName,
    				correlationKeys: {
               aVariable : {value : "aValue", type: "String"}
            },
            processVariables : {
               aVariable : {value : "aNewValue", type: "String"},
               anotherVariable : {value : true, type: "Boolean"}
            }
    			}, null, 4 )
    	};
    }    
    
    $scope.queryProcessDefinitions = function() {
    	 $http.get(Uri.appUri("engine://engine/default/process-definition?latest=true&sortBy=name&sortOrder=asc"))
         .success(function(data) {
                $scope.processDefinitions = data;
       });
    };
    $scope.queryProcessDefinitions();
    $scope.selectProcessDefiniton = function(processDefinition) {
    	$scope.restRequest = {
    		url: "http://localhost:8080/engine-rest/engine/default/process-definition/key/" + processDefinition.key + "/start",
    		method: "POST",
    		payload: JSON.stringify( {
    				businessKey: "myBusinessKey",    		
            variables : {
               aVariable : {value : "aNewValue", type: "String"},
               anotherVariable : {value : true, type: "Boolean"}
            }
    			}, null, 4 )
    	};
    }
   
  };

  RestClientController.$inject = ["$scope", "$http", "Uri"];

  var Configuration = function Configuration(ViewsProvider) {

    ViewsProvider.registerDefaultView('cockpit.dashboard', {
      id: 'rest-client',
      label: 'Rest Client',
      url: 'plugin://rest-client/static/app/index.html',
      controller: RestClientController,

      // make sure we have a higher priority than the default plugin
      priority: -10
    });
  };

  Configuration.$inject = ['ViewsProvider'];

  module.config(Configuration);

  return module;
});
