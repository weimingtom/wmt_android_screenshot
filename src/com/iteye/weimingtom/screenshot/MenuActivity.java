package com.iteye.weimingtom.screenshot;

import com.example.screenrecord.MainActivity;
import com.poofinc.screenshot.Screenshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {
	private Button buttonGlobalCap, buttonAppCap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_menu);
		
		buttonGlobalCap = (Button) this.findViewById(R.id.buttonGlobalCap);
		buttonAppCap = (Button) this.findViewById(R.id.buttonAppCap);
		
		buttonGlobalCap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MenuActivity.this, Screenshot.class));
			}
		});
		buttonAppCap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MenuActivity.this, MainActivity.class));
			}
		});
	}

}
