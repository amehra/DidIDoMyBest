'use strict';

angular.module('didIDoMyBestApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('question', {
                parent: 'entity',
                url: '/questions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'didIDoMyBestApp.question.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/question/questions.html',
                        controller: 'QuestionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('question');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('question.detail', {
                parent: 'entity',
                url: '/question/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'didIDoMyBestApp.question.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/question/question-detail.html',
                        controller: 'QuestionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('question');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Question', function($stateParams, Question) {
                        return Question.get({id : $stateParams.id});
                    }]
                }
            })
            .state('question.new', {
                parent: 'question',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/question/question-dialog.html',
                        controller: 'QuestionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    question: null,
                                    dateTimeCreated: null,
                                    orderNumber: null,
                                    dateTimeDeleted: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('question', null, { reload: true });
                    }, function() {
                        $state.go('question');
                    })
                }]
            })
            .state('question.edit', {
                parent: 'question',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/question/question-dialog.html',
                        controller: 'QuestionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Question', function(Question) {
                                return Question.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('question', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
