ngDefine('cockpit.plugin.flow-node-counter-plugin', function(module) {

  var DashboardController = function($scope, $http, Uri) {

	var today = new Date();
	var previous = new Date().setDate(today.getDate()-30);
    $http.get(Uri.appUri("engine://engine/default/history/activity-instance/count"
    		+ "?startedAfter=" + new Date(previous).toISOString()))
      .success(function(data) {
        $scope.activityInstanceCount = data;
        $scope.previous = new Date(previous).toLocaleDateString();
      });
    $http.get(Uri.appUri("plugin://flow-node-counter-plugin/default/flow-node-count-per-month"))
      .success(function(data) {
    	$scope.flowNodesPerMonth = data;
    })
  };

  DashboardController.$inject = ["$scope", "$http", "Uri"];


  var Configuration = function Configuration(ViewsProvider) {

    ViewsProvider.registerDefaultView('cockpit.dashboard', {
      id: 'flow-node-counter-plugin',
      label: 'Flow Node Counter',
      url: 'plugin://flow-node-counter-plugin/static/app/dashboard.html',
      controller: DashboardController,

      // make sure we have a higher priority than the default plugin
      priority: 12
    });
  };

  Configuration.$inject = ['ViewsProvider'];

  module.config(Configuration);

  return module;
});
