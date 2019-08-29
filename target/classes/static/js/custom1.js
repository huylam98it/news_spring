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
                $scope.listPost=value.data;
            });
        }
    }

    $scope.removePost=function(id){
        var isAction=confirm("Xac nhan xoa bai viet ?");
        if(isAction){
            $http.post('/admin/removepost',{id:id},{}).then(function (value) {
                alert(value.data.message);
                $scope.capnhat();
            });
        }
    }
})