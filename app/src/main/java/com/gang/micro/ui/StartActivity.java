package com.gang.micro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gang.micro.R;
import com.gang.micro.core.gallery.local.LocalGalleryFragment;
import com.gang.micro.core.microscope.MicroscopesFragment;
import com.gang.micro.core.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    static final boolean SHOWING_GALLERY = true;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    private ViewPagerAdapter adapter;

    private boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MicroscopesFragment(), getResources().getString(R.string.microscope_tab));
        adapter.addFragment(new LocalGalleryFragment(), getResources().getString(R.string.gallery_tab));
        state = SHOWING_GALLERY;

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsActivity = new Intent(StartActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private FragmentManager fm;
        private Fragment fragmentChange;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            fm = manager;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            if (object.getClass() != fragmentChange.getClass())
                return POSITION_NONE;
            return POSITION_UNCHANGED;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void switchFragment(int position, Fragment fragment) {
            fragmentChange = fragment;
            fm.beginTransaction()
                    .remove(mFragmentList.set(position, fragment))
                    .commit();
            notifyDataSetChanged();
        }
    }
}