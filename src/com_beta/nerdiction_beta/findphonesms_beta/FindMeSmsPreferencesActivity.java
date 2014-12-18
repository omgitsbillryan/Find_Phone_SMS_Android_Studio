package com_beta.nerdiction_beta.findphonesms_beta;

import com_beta.nerdiction_beta.findphonesms_beta.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class FindMeSmsPreferencesActivity extends PreferenceActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        
        
        //Preference triggerTextPref = findPreference(getResources().getString(R.string.text_id_settings_key));
        //Preference alarmDurationPref = findPreference(getResources().getString(R.string.timeout_settings_key));
    }
}