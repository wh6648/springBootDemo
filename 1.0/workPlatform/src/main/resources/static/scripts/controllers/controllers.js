define([ 'angular', 'jquery' ], function(angular, $) {
	'use strict';
	var ctrl = angular.module('controllers', []);
	ctrl.controller('testCtrl', [
			'$scope',
			'$location',
			'$q',
			'$http',
			'$compile',
			function($scope, $location, $q, $http, $compile) {
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

					var data = {};
					data.name = userName;
					data.password = userPass;

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

				// 页面加载
				angular.element(document).ready(function() {
				});

				$scope.getLoginInfo = function(data) {
					if (data) {
						$(".loginInfo").empty();
						if (data.user.login) {
							$(".loginInfo").append(
									'<span class="label label-success">'
											+ "用户名 : " + data.user.userName
											+ '</span>');
							$(".loginInfo")
									.append(
											'<span class="label label-info">'
													+ "DeviceToken : "
													+ data.user.deviceToken
													+ '</span>');
						} else {
							$(".loginInfo").append(
									'<span class="label label-success">'
											+ "错误 : " + data.msg + '</span>');
						}
					}
				}
			} ]);

	return ctrl;
});
