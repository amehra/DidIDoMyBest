'use strict';

angular.module('didIDoMyBestApp')
    .controller('QuestionDetailController', function ($scope, $rootScope, $stateParams, entity, Question, User) {
        $scope.question = entity;
        $scope.load = function (id) {
            Question.get({id: id}, function(result) {
                $scope.question = result;
            });
        };
        var unsubscribe = $rootScope.$on('didIDoMyBestApp:questionUpdate', function(event, result) {
            $scope.question = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
