/**
 * 
 */
var app = angular.module('qluser', []);
app.controller('control', function($scope, $http) {
	$scope.users = [];

	$scope.load = function() {
		$http.get('/api/users/all').then(function success(res) {
			$scope.users = res.data;
		});
	}
	
	$scope.xoa=function(id){
		var data={
				id:id,
				username:'abc',
				password:'abc',
				admin:false
			};
		$http.delete('/api/users',{
			data:JSON.stringify(data),
			headers:{'Content-Type': 'application/json;charset=utf-8'}
		}).then(function success(res){
			alert(res.data.message);
			if(res.data.error==0){
				$scope.load();
			}
		});
	}
})