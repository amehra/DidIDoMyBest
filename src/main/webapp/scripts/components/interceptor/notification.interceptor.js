 'use strict';

angular.module('didIDoMyBestApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-didIDoMyBestApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-didIDoMyBestApp-params')});
                }
                return response;
            }
        };
    });
