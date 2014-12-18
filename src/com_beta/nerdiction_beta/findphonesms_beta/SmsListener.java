package com_beta.nerdiction_beta.findphonesms_beta;

import com_beta.nerdiction_beta.findphonesms_beta.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;

public class SmsListener extends BroadcastReceiver{

    
    private String mMessageBody;
    private String mMessageFrom;
    
    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	//SharedPreferences prefs = this.getSharedPreferences("com.example.findmesms", Context.MODE_PRIVATE);
    	
    	if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
    		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    		if(prefs.getAll().size() < 1){
    			// shared preferences have not been set yet
    			Util.initSharedPreferences(context, prefs);
    		}
    		
    		String enabledKey = context.getString(R.string.findmesms_enabled);
            boolean enabled = prefs.getBoolean(enabledKey, true);
    		if(enabled) {
	    		Bundle bundle = intent.getExtras();
	            SmsMessage[] msgs = null;
	            
	    		if (bundle != null){
	                //---retrieve the SMS message received---
	    			Object[] pdus = (Object[]) bundle.get("pdus");
	    			msgs = new SmsMessage[pdus.length];
	                
	                for(int i=0; i<msgs.length; i++){
	                    msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
	                    mMessageFrom = msgs[i].getOriginatingAddress();
	                    mMessageBody = msgs[i].getMessageBody();
	                }
	            }
	            
	            String key = context.getString(R.string.text_id_settings_key);
	            String textToMatch = prefs.getString(key, "this should never get matched 621g$1c***"); // if we cant find the preference, then never match
	            if(mMessageBody.toLowerCase().contains(textToMatch.toLowerCase()))
	            {
	            	Intent i = new Intent(context, ShutAlarmOff.class);
	            	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            	context.startActivity(i);
	            }
    		} // end if enabled
    	} // end if SMS message
    	
    	// TODO - REMOVE (ACRA testing)
    	//throw new RuntimeException("Dying on purpose");
    }
    
    
}
