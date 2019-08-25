var app=angular.module('qlpost',[]);

app.controller('ctrl',function($scope,$http){
    $scope.listPost=[];
    $scope.loaded=true;

    $scope.capnhat=function(){
        $scope.loaded=false;
        var id=$scope.sl;
        if(id){
            $http.get('/admin/api/listpost/'+id).then(function (value) {
                $scope.loaded=true;
                console.log('data : '+value.data);
                $scope.listPost=value.data;
            });
        }
    }

    $scope.changeStatus=function(){

    }
})