'use strict';

angular.module('didIDoMyBestApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


