ngDefine('cockpit.plugin.findByBusinessKey', function(module) {

    // define Angular resource for REST communication

    var HistoryResource = function ($resource, Uri) {
        return $resource('/engine-rest/history/process-instance',
            {processInstanceBusinessKeyLike: '@processInstanceBusinessKeyLike'},
            {query: { method: 'GET', isArray: true, params:{processInstanceBusinessKeyLike:'', sortBy: 'startTime', sortOrder: 'desc', maxResults: '50'}}
            });
    };
    module.factory('FindHistoryByBusinessKeyResource', HistoryResource);
    
    var ProcessDefinitionResource = function($resource, Uri) {
    	return $resource('/engine-rest/process-definition/:processDefinitionId',
    			{processDefinitionId: '@processDefinitionId'},
    			{query: {method: 'GET', isArray: false}})
    };
    module.factory('GetProcessDefinitionResource', ProcessDefinitionResource);

    // create controller to load data for HTML
    function FindByBusinessKeyController ($scope, FindHistoryByBusinessKeyResource, GetProcessDefinitionResource) {
        // input: processInstance


        $scope.historicProcessInstancesByBusinessKey = null;

        $scope.businessKey = null;

        $scope.findByBusinessKey = function() {
            FindHistoryByBusinessKeyResource.query({processInstanceBusinessKeyLike: $scope.businessKey}).$then(function(response) {
                $scope.historicProcessInstancesByBusinessKey = null;
                $scope.historicProcessInstancesByBusinessKey = response.data;
                
                angular.forEach($scope.historicProcessInstancesByBusinessKey, function(processInstance) {
                	var procDefId = processInstance.processDefinitionId;
                	GetProcessDefinitionResource.query({processDefinitionId: procDefId}).$then(function(response) {
                		processInstance.name = response.data.name;
                	})
                 });
            });
        };

    };
    module.controller('FindByBusinessKeyController', [ '$scope', 'FindHistoryByBusinessKeyResource','GetProcessDefinitionResource', FindByBusinessKeyController ]);


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
