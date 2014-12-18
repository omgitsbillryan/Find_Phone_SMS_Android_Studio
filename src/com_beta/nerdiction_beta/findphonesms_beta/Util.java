package com_beta.nerdiction_beta.findphonesms_beta;

import com_beta.nerdiction_beta.findphonesms_beta.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Util 
{
	private static final boolean DEFAULT_ENABLED = true;
	private static final String DEFAULT_ALARM_TIMEOUT_PERDIOD = "60000";
	private static final String DEFAULT_ID_STRING = "findmesms";
	
	public static void initSharedPreferences(Context ctx, SharedPreferences prefs)
	{
		Editor edit = prefs.edit();
		
		String enabledKey = ctx.getString(R.string.findmesms_enabled);
        edit.putBoolean(enabledKey, DEFAULT_ENABLED);
        
		String smsIdPrefKey = ctx.getString(R.string.text_id_settings_key);
        edit.putString(smsIdPrefKey, DEFAULT_ID_STRING);
        
        String timeoutPrefKey = ctx.getString(R.string.timeout_settings_key);
        edit.putString(timeoutPrefKey, DEFAULT_ALARM_TIMEOUT_PERDIOD);
        
        edit.commit();
	}
}
