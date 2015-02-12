ngDefine('cockpit.plugin.rest-client', function(module) {
	
  var RestClientController = function($scope, $http, Uri) {
  	$scope.restRequest = {url: "http://localhost:8080/engine-rest/engine/"};
    $scope.executeRestRequest = function() {
    	 $http.post(Uri.appUri("plugin://rest-client/default"), $scope.restRequest)
         .success(function(data) {
                $scope.restResponse = data;
       });
    };
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
