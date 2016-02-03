package com.gang.micro.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gang.micro.R;
import com.gang.micro.core.gallery.GalleryWrapper;
import com.gang.micro.core.gallery.LocalGalleryFragment;
import com.gang.micro.core.gallery.ViewAnalysisFragment;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.microscope.MicroscopesFragment;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity implements GalleryWrapper {

    static final boolean SHOWING_GALLERY = true;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MicroscopesFragment(), getResources().getString(R.string.microscope_tab));
        adapter.addFragment(new LocalGalleryFragment(), getResources().getString(R.string.gallery_tab));
        state = SHOWING_GALLERY;

        viewPager.setAdapter(adapter);
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
            if (object.getClass() != fragmentChange.getClass() )
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

    @Override
    public void deleteImage() {
    }

    @Override
    public void showImage(Image image) {
        ViewAnalysisFragment viewAnalysisFragment = new ViewAnalysisFragment();
        viewAnalysisFragment.setImage(image);
        adapter.switchFragment(1,viewAnalysisFragment);
        state = !SHOWING_GALLERY;
    }

    @Override
    public void closeImage() {
        adapter.switchFragment(1,new LocalGalleryFragment());
        state = SHOWING_GALLERY;
    }

    @Override
    public void onBackPressed() {
        if (state != SHOWING_GALLERY)
            closeImage();
        else {
            super.onBackPressed();
        }
    }
}