package com.poofinc.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.iteye.weimingtom.screenshot.R;
import android.widget.TextView;

public class Screenshot extends Activity {
    public static final String SHARED_PREF_NAME = "poofincscreenshot";
    public static final String KEY_TIMEOUT = "timeout";
    
    private Button btn;
    private SeekBar delaySeekBar;
    private TextView delayTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.global_screenshot_main);
        this.btn = (Button) findViewById(R.id.btnTakeScreenshot);
        this.btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Editor edit = Screenshot.this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
                edit.putInt(KEY_TIMEOUT, Screenshot.this.delaySeekBar.getProgress());
                edit.commit();
                Screenshot.this.startService(new Intent(Screenshot.this, ScreenshotService.class));
            }
        });
        this.delaySeekBar = (SeekBar) findViewById(R.id.delaySeekBar);
        this.delaySeekBar.setProgress(getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).getInt(KEY_TIMEOUT, 5));
        this.delayTextView = (TextView) findViewById(R.id.delayTextView);
        this.delayTextView.setText("Screenshot delay: " + this.delaySeekBar.getProgress() + " seconds");
        this.delaySeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            	
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            	
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                delayTextView.setText("Screenshot delay: " + progress + " seconds");
            }
        });
    }
}
