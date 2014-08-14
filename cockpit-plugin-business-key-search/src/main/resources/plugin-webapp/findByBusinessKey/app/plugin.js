ngDefine('cockpit.plugin.findByBusinessKey', function(module) {

    // define Angular ressource for REST communication

    var HistoryResource = function ($resource, Uri) {
        return $resource('/engine-rest/history/process-instance',
            {processInstanceBusinessKeyLike: '@processInstanceBusinessKeyLike'},
            {query: { method: 'GET', isArray: true, params:{processInstanceBusinessKeyLike:'', sortBy: 'startTime', sortOrder: 'desc', maxResults: '50'}}
            });
    };
    module.factory('FindHistoryByBusinessKeyResource', HistoryResource);

    // create controller to load data for HTML
    function FindByBusinessKeyController ($scope, FindHistoryByBusinessKeyResource) {
        // input: processInstance


        $scope.historicProcessInstancesByBusinessKey = null;

        $scope.businessKey = null;

        $scope.findByBusinessKey = function() {
            FindHistoryByBusinessKeyResource.query({processInstanceBusinessKeyLike: $scope.businessKey}).$then(function(response) {
                $scope.historicProcessInstancesByBusinessKey = null;
                $scope.historicProcessInstancesByBusinessKey = response.data;
            });

        };

    };
    module.controller('FindByBusinessKeyController', [ '$scope', 'FindHistoryByBusinessKeyResource',FindByBusinessKeyController ]);


    // register Plugin
    var Configuration = function PluginConfiguration(ViewsProvider) {

        ViewsProvider.registerDefaultView('cockpit.dashboard', {
            id: 'findByBusinessKey',
            label: 'Find Process Instances by Business Key',
            url: 'plugin://findByBusinessKey/static/app/panel.html',
            controller: 'FindByBusinessKeyController',
            priority: 15
        });
    };

    Configuration.$inject = ['ViewsProvider'];

    module.config(Configuration);
});
