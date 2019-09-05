/**
 * 
 */
var app = angular.module('qluser', []);
app.controller('control', function($scope, $http) {
	$scope.users = [];
	$scope.loaded=true;

	$scope.load = function() {
		$scope.loaded=false;
		$http.get('/api/users/all').then(function success(res) {
			$scope.users = res.data;
			$scope.loaded=true;
		});
	}
	
	$scope.xoa=function(id){
		var result=confirm("xac nhan xoa tai khoan ?");
		if(result){
			$scope.loaded=false;
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
				if(res.data.error==0){
					$scope.load();
				}
				$scope.loaded=true;
				alert(res.data.message);
			});
		}
	}
})