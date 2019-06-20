var networkinterface = function() {
};

var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');


networkinterface.showStats = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'Traffic', 'showStats');
};

module.exports = networkinterface;
