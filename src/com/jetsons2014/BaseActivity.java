package com.jetsons2014;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by bradtop on 2/22/14.
 */
public class BaseActivity extends Activity implements ListView.OnItemClickListener {
    private String[] mCategoryItems;
    private String[] mFriends;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private int mListViewSize;
    private final int CATEGORY_ROW = 121;
    private final int FRIEND_ROW = 123;
    private final int POINTS_ROW = 125;
    private final int LEVEL_ROW = 127;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mCategoryItems = getResources().getStringArray(R.array.drawerList);
        mFriends = getResources().getStringArray(R.array.friendList);
        mListViewSize = mCategoryItems.length + mFriends.length + 2;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new NavAdapter());

//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_category_item, mCategoryItems));
        mDrawerList.setOnItemClickListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.open_drawer,  /* "open drawer" description */
                R.string.close_drawer  /* "close drawer" description */
        ) { };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        switch(position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    private class NavAdapter extends BaseAdapter{

        @Override
        public int getItemViewType(int position) {
            if(position == 0 || position == 2 || position == 4){
                return CATEGORY_ROW;
            } else if(position == 1) {
                return LEVEL_ROW;
            } else if(position == 3){
                return POINTS_ROW;
            } else {
                return FRIEND_ROW;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 4;
        }

        @Override
        public int getCount(){
            return mListViewSize;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            LayoutInflater inflater = getLayoutInflater();
            switch (getItemViewType(position)) {
                case CATEGORY_ROW:
                    row = inflater.inflate(R.layout.drawer_category_item, null);
                    ((TextView) (row)).setText(mCategoryItems[position / 2]);
                    break;
                case FRIEND_ROW:
                    row = inflater.inflate(R.layout.drawer_friend_item, parent, false);
                    TextView textView1 = (TextView)row.findViewById(R.id.textView);
                    textView1.setText(mFriends[position  - 5]);
                    break;
                case LEVEL_ROW:
                    row = inflater.inflate(R.layout.drawer_points_layout, parent, false);
                    TextView textView2 = (TextView)row.findViewById(R.id.textView);
                    textView2.setText("5");
                    break;
                case POINTS_ROW:
                    row = inflater.inflate(R.layout.drawer_points_layout, parent, false);
                    TextView textView3 = (TextView)row.findViewById(R.id.textView);
                    textView3.setText("100");
                    break;
                default:
                    row = inflater.inflate(R.layout.drawer_category_item, null);
                    break;
            }

            return row;

        }
    }
}
