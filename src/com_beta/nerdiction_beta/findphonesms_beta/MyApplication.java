package com_beta.nerdiction_beta.findphonesms_beta;

import android.app.Application;

public class MyApplication extends Application {
  @Override
  public void onCreate() {
    // The following line triggers the initialization of ACRA
    super.onCreate();
    //ACRA.init(this);
  }

}