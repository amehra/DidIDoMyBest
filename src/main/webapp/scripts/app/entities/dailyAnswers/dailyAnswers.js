'use strict';

angular.module('didIDoMyBestApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dailyAnswers', {
                parent: 'entity',
                url: '/dailyAnswerss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'didIDoMyBestApp.dailyAnswers.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dailyAnswers/dailyAnswerss.html',
                        controller: 'DailyAnswersController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dailyAnswers');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dailyAnswers.detail', {
                parent: 'entity',
                url: '/dailyAnswers/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'didIDoMyBestApp.dailyAnswers.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dailyAnswers/dailyAnswers-detail.html',
                        controller: 'DailyAnswersDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dailyAnswers');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DailyAnswers', function($stateParams, DailyAnswers) {
                        return DailyAnswers.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dailyAnswers.new', {
                parent: 'dailyAnswers',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dailyAnswers/dailyAnswers-dialog.html',
                        controller: 'DailyAnswersDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    value: null,
                                    notes: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dailyAnswers', null, { reload: true });
                    }, function() {
                        $state.go('dailyAnswers');
                    })
                }]
            })
            .state('dailyAnswers.edit', {
                parent: 'dailyAnswers',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dailyAnswers/dailyAnswers-dialog.html',
                        controller: 'DailyAnswersDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DailyAnswers', function(DailyAnswers) {
                                return DailyAnswers.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dailyAnswers', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
