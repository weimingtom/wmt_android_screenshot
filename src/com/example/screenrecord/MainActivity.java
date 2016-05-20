package com.example.screenrecord;

import com.iteye.weimingtom.screenshot.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    private final static String TAG = "zyw";
    private final static boolean DEBUG = true;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.app_screenshot_main);
        this.startService(new Intent(this, ScreenShotService.class)
        		.putExtra(ScreenShotService.TRANSCRIBE_TAG, true));
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    
}