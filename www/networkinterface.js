var networkinterface = function() {
};

var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');


networkinterface.getUsageStatistics = function(interval, successCallback, failureCallback) {
        var array = [interval];
	cordova.exec( successCallback, failureCallback, 'MyUsageStatsManager', 'getUsageStatistics', array);
};

networkinterface.openPermissionSettings = function(successCallback, failureCallback) {
        var array = []; // not needed but seems to throw exceptions on some cases if not included.
	cordova.exec( successCallback, failureCallback,  "MyUsageStatsManager", "openPermissionSettings", array);
};

module.exports = networkinterface;
