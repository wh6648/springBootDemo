define(['angular', 'jquery', 'uiGrid'], function(angular, $) {
      'use strict';
      var ctrl = angular.module('controllers', ['ui.grid']);
      ctrl.controller('testCtrl', ['$scope', '$location', '$q', '$http', '$compile', function($scope, $location, $q, $http, $compile) {
                $scope.positionGridOptions = {
                  enableFiltering : false,
                  enableColumnResizing : false,
                  onRegisterApi : function(gridApi) {
                    $scope.gridApi = gridApi
                  },
                  columnDefs : [],
                  data : []
                };

                // login
                $scope.login = function() {
                  console.log("login");
                  var deferred = $q.defer();

                  var userName = $("#userName").val();
                  var userPass = $("#userPass").val();

                  var data = {};
                  data.name = userName;
                  data.password = userPass;
                  data.deviceToken = "00000000-54b3-e7c7-0000-000046bffd96";

                  $http({
                        method : 'post',
                        url : 'userInfo/login',
                        'data' : JSON.stringify(data)
                      }).success(function(data) {
                        deferred.resolve(data);
                      }).error(function(data) {
                        deferred.reject(data);
                      });
                  deferred.promise.then(function(data) {
                        $scope.getLoginInfo(data);

                        console.log('login 请求成功');
                      }, function(data) {
                        console.log('login 请求失败');
                      });
                }

                // saveUser
                $scope.saveUser = function() {
                  console.log("saveUser");
                  var deferred = $q.defer();

                  var userName = $("#userName").val();
                  var userPass = $("#userPass").val();
                  var positionId = $scope.position.selected;
                  var superiorId = $scope.superiorUser.selected;

                  if (positionId == null || positionId == "") {
                    $(".loginInfo").empty();
                    $(".loginInfo").append('<span class="label label-success">' + "错误 : 职务没有设定!" + '</span>');
                    return;
                  }

                  if (superiorId == null) {
                    superiorId = "";
                  }

                  var data = {};
                  data.name = userName;
                  data.password = userPass;
                  data.positionId = positionId;
                  data.superiorId = superiorId;

                  $http({
                        method : 'post',
                        url : 'userInfo/createUser',
                        'data' : JSON.stringify(data)
                      }).success(function(data) {
                        deferred.resolve(data);
                      }).error(function(data) {
                        deferred.reject(data);
                      });
                  deferred.promise.then(function(data) {
                        $scope.getLoginInfo(data);

                        console.log('saveUser 请求成功');
                      }, function(data) {
                        console.log('saveUser 请求失败');
                      });
                }

                // currentUser
                $scope.currentUser = function() {
                  console.log("currentUser");
                  var deferred = $q.defer();

                  var data = {};

                  $http({
                        method : 'post',
                        url : 'userInfo/currentUser',
                        'data' : JSON.stringify(data)
                      }).success(function(data) {
                        deferred.resolve(data);
                      }).error(function(data) {
                        deferred.reject(data);
                      });
                  deferred.promise.then(function(data) {
                        $scope.getLoginInfo(data);

                        console.log('currentUser 请求成功');
                      }, function(data) {
                        console.log('currentUser 请求失败');
                      });
                }

                $scope.getAllPositionInfo = function() {
                  console.log("getAllPositionInfo");
                  var deferred = $q.defer();

                  var data = {};

                  $http({
                        method : 'post',
                        url : 'positionInfo/getAllPositionInfo',
                        'data' : JSON.stringify(data)
                      }).success(function(data) {
                        deferred.resolve(data);
                      }).error(function(data) {
                        deferred.reject(data);
                      });
                  deferred.promise.then(function(data) {
                        if (data.error == false && data.retMap) {
                          $scope.position = {};
                          $scope.position.all = data.retMap.allPosition;

                          $scope.positionGridOptions.columnDefs = [{
                                field : 'name',
                                enableColumnResizing : true,
                                enableFiltering : false,
                                enableSorting : false,
                                cellTemplate : '<div class="ui-grid-cell-contents ng-binding ng-scope" title="{{row.entity.id}}">{{row.entity.name}}</div>',
                                displayName : "职务名"
                              }];
                          $scope.positionGridOptions.data = data.retMap.allPosition;
                        }
                        console.log('getAllPositionInfo 请求成功');
                      }, function(data) {
                        console.log('getAllPositionInfo 请求失败');
                      });
                }

                $scope.getAllSuperiorUserInfo = function() {
                  console.log("getAllSuperiorUserInfo");
                  var deferred = $q.defer();

                  var data = {};

                  $http({
                        method : 'post',
                        url : 'userInfo/getAllSuperiorUserInfo',
                        'data' : JSON.stringify(data)
                      }).success(function(data) {
                        deferred.resolve(data);
                      }).error(function(data) {
                        deferred.reject(data);
                      });
                  deferred.promise.then(function(data) {
                        if (data.error == false && data.retMap) {
                          $scope.superiorUser = {};
                          $scope.superiorUser.all = data.retMap.allSuperiorUser;
                        }
                        console.log('getAllSuperiorUserInfo 请求成功');
                      }, function(data) {
                        console.log('getAllSuperiorUserInfo 请求失败');
                      });
                }

                $scope.getLoginInfo = function(data) {
                  if (data) {
                    $(".loginInfo").empty();
                    if (data.user.login) {
                      $(".loginInfo").append('<span class="label label-success">' + "用户名 : " + data.user.userName + '</span>');
                      $(".loginInfo").append('<span class="label label-info">' + "DeviceToken : " + data.user.deviceToken + '</span>');
                    } else {
                      $(".loginInfo").append('<span class="label label-success">' + "错误 : " + data.msg + '</span>');
                    }
                  }
                }

                $scope.savePosition = function() {
                  console.log("savePosition");
                  var deferred = $q.defer();

                  var data = {};
                  data.name = $("#positionName").val();

                  $http({
                        method : 'post',
                        url : 'positionInfo/savePositionInfo',
                        'data' : JSON.stringify(data)
                      }).success(function(data) {
                        deferred.resolve(data);
                      }).error(function(data) {
                        deferred.reject(data);
                      });
                  deferred.promise.then(function(data) {
                        $(".loginInfo").empty();
                        if (data.error) {
                          $(".loginInfo").append('<span class="label label-success">' + "错误 : " + data.msg + '</span>');
                        } else {
                          $scope.getAllPositionInfo();
                        }

                        console.log('savePosition 请求成功');
                      }, function(data) {
                        console.log('savePosition 请求失败');
                      });
                }

                // 页面加载
                angular.element(document).ready(function() {
                      $scope.getAllPositionInfo();
                      $scope.getAllSuperiorUserInfo();
                    });
              }]);

      return ctrl;
    });
