// the app/scripts/main.js file, which defines our RequireJS config
require.config({
	paths : {
		'angular' : 'vendor/angular',
		'domReady' : 'vendor/domReady',
		'jquery' : 'vendor/jquery-2.0.0.min',
		'bootstrap' : 'vendor/bootstrap.min'
	},
	waitSeconds : 0,
	shim : {
		'angular' : {
			exports : 'angular'
		},
		'jquery' : {
			exports : 'jquery'
		},
		'domReady' : {
			exports : 'domReady'
		},
		'bootstrap' : {
			deps : [ 'jquery' ],
			exports : 'bootstrap'
		}
	}

});

require([ 'angular', 'app', 'domReady', 'bootstrap', 'directives/directives',
		'controllers/controllers' ], function(angular, app, domReady) {
	'use strict';
	domReady(function() {
		angular.bootstrap(document, [ 'webapp' ]);
	});
});
