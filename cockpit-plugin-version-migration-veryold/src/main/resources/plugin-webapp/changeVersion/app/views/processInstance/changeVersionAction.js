ngDefine('cockpit.plugin.changeVersion.views', ['require'], function(module, require) {

  var Controller = [
    '$scope',
    '$dialog',
  function($scope, $dialog) {

    $scope.openDialog = function (processInstance) {
      var dialog = $dialog.dialog({
        resolve: {
          processInstance: function() { return processInstance; }
        },
        controller: 'ChangeVersionController',
        templateUrl: require.toUrl('./change-pi-version-dialog.html')
      });

      dialog.open().then(function(result) {
        // dialog closed. YEA!
        if (result.status === 'SUCCESS') {
          $scope.processData.set('filter', angular.extend({}, $scope.filter));
        }
      });

    };

  }];


  var Configuration = [ 'ViewsProvider', function(ViewsProvider) {

    ViewsProvider.registerDefaultView('cockpit.processInstance.runtime.action', {
      id: 'change-process-definition-version',
      label: 'Change Process Instance Version',
      url: 'plugin://changeVersion/static/app/views/processInstance/change-pi-version.html',
      controller: Controller,
      priority: 25
    });
  }];

  module.config(Configuration);
});





