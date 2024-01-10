package com.example.bellyfull.modules.visualisation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bellyfull.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BabyVisualisationFragment extends Fragment {
    public BabyVisualisationFragment() {
        super(R.layout.fragment_baby_visualisation);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baby_visualisation, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = view.findViewById(R.id.graphic_viewpager);

        BabyPagerAdapter adapter = new BabyPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        final String[] tabTitles = {"Graphic WHO", "Graphic CDC"};

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();

        return view;
    }

    private class BabyPagerAdapter extends FragmentStateAdapter {
        private final int NUM_PAGES = 2;
        BabyPagerAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
           switch(position) {
               case 0:
                   return new WHOgraphicFragment();
               case 1:
                   return new CDCgraphicFragment();
               default:
                   return new WHOgraphicFragment();
           }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
