define(['angular', 'jquery', 'uiGrid'], function(angular, $) {
      'use strict';
      var ctrl = angular.module('controllers', ['ui.grid', 'ui.grid.selection', 'ui.grid.autoResize', 'ui.grid.cellNav', 'ui.grid.resizeColumns', 'ui.grid.saveState']);
      ctrl.controller('testCtrl', ['$scope', '$location', '$q', '$http', '$compile', '$timeout', '$interval', function($scope, $location, $q, $http, $compile, $timeout, $interval) {
                function randomNumber() {
                  return randomFromInterval(1, 1e6);
                }

                function randomFromInterval(e, t) {
                  return Math.floor(Math.random() * (t - e + 1) + e);
                }

                $scope.labelCssArr = ["label-default", "label-primary", "label-success", "label-info", "label-warning"];

                $scope.positionGridOptions = {
                  enableRowSelection : true,
                  enableRowHeaderSelection : false,
                  multiSelect : false,
                  modifierKeysToMultiSelect : false,
                  noUnselect : false,
                  enableFullRowSelection : true,
                  enableFiltering : false,
                  enableColumnResizing : false,
                  onRegisterApi : function(gridApi) {
                    $scope.gridApi = gridApi,

                    $scope.gridApi.selection.on.rowSelectionChanged($scope, function(row, event) {
                          $("#positionName").attr("positionId", "");
                          $("#positionName").attr("oldName", "");
                          $("#positionName").val("");

                          if (row && row.isSelected) {
                            $("#positionName").val(row.entity.name);
                            $("#positionName").attr("oldName", row.entity.name);
                            $("#positionName").attr("positionId", row.entity.id);
                          }
                        })
                  },
                  columnDefs : [],
                  data : [],
                  selectedRows : []
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
                                cellTemplate : '<div class="ui-grid-cell-contents ng-binding ng-scope" style="cursor: pointer;" title="{{row.entity.id}}">{{row.entity.name}}</div>',
                                displayName : "职务名"
                              }];
                          $scope.positionGridOptions.data = data.retMap.allPosition;
                          $scope.positionGridOptions.multiSelect = false;

                          $timeout(function() {
                              });

                          $interval(function() {
                              }, 0, 1);
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

                        $("#positionName").attr("positionId", "");
                        $("#positionName").attr("oldName", "");
                        $("#positionName").val("");

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

                $scope.updatePosition = function() {
                  console.log("updatePosition");

                  var id = $("#positionName").attr("positionId");
                  var oldName = $("#positionName").attr("oldName");
                  var newName = $("#positionName").val();

                  if (id != null && oldName != null && newName != null) {
                    if (oldName !== newName) {
                      var deferred = $q.defer();

                      var data = {};
                      data.id = id;
                      data.name = oldName;
                      data.newName = newName;

                      $http({
                            method : 'post',
                            url : 'positionInfo/updatePositionInfo',
                            'data' : JSON.stringify(data)
                          }).success(function(data) {
                            deferred.resolve(data);
                          }).error(function(data) {
                            deferred.reject(data);
                          });
                      deferred.promise.then(function(data) {
                            $("#positionName").attr("positionId", "");
                            $("#positionName").attr("oldName", "");
                            $("#positionName").val("");

                            $(".loginInfo").empty();
                            if (data.error) {
                              $(".loginInfo").append('<span class="label label-success">' + "错误 : " + data.msg + '</span>');
                            } else {
                              $scope.getAllPositionInfo();
                            }

                            console.log('deletePosition 请求成功');
                          }, function(data) {
                            console.log('deletePosition 请求失败');
                          });
                    }
                  }
                }

                $scope.deletePosition = function() {
                  console.log("deletePosition");
                  var deferred = $q.defer();

                  var data = {};
                  data.name = $("#positionName").val();

                  $http({
                        method : 'post',
                        url : 'positionInfo/deletePositionInfo',
                        'data' : JSON.stringify(data)
                      }).success(function(data) {
                        deferred.resolve(data);
                      }).error(function(data) {
                        deferred.reject(data);
                      });
                  deferred.promise.then(function(data) {
                        $("#positionName").attr("positionId", "");
                        $("#positionName").attr("oldName", "");
                        $("#positionName").val("");

                        $(".loginInfo").empty();
                        if (data.error) {
                          $(".loginInfo").append('<span class="label label-success">' + "错误 : " + data.msg + '</span>');
                        } else {
                          $scope.getAllPositionInfo();
                        }

                        console.log('deletePosition 请求成功');
                      }, function(data) {
                        console.log('deletePosition 请求失败');
                      });
                }

                $scope.getAllDefect = function() {
                  console.log("getAllDefect");
                  var deferred = $q.defer();

                  var data = {};

                  $http({
                        method : 'post',
                        url : 'defect/getAllDefect',
                        'data' : JSON.stringify(data)
                      }).success(function(data) {
                        deferred.resolve(data);
                      }).error(function(data) {
                        deferred.reject(data);
                      });
                  deferred.promise.then(function(data) {
                        $(".defect").empty();

                        $(".loginInfo").empty();
                        if (data.error) {
                          $(".loginInfo").append('<span class="label label-success">' + "错误 : " + data.msg + '</span>');
                        } else {
                          if (data.retMap) {
                            var allDefect = data.retMap.allDefect;
                            angular.forEach(allDefect, function(val, idx) {
                                  var css = "label-default";
                                  if ($scope.labelCssArr) {
                                    css = $scope.labelCssArr[randomFromInterval(0, $scope.labelCssArr.length - 1)];
                                  }

                                  $(".defect").append('<span class="label ' + css + '" style="cursor: pointer;margin: 10px" defectId="' + val.id + '">' + val.name + '</span>');
                                });
                          }
                        }

                        console.log('getAllDefect 请求成功');
                      }, function(data) {
                        console.log('getAllDefect 请求失败');
                      });
                }

                $scope.saveDefect = function() {
                  console.log("saveDefect");
                  var deferred = $q.defer();

                  var data = {};
                  data.type = $("#defectType").val();
                  data.name = $("#defectName").val();

                  $http({
                        method : 'post',
                        url : 'defect/saveDefect',
                        'data' : JSON.stringify(data)
                      }).success(function(data) {
                        deferred.resolve(data);
                      }).error(function(data) {
                        deferred.reject(data);
                      });
                  deferred.promise.then(function(data) {
                        $(".defect").empty();

                        $(".loginInfo").empty();
                        if (data.error) {
                          $(".loginInfo").append('<span class="label label-success">' + "错误 : " + data.msg + '</span>');
                        } else {
                          $scope.getAllDefect();
                        }

                        console.log('saveDefect 请求成功');
                      }, function(data) {
                        console.log('saveDefect 请求失败');
                      });
                }

                // 页面加载
                angular.element(document).ready(function() {
                      $scope.getAllPositionInfo();
                      $scope.getAllSuperiorUserInfo();
                      $scope.getAllDefect();
                    });
              }]);

      return ctrl;
    });
