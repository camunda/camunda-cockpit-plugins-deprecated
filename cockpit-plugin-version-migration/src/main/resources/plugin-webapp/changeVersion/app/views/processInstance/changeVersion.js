ngDefine('cockpit.plugin.changeVersion.views', ['require'], function(module, require) {

  var Controller = [
    '$scope',
    'dialog',
    'Notifications',
    'ProcessInstanceResource',
    'processInstance',
  function($scope, dialog, Notifications, ProcessInstanceResource, processInstance) {

    var PERFORM = 'PERFORM'
        SUCCESS = 'SUCCESS',
        FAILED = 'FAILED';

    $scope.$on('$routeChangeStart', function () {
      dialog.close($scope.status);
    });

    $scope.incrementJobRetries = function () {
      $scope.status = PERFORM;

      JobDefinitionResource.setRetries({ 'id' : jobDefinition.id }, { 'retries' : 1 },

        function (response) {
          $scope.status = SUCCESS;
          // Notifications.addMessage({ 'status': 'Finished', 'message': 'Incrementing the number of retries finished successfully.', 'exclusive': true });
          Notifications.addMessage({ 'status': 'Finished', 'message': 'Incrementing the number of retries completed successfully.', 'exclusive': true });
        },

        function (error) {
          $scope.status = FAILED;
          Notifications.addError({ 'status': 'Finished', 'message': 'Incrementing the number of retries was not successful: ' + error.data.message, 'exclusive': true });
        }
      );
    }

    $scope.close = function (status) {
      var response = {
        status: status
      };

      dialog.close(response);
    };


  }];

  module.controller('ChangeVersionController', Controller);
});
