package com.jetsons2014;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by bradtop on 2/22/14.
 */
public class BaseActivity extends Activity implements ListView.OnItemClickListener {
    private String[] mCategoryItems;
    private String[] mFriends;
    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected FrameLayout vMainContent;

    private int mListViewSize;
    private final int CATEGORY_ROW = 121;
    private final int FRIEND_ROW = 123;
    private final int POINTS_ROW = 125;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mCategoryItems = getResources().getStringArray(R.array.drawerList);
        mFriends = getResources().getStringArray(R.array.friendList);
        mListViewSize = mCategoryItems.length + mFriends.length;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new NavAdapter());
        vMainContent = (FrameLayout) findViewById(R.id.content_frame);

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
            if(position == 0 || position == 2){
                return CATEGORY_ROW;
            } else if(position == 1){
                return POINTS_ROW;
            } else {
                return FRIEND_ROW;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 3;
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
                    if(position == 0){
                    	((TextView) (row)).setTypeface(null,Typeface.NORMAL);
                    	SpannableStringBuilder sb = new SpannableStringBuilder();
                    	sb.append("Christov");
                    	int bold_font_end = sb.length();
                    	sb.append(" - Road Warrior");
                    	sb.setSpan(new StyleSpan(Typeface.BOLD), 0, bold_font_end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    	((TextView) (row)).setText(sb);
                    } else {
                    	((TextView) (row)).setText("Friends");
                    }
                    break;
                case FRIEND_ROW:
                    row = inflater.inflate(R.layout.drawer_friend_item, parent, false);
                    TextView textView1 = (TextView)row.findViewById(R.id.textView);
                    textView1.setText(mFriends[position - 3]);
                    break;
                case POINTS_ROW:
                    row = inflater.inflate(R.layout.drawer_level_points_item, parent, false);
                    TextView numberView2 = (TextView) row.findViewById(R.id.numberView);
                    TextView textView3 = (TextView)row.findViewById(R.id.textView);
                    textView3.setText("Points");
                    numberView2.setText("100");
                    break;
                default:
                    row = inflater.inflate(R.layout.drawer_category_item, null);
                    break;
            }

            return row;

        }
    }
}
