package com.hemant.bountyapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hemant.bountyapp.R;
import com.hemant.bountyapp.adapters.ViewPagerAdapter;

/**
 * Created by hemant on 5/11/2017.
 */

public class AddDataFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    int color;
    public AddDataFragment(){

    }
    @SuppressLint("ValidFragment")
    public AddDataFragment(int color){
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_expense,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.view_pager_add_frag);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout_add_frg);
        setupViewPager(viewPager);
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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
      //  ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new IncomeFragment(
                ContextCompat.getColor(getContext(),R.color.colorAccent)), "Income");
        adapter.addFrag(new ExpenseFragment(
                ContextCompat.getColor(getContext(),R.color.colorAccent)), "Expense");
        viewPager.setAdapter(adapter);
    }
}