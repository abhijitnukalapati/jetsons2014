package com.jetsons2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class AchievementsActivity extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achievements);
		try {
			getActionBar().hide();
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
    	overridePendingTransition(R.anim.stay_anim, R.anim.slide_down_exit);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
		finish();
    	overridePendingTransition(R.anim.stay_anim, R.anim.slide_down_exit);
		return super.dispatchTouchEvent(ev);
	}
}
