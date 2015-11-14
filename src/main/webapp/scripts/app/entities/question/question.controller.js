'use strict';

angular.module('didIDoMyBestApp')
    .controller('QuestionController', function ($scope, Question, ParseLinks) {
        $scope.questions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Question.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.questions.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.questions = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Question.get({id: id}, function(result) {
                $scope.question = result;
                $('#deleteQuestionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Question.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteQuestionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.question = {
                question: null,
                dateTimeCreated: null,
                orderNumber: null,
                dateTimeDeleted: null,
                id: null
            };
        };
    });
