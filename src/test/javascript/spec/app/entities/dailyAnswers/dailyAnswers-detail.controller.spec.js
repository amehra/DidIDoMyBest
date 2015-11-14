'use strict';

describe('DailyAnswers Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDailyAnswers, MockQuestion;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDailyAnswers = jasmine.createSpy('MockDailyAnswers');
        MockQuestion = jasmine.createSpy('MockQuestion');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DailyAnswers': MockDailyAnswers,
            'Question': MockQuestion
        };
        createController = function() {
            $injector.get('$controller')("DailyAnswersDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'didIDoMyBestApp:dailyAnswersUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
