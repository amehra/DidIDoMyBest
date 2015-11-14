'use strict';

angular.module('didIDoMyBestApp')
    .factory('Question', function ($resource, DateUtils) {
        return $resource('api/questions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateTimeCreated = DateUtils.convertDateTimeFromServer(data.dateTimeCreated);
                    data.dateTimeDeleted = DateUtils.convertDateTimeFromServer(data.dateTimeDeleted);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
