ngDefine('cockpit.plugin.kpi', function(module) {

    // define Angular resource for REST communication

    var KPIResource = function ($resource, Uri) {
        return $resource(Uri.appUri('plugin://kpi/:engine/kpi',
            {processKey: '@processKey', startDate: '@startDate', endDate: '@endDate'}));
    };
    module.factory('KPIResource', KPIResource);

    // create controller to load data for HTML
    function ShowKPIController ($scope, KPIResource) {
        // input: processInstance

        $scope.KPIs = null;

        var date = new Date();
        $scope.KpiStartDate = new Date(date.getFullYear(), date.getMonth(), 1).toISOString().slice(0, 10);
        $scope.KpiEndDate = new Date().toISOString().slice(0, 10);
        
        console.log("show kpis for " + $scope.KpiStartDate + " " +$scope.KpiEndDate + " " + $scope.processDefinition.key);
        $scope.showKPI = function() {
            KPIResource.query({processKey: $scope.processDefinition.key, startDate:$scope.KpiStartDate, endDate:$scope.KpiEndDate}).$promise.then(function(response) {
                console.log("response: " + response);
                $scope.KPIs = null;
                $scope.KPIs = response;
            });

        };
        console.log("kpi rest result: " + $scope.KPIs);

    };
    module.controller('ShowKPIController', [ '$scope', 'KPIResource', ShowKPIController ]);


    // register Plugin
    var Configuration = function PluginConfiguration(ViewsProvider) {

        ViewsProvider.registerDefaultView('cockpit.processDefinition.runtime.tab', {
            id: 'kpi',
            label: 'KPIs',
            url: 'plugin://kpi/static/app/panel.html',
            controller: 'ShowKPIController',
            priority: 20
        });
    };

    Configuration.$inject = ['ViewsProvider'];

    module.config(Configuration);
});
