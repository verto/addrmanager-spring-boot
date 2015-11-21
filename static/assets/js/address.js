angular
  .module('addrManager')
  .controller('ListAddressController', function($scope, $http, $location, response) {
      $scope.addresses = response.data;

      $scope.destroy = function(address) {
        var success = function(data) { $location.path("/app/"); };
        $http.delete("/api/addresses/" + address.id).then(success);
      }
  })
  .controller('CreateAddressController', function($scope, $http, $location) {
      $scope.address = {};
      $scope.errors = [];

      $scope.save = function() {
        var success = function(data) { $location.path("/app"); };
        var error = function(response) {
            var errorsObject = response.data;
            $scope.errors = errorsObject.errors;
        };

        $http.post("/api/addresses", $scope.address).then(success, error);
      }
  })
  .controller('EditAddressController', function($scope, $http, $location, response) {
      $scope.address = response.data;
      $scope.errors = [];

      $scope.save = function() {
        var success = function(data) { $location.path("/app"); };
        var error = function(response) {
            var errorsObject = response.data;
            $scope.errors = errorsObject.errors;
        };

        $http.put("/api/addresses/" + $scope.address.id, $scope.address).then(success, error);
      }
  })
  .controller('ZipCodeController', function($scope, $http) {
      $scope.error = null;

      $scope.search = function(zipCode) {
          if (zipCode == null || zipCode == "") { return; }

          var success = function(response) {
              var data = response.data;
              $scope.$parent.address.zipCode = data.zipCode;
              $scope.$parent.address.street = data.street;
              $scope.$parent.address.neighborhood = data.neighborhood;
              $scope.$parent.address.city = data.city;
              $scope.$parent.address.state = data.state;
          };

          var error = function(response) {
              var type = response.status == 422 ? "warnning" : "negative";
              $scope.error = { type: type, message: response.data.errors[0].message };
              console.log($scope.error);
          };

          $http.get("/api/zipcodes/search?zipCode=" + zipCode).then(success, error);
      }
  });
