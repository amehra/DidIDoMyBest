'use strict';

angular.module('didIDoMyBestApp')
    .controller('DailyAnswersDetailController', function ($scope, $rootScope, $stateParams, entity, DailyAnswers, Question) {
        $scope.dailyAnswers = entity;
        $scope.load = function (id) {
            DailyAnswers.get({id: id}, function(result) {
                $scope.dailyAnswers = result;
            });
        };
        var unsubscribe = $rootScope.$on('didIDoMyBestApp:dailyAnswersUpdate', function(event, result) {
            $scope.dailyAnswers = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
