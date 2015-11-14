'use strict';

angular.module('didIDoMyBestApp')
    .factory('DailyAnswers', function ($resource, DateUtils) {
        return $resource('api/dailyAnswerss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
