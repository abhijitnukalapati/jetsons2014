package com.jetsons2014;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jetsons2014.models.MapLocation;

public class MainMapActivity extends BaseActivity implements OnMarkerClickListener, OnInfoWindowClickListener {
	
	private GoogleMap mGoogleMap;
	private MapFragment mMapFragment;
	private boolean mIsListening;
	
	private List<MarkerHolder> mEventMarkerOptions = new ArrayList<MarkerHolder>();
	private List<MarkerHolder> mPoiMarkerOptions = new ArrayList<MarkerHolder>();
	private List<MarkerHolder> mOfferMarkerOptions = new ArrayList<MarkerHolder>();
	private List<MarkerHolder> mFriendMarkerOptions = new ArrayList<MarkerHolder>();
	private GroundOverlay mGroundOverlay;
	private Button mStartButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.main_map_activity_layout, vMainContent, true);
        
        mMapFragment = getMapFragment();
        populatePoiMarkerOptions();
        populateEventMarkerOptions();
        populateOfferMarkerOptions();
        populateFriendMarkerOptions();
        mStartButton = (Button) findViewById(R.id.sartButton);
        mStartButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
	        	i.setClass(MainMapActivity.this, InRouteActivity.class);
	        	startActivity(i);
			}
		});
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
							LatLng ll = new LatLng(40.426406, -79.96379);
							centerMap(ll);
						}
					} });
			}
		}, 3000);
	}
	
	private GoogleMap getGoogleMapInstance() {
		if (mGoogleMap == null && mMapFragment != null) {
            mGoogleMap = mMapFragment.getMap();
        }
		if (!mIsListening && mGoogleMap != null) {
			mGoogleMap.setOnMarkerClickListener(this);
			mGoogleMap.setOnInfoWindowClickListener(this);
			mIsListening = true;
		}
		
		return mGoogleMap;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		getGoogleMapInstance();
		item.setChecked(!item.isChecked());
		boolean result = true;
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.events:
	        	if (item.isChecked()) {
	        		item.setIcon(R.drawable.map_event_o);
	        		for(MarkerHolder mh : mEventMarkerOptions) {
	        			mh.marker = mGoogleMap.addMarker(mh.mo);
	        			mh.marker.setIcon(BitmapDescriptorFactory.defaultMarker(357));
	        			mh.marker.setTitle(mh.name + 
	        					(mh.secondaryText == null ? "" : " - " + mh.secondaryText));
	        		}
	        	}
	        	else {
	        		item.setIcon(R.drawable.map_event);
	        		for(MarkerHolder mh : mEventMarkerOptions) {
        				mh.marker.remove();
        				mh.marker = null;
	        		}
	        	}
	        	result = true;
	        	break;
	        case R.id.poi:
	        	if (item.isChecked()) {
	        		item.setIcon(R.drawable.map_location_o);
	        		for(MarkerHolder mh : mPoiMarkerOptions) {
	        			mh.marker = mGoogleMap.addMarker(mh.mo);
	        			mh.marker.setIcon(BitmapDescriptorFactory.defaultMarker(228));
	        			mh.marker.setTitle(mh.name + 
	        					(mh.secondaryText == null ? "" : " - " + mh.secondaryText));
	        		}
	        	}
	        	else {
	        		item.setIcon(R.drawable.map_location);
	        		for(MarkerHolder mh : mPoiMarkerOptions) {
	        			mh.marker.remove();
        				mh.marker = null;
	        		}
	        	}
	        	result = true;
	        	break;
	        case R.id.offers:
	        	if (item.isChecked()) {
	        		item.setIcon(R.drawable.map_offer_o);
	        		for(MarkerHolder mh : mOfferMarkerOptions) {
	        			mh.marker = mGoogleMap.addMarker(mh.mo);
	        			mh.marker.setIcon(BitmapDescriptorFactory.defaultMarker(88));
	        			mh.marker.setTitle(mh.name + 
	        					(mh.secondaryText == null ? "" : " - " + mh.secondaryText));
	        		}
	        	}
	        	else {
	        		item.setIcon(R.drawable.map_offer);
	        		for(MarkerHolder mh : mOfferMarkerOptions) {
	        			mh.marker.remove();
        				mh.marker = null;
	        		}
	        	}
	        	result = true;
	        	break;
	        case R.id.friends:
	        	if (item.isChecked()) {
	        		item.setIcon(R.drawable.map_group_o);
	        		for(MarkerHolder mh : mFriendMarkerOptions) {
	        			mh.marker = mGoogleMap.addMarker(mh.mo);
	        			mh.marker.setIcon(BitmapDescriptorFactory.defaultMarker(26));
	        			mh.marker.setTitle(mh.name + 
	        					(mh.secondaryText == null ? "" : " - " + mh.secondaryText));
	        		}
	        	}
	        	else {
	        		item.setIcon(R.drawable.map_group);
	        		for(MarkerHolder mh : mFriendMarkerOptions) {
	        			mh.marker.remove();
        				mh.marker = null;
	        		}
	        	}
	        	result = true;
	        	break;
	        case R.id.heatmap:
	        	if (item.isChecked()) {
	        		item.setIcon(R.drawable.map_routes_o);
	        		addGroundOverlay();
	        	}
	        	else {
	        		item.setIcon(R.drawable.map_routes);
	        		mGroundOverlay.remove();
	        		mGroundOverlay = null;
	        	}
	        	result = true;
	        	break;
	        default:
	        	result = super.onOptionsItemSelected(item);
	    }
	    if (item.isChecked() && item.getItemId() != R.id.heatmap) {
	    	centerMap();
	    }
	    
	    return result;
	}
	
	private void addGroundOverlay() {
		
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		//https://maps.google.com/?ll=40.430124,-79.972843&spn=0.001156,0.002642&t=m&z=19
		LatLng topLeft = new LatLng(40.430424, -79.972832);
		builder.include(topLeft);
		//https://maps.google.com/?ll=40.422167,-79.963823&spn=0.002311,0.005284&t=m&z=18
		LatLng bottomRight = new LatLng(40.422467, -79.963812);
		builder.include(bottomRight);
        try {
        	LatLngBounds bounds = builder.build();
            mGroundOverlay = mGoogleMap.addGroundOverlay(
        			new GroundOverlayOptions()
            	     .image(BitmapDescriptorFactory.fromResource(R.drawable.map_overlay))
            	     .positionFromBounds(bounds)
            	     .transparency(0.5f));
        }
        catch (IllegalStateException ise) {}
		
	}

	/**
     * Centers the map around the markers in mMarkers
     */
    private void centerMap() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(MarkerHolder m : mEventMarkerOptions) {
        	if (m.marker != null) builder.include(m.marker.getPosition());
		}
        for(MarkerHolder m : mPoiMarkerOptions) {
        	if (m.marker != null) builder.include(m.marker.getPosition());
		}
        for(MarkerHolder m : mOfferMarkerOptions) {
        	if (m.marker != null) builder.include(m.marker.getPosition());
		}
        for(MarkerHolder m : mFriendMarkerOptions) {
        	if (m.marker != null) builder.include(m.marker.getPosition());
		}
        try {
        	LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
            mGoogleMap.animateCamera(cu);
        }
        catch (IllegalStateException ise) {}
        
    }
    
    private void centerMap(LatLng ll) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(ll);
        LatLng buffer = new LatLng(ll.latitude+0.0012, ll.longitude+0.0012);
        builder.include(buffer);
        buffer = new LatLng(ll.latitude-0.0012, ll.longitude-0.0012);
        builder.include(buffer);
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 10);
        mGoogleMap.animateCamera(cu);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_map_action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	
	class MarkerHolder {
		public MarkerOptions mo;
		public String name;
		public String secondaryText;
		public Marker marker;
		
		MarkerHolder(MarkerOptions mo, String name, String secondaryText) {
			this.mo = mo;
			this.name = name;
			this.secondaryText = secondaryText;
		}
	}
	
	@Override
    public boolean onMarkerClick(Marker marker) {
		MarkerHolder mh = null;
		for(MarkerHolder m : mEventMarkerOptions) {
        	if (m.marker != null) {
        		if (m.marker.equals(marker)) {
        			mh = m;
        		}
        	}
		}
        for(MarkerHolder m : mPoiMarkerOptions) {
        	if (m.marker != null) {
        		if (m.marker.equals(marker)) {
        			mh = m;
        		}
        	}
		}
        for(MarkerHolder m : mOfferMarkerOptions) {
        	if (m.marker != null) {
        		if (m.marker.equals(marker)) {
        			mh = m;
        		}
        	}
		}
        for(MarkerHolder m : mFriendMarkerOptions) {
        	if (m.marker != null) {
        		if (m.marker.equals(marker)) {
        			mh = m;
        		}
        	}
		}
        return false;
    }
	
	private void populateEventMarkerOptions() {
		LatLng ll = new LatLng(40.427843, -79.96562);
		MarkerOptions mo = new MarkerOptions();
		mo.position(ll);
		mEventMarkerOptions.add(new MarkerHolder(mo, "Monthly Meet and Greet", null));
		
	}
	
	private void populatePoiMarkerOptions() {
		LatLng ll = new LatLng(40.429198, -79.96474);
		MarkerOptions mo = new MarkerOptions();
		mo.position(ll);
		mPoiMarkerOptions.add(new MarkerHolder(mo, "Three Rivers Heritage Trail", null));
		
		ll = new LatLng(40.431485, -79.972959);
		mo = new MarkerOptions();
		mo.position(ll);
		mPoiMarkerOptions.add(new MarkerHolder(mo, "Southside Riverfront Park", null));
	}
	
	private void populateOfferMarkerOptions() {
		LatLng ll = new LatLng(40.427434, -79.968388);
		MarkerOptions mo = new MarkerOptions();
		mo.position(ll);
		mOfferMarkerOptions.add(new MarkerHolder(mo, "Blues Coffee House", "Free Coffee"));
		
		ll = new LatLng(40.42554, -79.964654);
		mo = new MarkerOptions();
		mo.position(ll);
		mOfferMarkerOptions.add(new MarkerHolder(mo, "Fred's Pancake House", "10% Off"));
	}
	
	private void populateFriendMarkerOptions() {
		LatLng ll = new LatLng(40.428104, -79.970899 );
		MarkerOptions mo = new MarkerOptions();
		mo.position(ll);
		mFriendMarkerOptions.add(new MarkerHolder(mo, "Stan Marsh", null));
		
		ll = new LatLng(40.428202, -79.971113);
		mo = new MarkerOptions();
		mo.position(ll);
		mFriendMarkerOptions.add(new MarkerHolder(mo, "Kenny McCormick", null));
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		MarkerHolder mh = null;
		for(MarkerHolder m : mEventMarkerOptions) {
        	if (m.marker != null) {
        		if (m.marker.equals(marker)) {
        			mh = m;
        		}
        	}
		}
		
        for(MarkerHolder m : mPoiMarkerOptions) {
        	if (m.marker != null) {
        		if (m.marker.equals(marker)) {
        			mh = m;
        		}
        	}
		}
        
        for(MarkerHolder m : mOfferMarkerOptions) {
        	if (m.marker != null) {
        		if (m.marker.equals(marker)) {
        			mh = m;
        		}
        	}
		}
        
        for(MarkerHolder m : mFriendMarkerOptions) {
        	if (m.marker != null) {
        		if (m.marker.equals(marker)) {
        			mh = m;
        		}
        	}
		}
        
        if (mh != null) {
        	Intent i = new Intent();
        	i.setClass(this, InRouteActivity.class);
        	MapLocationDetailPage.mMapLocation = new MapLocation(
        			mh.mo.getPosition().longitude, 
        			mh.mo.getPosition().latitude, 
        			mh.name,
        			mh.secondaryText);
        	startActivity(i);
        }
		
	}
}
