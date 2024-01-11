package com.example.bellyfull.modules.visualisation.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.collection.BabyInfo;
import com.example.bellyfull.data.firebase.ports.dbBabyInfoCallback;
import com.example.bellyfull.data.firebase.repository.dbBabyInfoRepositoryImpl;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.example.bellyfull.Constant.preference_constant;

public class BabyVisualisationFragment extends Fragment {
    SharedPreferences preferences;

    dbBabyInfoCallback callback = new dbBabyInfoCallback() {
        @Override
        public void onSuccess(BabyInfo babyInfo) { updateUI(babyInfo); }
    };
    public BabyVisualisationFragment() {
        super(R.layout.fragment_baby_visualisation);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
        String userId = preferences.getString(preference_constant.pUserId, "");
        dbBabyInfoRepositoryImpl impl = new dbBabyInfoRepositoryImpl(getContext());
        impl.getBabyInfo(userId, callback);
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

    private void updateUI(BabyInfo babyInfo) {
        TextView TVFetalWeight = getView().findViewById(R.id.WeightText);
        TextView TVFetalHeight = getView().findViewById(R.id.HeightText);
        TextView TVHeadCircumference = getView().findViewById(R.id.HeadCircumferenceText);
        if (babyInfo != null) {
            Double Weight = babyInfo.getFetalWeight();
            Double Height = babyInfo.getFetalLength();
            Double HeadCircumference = babyInfo.getHeadCircumference();
            if (Weight != null) {
                TVFetalWeight.setText(String.valueOf(Weight));
            } else {
                TVFetalWeight.setText("");
            }
            if (Height != null) {
                TVFetalHeight.setText(String.valueOf(Height));
            } else {
                TVFetalHeight.setText("");
            }
            if (HeadCircumference != null) {
                TVHeadCircumference.setText(String.valueOf(HeadCircumference));
            } else {
                TVHeadCircumference.setText("");
            }
        } else {
            TVFetalWeight.setText("");
            TVFetalHeight.setText("");
            TVHeadCircumference.setText("");
        }

    }
}
