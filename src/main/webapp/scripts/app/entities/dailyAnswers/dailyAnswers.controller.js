'use strict';

angular.module('didIDoMyBestApp')
    .controller('DailyAnswersController', function ($scope, DailyAnswers, ParseLinks) {
        $scope.dailyAnswerss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DailyAnswers.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dailyAnswerss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DailyAnswers.get({id: id}, function(result) {
                $scope.dailyAnswers = result;
                $('#deleteDailyAnswersConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DailyAnswers.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDailyAnswersConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dailyAnswers = {
                value: null,
                notes: null,
                id: null
            };
        };
    });
