package com.apps.geo.notes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.geo.notes.MainActivity;
import com.apps.geo.notes.R;
import com.google.android.gms.maps.SupportMapFragment;

public class MainFragment extends Fragment{
    private SupportMapFragment mMapFragment;

    public MainFragment() {
    }

    private SupportMapFragment loadMap() {
        if (mMapFragment == null){
            mMapFragment = SupportMapFragment.newInstance();
            mMapFragment.getMapAsync((MainActivity) getActivity());
        }
        return mMapFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager, null);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(new NavigationAdapter(getFragmentManager()));
        pager.requestTransparentRegion(pager);
        return rootView;
    }

    private class NavigationAdapter extends FragmentPagerAdapter{

        NavigationAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new NoteListFragment();
                case 1:
                    return MainFragment.this.loadMap();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}