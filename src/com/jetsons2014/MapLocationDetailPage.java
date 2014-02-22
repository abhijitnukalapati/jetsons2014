package com.jetsons2014;

import com.jetsons2014.models.MapLocation;
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.map_location_detail_page, vMainContent, true);
        mNameText = (TextView) findViewById(R.id.nameText);
        mStartButton = (Button) findViewById(R.id.startButton);
        if (mMapLocation != null) {
        	mNameText.setText(mMapLocation.getStoreName() + "\n" + mMapLocation.getName());
        }
        
        mStartButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
    }
}
