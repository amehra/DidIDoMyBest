'use strict';

angular.module('didIDoMyBestApp').controller('DailyAnswersDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DailyAnswers', 'Question',
        function($scope, $stateParams, $modalInstance, entity, DailyAnswers, Question) {

        $scope.dailyAnswers = entity;
        $scope.questions = Question.query();
        $scope.load = function(id) {
            DailyAnswers.get({id : id}, function(result) {
                $scope.dailyAnswers = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('didIDoMyBestApp:dailyAnswersUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.dailyAnswers.id != null) {
                DailyAnswers.update($scope.dailyAnswers, onSaveFinished);
            } else {
                DailyAnswers.save($scope.dailyAnswers, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
