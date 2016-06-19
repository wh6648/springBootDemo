// The app/scripts/app.js file, which defines our AngularJS app
define([ 'angular', 'controllers/controllers', 'directives/directives' ],
		function(angular) {
			var webapp = angular.module('webapp',
					[ 'controllers', 'directives' ]);

			// 禁止模板缓存
			webapp.run(function($rootScope, $templateCache) {
				$rootScope.$on('$routeChangeStart', function(event, next,
						current) {
					if (typeof (current) !== 'undefined') {
						$templateCache.remove(current.templateUrl);
					}
				});
			});

			return webapp;
		});
