package com.hemant.bountyapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.hemant.bountyapp.R;
import com.hemant.bountyapp.adapters.ViewPagerAdapter;

/**
 * Created by hemant on 5/11/2017.
 */

public class DashboardFragment extends Fragment {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new SummaryFragment(
                ContextCompat.getColor(getContext(), R.color.colorAccent)), "Summary");
        adapter.addFrag(new DetailsFragment(
                ContextCompat.getColor(getContext(), R.color.colorAccent)), "Details");
        adapter.addFrag(new AddDataFragment(
                ContextCompat.getColor(getContext(), R.color.colorAccent)), "Add");
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(-1);
    }
}
