package com.jetsons2014;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainMapActivity extends BaseActivity {
	
	private GoogleMap mGoogleMap;
	private MapFragment mMapFragment;
	
	private List<MarkerHolder> mEventMarkerOptions = new ArrayList<MarkerHolder>();
	private List<MarkerHolder> mPoiMarkerOptions = new ArrayList<MarkerHolder>();
	private List<MarkerHolder> mOfferMarkerOptions = new ArrayList<MarkerHolder>();
	private List<MarkerHolder> mFriendMarkerOptions = new ArrayList<MarkerHolder>();
	
	private List<Marker> mEventMarkers = new ArrayList<Marker>();
	private List<Marker> mPoiMarkers = new ArrayList<Marker>();
	private List<Marker> mOfferMarkers = new ArrayList<Marker>();
	private List<Marker> mFriendMarkers = new ArrayList<Marker>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.main_map_activity_layout, vMainContent, true);
        
        mMapFragment = getMapFragment();
        populatePoiMarkerOptions();
        populateEventMarkerOptions();
        populateOfferMarkerOptions();
        populateFriendMarkerOptions();
	}
	
	private MapFragment getMapFragment() {
        MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        return map;
    }
	
	private GoogleMap getGoogleMapInstance() {
		if (mGoogleMap == null && mMapFragment != null) {
            mGoogleMap = mMapFragment.getMap();
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
						mEventMarkers.add(mGoogleMap.addMarker(mh.mo));
	        		}
	        	}
	        	else {
	        		item.setIcon(R.drawable.map_event);
	        		for(Marker m : mEventMarkers) {
        				m.remove();
	        		}
	        		mEventMarkers.clear();
	        	}
	        	result = true;
	        	break;
	        case R.id.poi:
	        	if (item.isChecked()) {
	        		item.setIcon(R.drawable.map_location_o);
	        		for(MarkerHolder mh : mPoiMarkerOptions) {
						mPoiMarkers.add(mGoogleMap.addMarker(mh.mo));
	        		}
	        	}
	        	else {
	        		item.setIcon(R.drawable.map_location);
	        		for(Marker m : mPoiMarkers) {
        				m.remove();
	        		}
	        		mPoiMarkers.clear();
	        	}
	        	result = true;
	        	break;
	        case R.id.offers:
	        	if (item.isChecked()) {
	        		item.setIcon(R.drawable.map_offer_o);
	        		for(MarkerHolder mh : mOfferMarkerOptions) {
						mOfferMarkers.add(mGoogleMap.addMarker(mh.mo));
	        		}
	        	}
	        	else {
	        		item.setIcon(R.drawable.map_offer);
	        		for(Marker m : mOfferMarkers) {
        				m.remove();
	        		}
	        		mOfferMarkers.clear();
	        	}
	        	result = true;
	        	break;
	        case R.id.friends:
	        	if (item.isChecked()) {
	        		item.setIcon(R.drawable.map_group_o);
	        		for(MarkerHolder mh : mFriendMarkerOptions) {
						mFriendMarkers.add(mGoogleMap.addMarker(mh.mo));
	        		}
	        	}
	        	else {
	        		item.setIcon(R.drawable.map_group);
	        		for(Marker m : mFriendMarkers) {
        				m.remove();
	        		}
	        		mFriendMarkers.clear();
	        	}
	        	result = true;
	        	break;
	        default:
	        	result = super.onOptionsItemSelected(item);
	    }
	    centerMap();
	    return result;
	}
	
	/**
     * Centers the map around the markers in mMarkers
     */
    private void centerMap() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Marker m : mEventMarkers) {
        	builder.include(m.getPosition());
		}
        for(Marker m : mPoiMarkers) {
        	builder.include(m.getPosition());
		}
        for(Marker m : mOfferMarkers) {
        	builder.include(m.getPosition());
		}
        for(Marker m : mFriendMarkers) {
        	builder.include(m.getPosition());
		}
        try {
        	LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
            mGoogleMap.animateCamera(cu);
        }
        catch (IllegalStateException ise) {}
        
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
		
		MarkerHolder(MarkerOptions mo, String name, String secondaryText) {
			this.mo = mo;
			this.name = name;
			this.secondaryText = secondaryText;
		}
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
}
