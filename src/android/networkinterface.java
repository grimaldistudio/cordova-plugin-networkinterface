package com.albahra.plugin.networkinterface;

import android.app.Fragment;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.PackageInfo;

public class networkinterface
extends CordovaPlugin {
    private static final String LOGTAG = "networkinterface";
	private static final String ACTION_LIST_TRAFFIC = "showStats";

    public boolean execute(String action, JSONArray inputs, CallbackContext callbackContext) throws JSONException {
        PluginResult result = null;
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));

        if (ACTION_LIST_TRAFFIC.equals(action)) {
            JSONObject filters = inputs.optJSONObject(0);
            result = this.showStats(callbackContext);
        } else {
            Log.d(LOGTAG, String.format("Invalid action passed: %s", action));
            result = new PluginResult(PluginResult.Status.INVALID_ACTION);
        }
        if (result != null) {
            callbackContext.sendPluginResult(result);
        }
        return true;
    }

    public void onDestroy() {
    }

    public void setOptions(JSONObject options) {
    }

    protected String __getProductShortName() {
        return "Traffic";
    }

    public final String md5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; ++i) {
                String h = Integer.toHexString(255 & messageDigest[i]);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException digest) {
            return "";
        }
    }
	
	private PluginResult showStats(CallbackContext callbackContext) {


       JSONObject json = new JSONObject();
       JSONArray j=new JSONArray();
       final PackageManager pm = getPackageManager();
       List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
       double totalmobile = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes();
       for(ApplicationInfo packageInfo : packages){
           j.put(packageInfo.packageName);
       }
       try {
           json.put("packages",j);
           json.put("total", totalmobile);
           jsons.put(json);
       } catch (Exception e) {
           e.printStackTrace();
       }
       Variables.main.runOnUiThread(new Runnable() {
           public void run() {
               Toast.makeText(Variables.main, jsons.toString(),
                       Toast.LENGTH_SHORT).show();
           }
       });
       Log.e("array", jsons.toString());
      
		
        callbackContext.success(jsons);
        return null;
    }
}
