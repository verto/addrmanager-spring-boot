
var addrManager = angular.module('addrManager', ['ngRoute']);

addrManager.config(function($routeProvider, $httpProvider, $locationProvider) {

    $locationProvider.html5Mode(true);

    var path = function(url) { return "/app" + url; };

    $routeProvider
        .when(path(''), {
            templateUrl : '/addresses/index.html',
            controller  : 'ListAddressController',
            resolve: {
              response: function($http) {
                return $http.get('/api/addresses');
              }
            }
        })
        .when(path('/addresses/new'), {
          templateUrl : '/addresses/new.html',
          controller  : 'CreateAddressController'
        })
       .when(path('/addresses/:id'), {
           templateUrl : '/addresses/edit.html',
           controller  : 'EditAddressController',
           resolve: {
             response: function($http, $location) {
               return $http.get($location.path().replace("/app","/api"));
             }
           }
       });
});

