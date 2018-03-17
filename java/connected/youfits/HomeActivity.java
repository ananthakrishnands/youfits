package connected.youfits;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import connected.youfit.R;

public class HomeActivity extends AppCompatActivity implements ActionBar.TabListener {
    ViewPager viewpager;
    ActionBar actionBar;
    DrawerLayout drawerLayout;
    ListView dlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_home);
         actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewpager = (ViewPager) findViewById(R.id.pager);
        ActionBar.Tab trending = actionBar.newTab();
        trending.setText(" Trending");
        trending.setIcon(R.mipmap.ic_trending);
        trending.setTabListener(this);
        ActionBar.Tab pinned = actionBar.newTab();
        pinned.setText(" Pinned");
        pinned.setIcon(R.mipmap.ic_pinned);
        // pinned.setIcon(R.mipmap.ic_pinned);
        pinned.setTabListener(this);
        ActionBar.Tab answer = actionBar.newTab();
        answer.setText(" Answer");
        answer.setIcon(R.mipmap.ic_answer);
        answer.setTabListener(this);
        actionBar.addTab(trending);
        actionBar.addTab(pinned);
        actionBar.addTab(answer);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
dlist=(ListView)findViewById(R.id.left_drawer);
        viewpager=(ViewPager)findViewById(R.id.pager);
        viewpager.setAdapter(new FragmentPageAdapter(getSupportFragmentManager(),HomeActivity.this));
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }
    @Override
          public boolean onOptionsItemSelected(MenuItem item) {
        return menuChoice(item);
    }



    private boolean menuChoice(MenuItem item) {
        switch(item.getItemId()){


        }
        return false;
    }


}
