package com.cyq.allprogress;

import java.io.File;

import com.cyq.allprogress.progress.HorizonProgress;
import com.cyq.allprogresss.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends Activity {
	com.cyq.allprogress.progress.HorizonProgress pro;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		pro = (HorizonProgress) findViewById(R.id.progress);
		
		
		
		handler.post(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessage(0);
				handler.postDelayed(this, 100);
			}
		});
		
	}
	
	int i=0;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (i==100) {
					i=0;
				}
				i++;
				pro.setProgress(i);
				break;

			default:
				break;
			}
		};
	};
	
	
}
