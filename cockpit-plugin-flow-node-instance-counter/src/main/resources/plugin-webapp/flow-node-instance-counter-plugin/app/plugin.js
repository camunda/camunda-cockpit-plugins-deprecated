ngDefine('cockpit.plugin.flow-node-instance-counter-plugin', function(module) {

  var DashboardController = function($scope, $http, Uri) {

	var today = new Date();
	var previous = new Date().setDate(today.getDate() - 30);
    $http.get(Uri.appUri("engine://engine/:engine/history/activity-instance/count?startedAfter="
    		+ new Date(previous).toISOString()))
      .success(function(data) {
        $scope.activityInstanceCounts = data;
        $scope.previous = new Date(previous).toLocaleDateString();
      });
    $http.get(Uri.appUri("plugin://flow-node-instance-counter-plugin/:engine/flow-node-instance-count-per-month"))
    	.success(function(data) {
    		$scope.flowNodeInstancesPerMonth = data
		});
  };

  DashboardController.$inject = ["$scope", "$http", "Uri"];


  var Configuration = function Configuration(ViewsProvider) {

    ViewsProvider.registerDefaultView('cockpit.dashboard', {
      id: 'flow-node-instance-counter-plugin',
      label: 'Flow Node Instance Counter',
      url: 'plugin://flow-node-instance-counter-plugin/static/app/dashboard.html',
      controller: DashboardController,

      // make sure we have a higher priority than the default plugin
      priority: 12
    });
  };

  Configuration.$inject = ['ViewsProvider'];

  module.config(Configuration);

  return module;
});
