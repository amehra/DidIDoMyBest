'use strict';

angular.module('didIDoMyBestApp').controller('QuestionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Question', 'User',
        function($scope, $stateParams, $modalInstance, entity, Question, User) {

        $scope.question = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Question.get({id : id}, function(result) {
                $scope.question = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('didIDoMyBestApp:questionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.question.id != null) {
                Question.update($scope.question, onSaveFinished);
            } else {
                Question.save($scope.question, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
