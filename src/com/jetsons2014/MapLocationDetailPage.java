package com.jetsons2014;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jetsons2014.models.MapLocation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by bradtop on 2/22/14.
 */
public class MapLocationDetailPage extends BaseActivity {
	private TextView mNameText;
	private Button mStartButton;
	public static MapLocation mMapLocation;
	private GoogleMap mGoogleMap;
	private MapFragment mMapFragment;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.map_location_detail_page, vMainContent, true);
        mNameText = (TextView) findViewById(R.id.nameText);
        mStartButton = (Button) findViewById(R.id.startButton);
        if (mMapLocation != null) {
        	mNameText.setText(mMapLocation.getName() +
        			mMapLocation.getSecondary() != null ?
        					"\n" + mMapLocation.getSecondary() : "");
        }
        
        mStartButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(MapLocationDetailPage.this, InRouteActivity.class);
				startActivity(i);
				
			}
		});
        getMapFragment();
        
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
							LatLng ll = new LatLng(mMapLocation.getLatitude(), mMapLocation.getLongitude());
							MarkerOptions mo = new MarkerOptions();
							mo.position(ll);
							Marker m = mGoogleMap.addMarker(mo);
							centerMap(m);
						}
					} });
			}
		}, 3000);
	}
    
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
