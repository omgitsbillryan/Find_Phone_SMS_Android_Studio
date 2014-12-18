package com_beta.nerdiction_beta.findphonesms_beta;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kopfgeldjaeger.ratememaybe.RateMeMaybe;
import com_beta.nerdiction_beta.findphonesms_beta.R;

public class ShutAlarmOff extends FragmentActivity {
	
	private static final String DEFAULT_ALARM_TIMEOUT_PERDIOD = "60000";
	
	private FragmentActivity mActivity;
	
	private MediaPlayer mMediaPlayer;
	private Vibrator mVibrator;
	
	private AudioManager mAudioManager;
	private int mInitialStreamVolume;
	
	private final Handler myHandler = new Handler();
	final Runnable mTimeoutCode = new Runnable(){
		public void run(){
			stopAllMedia(); 
			finish();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shut_alarm_off);
		
		mActivity = this;
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String key = getString(R.string.timeout_settings_key);
		String alarmTimeoutPeriod_string = prefs.getString(key, DEFAULT_ALARM_TIMEOUT_PERDIOD);
		int alarmTimeoutPeriod = Integer.parseInt(alarmTimeoutPeriod_string);
		
		Button shutOffBtn = (Button) findViewById(R.id.btnShutOff);
		shutOffBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				myHandler.removeCallbacks(mTimeoutCode);
				stopAllMedia();
				
				RateMeMaybe rmm = new RateMeMaybe(mActivity){
					@Override
					public void _handleCancel() {
						super._handleCancel();
						mActivity.finish();
					}
					@Override
					public void _handleNegativeChoice() {
						super._handleNegativeChoice();
						mActivity.finish();
					}
					@Override
					public void _handleNeutralChoice() {
						super._handleNeutralChoice();
						mActivity.finish();
					}
					@Override
					public void _handlePositiveChoice() {
						super._handlePositiveChoice();
						mActivity.finish();
					}
				};
				
				rmm.setPromptMinimums(2, 14, 2, 14);
				
				rmm.setDialogMessage("Like this app?  Why not take a moment to "
		                +"rate it?!  Or not, that's cool too.  Whatever.");
				rmm.setDialogTitle("Rate me!");
				rmm.setPositiveBtn("Yeeah!");
				
				if(rmm.run() == false) {
					finish();
				}
			}
		});
		
		playSoundOverride(this);
		
		myHandler.postDelayed(mTimeoutCode, alarmTimeoutPeriod);
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopAllMedia();
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_shut_alarm_off, menu);
		return true;
	}
	
	private void playSoundOverride(Context ctx)
    {
    	mVibrator = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
    	long[] vibPattern = { 0, 300, 300 };
    	mVibrator.vibrate(vibPattern, 0);

        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        mMediaPlayer = new MediaPlayer();

        try {
			mMediaPlayer.setDataSource(ctx, alert);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        mAudioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        
        mInitialStreamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM), 0);
        
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        mMediaPlayer.setLooping(true);
        
        try {
			mMediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        mMediaPlayer.start();
    }
	
	private void stopAllMedia()
	{
		mMediaPlayer.stop();
		mVibrator.cancel();
		mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, mInitialStreamVolume, 0);
	}

}
