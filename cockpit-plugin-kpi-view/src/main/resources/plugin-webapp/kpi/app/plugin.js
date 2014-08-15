ngDefine('cockpit.plugin.kpi', function(module) {

    // define Angular resource for REST communication

    var KPIResource = function ($resource, Uri) {
        return $resource('/camunda/api/cockpit/plugin/kpi/default/kpi',
            {},
            {getKPIs: { method: 'GET', isArray: true, params:{processKey: '', startDate: '', endDate: ''}}
            });
    };
    module.factory('KPIResource', KPIResource);

    // create controller to load data for HTML
    function ShowKPIController ($scope, KPIResource) {
        // input: processInstance

        $scope.KPIs = null;

        var date = new Date();
        $scope.KpiStartDate = new Date(date.getFullYear(), date.getMonth(), 1);
        $scope.KpiEndDate = new Date();

        $scope.showKPI = function() {
            KPIResource.getKPIs({processKey: $scope.processDefinition.key, startDate:$scope.KpiStartDate, endDate:$scope.KpiEndDate}).$then(function(response) {
                $scope.KPIs = null;
                $scope.KPIs = response.data;
            });

        };

    };
    module.controller('ShowKPIController', [ '$scope', 'KPIResource',ShowKPIController ]);


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
