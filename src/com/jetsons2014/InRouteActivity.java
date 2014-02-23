package com.jetsons2014;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.model.people.Person.Image;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InRouteActivity extends BaseActivity implements OnClickListener {
	
	private ImageView mBikerImage;
	private Button mStopButton;
	private TranslateAnimation mEnterAnimation;
	private TranslateAnimation mLeaveAnimation;
	private GoogleMap mGoogleMap;
	private MapFragment mMapFragment;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.in_route_activity_layout, vMainContent, true);
        
        mBikerImage = (ImageView) findViewById(R.id.bikerImage);
        mStopButton = (Button) findViewById(R.id.stopButton);
        mMapFragment = getMapFragment();
        
        mStopButton.setOnClickListener(this);
        
        // rotation setup
        final RotateAnimation anim = new RotateAnimation(-1.0f, 1.5f, 500, 500);
        anim.setStartOffset(0);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(300);
        anim.setRepeatMode(Animation.REVERSE);
        
        // enter animation setup
        mEnterAnimation = new TranslateAnimation(-1500.0f, 0f, 0, 0);
        mEnterAnimation.setStartOffset(1000);
        mEnterAnimation.setInterpolator(new DecelerateInterpolator());
        mEnterAnimation.setRepeatCount(0);
        mEnterAnimation.setDuration(1200);
        mEnterAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mBikerImage.setAnimation(anim);
			}
		});
        
        // leave animation setup
        mLeaveAnimation = new TranslateAnimation(0f, 1500.0f, 0, 0);
        mLeaveAnimation.setStartOffset(500);
        mLeaveAnimation.setInterpolator(new DecelerateInterpolator());
        mLeaveAnimation.setRepeatCount(0);
        mLeaveAnimation.setDuration(500);
        mLeaveAnimation.setFillAfter(true);
        mLeaveAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				InRouteActivity.this.finish();
				
				Intent i = new Intent(InRouteActivity.this, AchievementsActivity.class);
            	startActivity(i);
            	overridePendingTransition(R.anim.slide_up_enter, R.anim.stay_anim);
			}
		});
        
        mBikerImage.startAnimation(mEnterAnimation);
    }
	
	private MapFragment getMapFragment() {
        MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        return map;
    }

	@Override
	protected void onResume() {
		super.onResume();
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (mGoogleMap == null && mMapFragment != null) {
				            mGoogleMap = mMapFragment.getMap();
				        }
						if (mGoogleMap != null) {
							//40.426406,-79.96379
							LatLng ll = new LatLng(40.426406, -79.96379);
							MarkerOptions mo = new MarkerOptions();
							mo.position(ll);
							Marker m = mGoogleMap.addMarker(mo);
							centerMap(m);
						}
					} });
			}
		}, 3000);
	}
	
	@Override
	public void onClick(View v) {
		mBikerImage.startAnimation(mLeaveAnimation);
	}
	
	/**
     * Centers the map around the markers in mMarkers
     */
    private void centerMap(Marker m) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(m.getPosition());
        LatLng buffer = new LatLng(m.getPosition().latitude+0.0012, m.getPosition().longitude+0.0012);
        builder.include(buffer);
        buffer = new LatLng(m.getPosition().latitude-0.0012, m.getPosition().longitude-0.0012);
        builder.include(buffer);
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 10);
        mGoogleMap.animateCamera(cu);
    }
}
