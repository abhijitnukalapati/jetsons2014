package com.jetsons2014;

import android.app.Activity;
import android.os.Bundle;

public class AchievementsActivity extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			getActionBar().setTitle("Achievements");
		} catch (Exception e) {
			
		}
	}
	
	
	
}
