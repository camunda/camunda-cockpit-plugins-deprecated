define(['angular'], function(angular) {

    // define Angular resource for REST communication
    var KPIResource = function ($resource, Uri) {
        return $resource(Uri.appUri('plugin://kpi/:engine/kpi',
            {processKey: '@processKey', startDate: '@startDate', endDate: '@endDate'}));
    };

    // create controller to load data for HTML
    function ShowKPIController ($scope, KPIResource) {
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

    var ngModule = angular.module('cockpit.plugin.kpi', []);

    ngModule.config(Configuration);
    ngModule.factory('KPIResource', KPIResource);
    ngModule.controller('ShowKPIController', [ '$scope', 'KPIResource', ShowKPIController ]);

    return ngModule;    
});
