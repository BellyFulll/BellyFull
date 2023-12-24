package com.example.bellyfull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.bellyfull.modules.AdviceAndTips.Fragments.AdviceAndTipsFragment;
import com.example.bellyfull.modules.Authentication.Fragments.RegisterFragment;
import com.example.bellyfull.modules.HomeFragment;
import com.example.bellyfull.modules.PersonalisedNotifications.Fragments.CalendarFragment;
import com.example.bellyfull.modules.PregnancyTracking.Fragments.BabyVisualizationFragment;
import com.example.bellyfull.modules.PregnancyTracking.Fragments.MomVisualizationFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    AdviceAndTipsFragment adviceAndTipsFragment = new AdviceAndTipsFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    BabyVisualizationFragment babyVisualizationFragment = new BabyVisualizationFragment();
    MomVisualizationFragment momVisualizationFragment = new MomVisualizationFragment();
    HomeFragment homeFragment =new HomeFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,calendarFragment);

        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cal);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(3);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.cal) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, calendarFragment).commit();
                    return true;
                } else if (itemId == R.id.home){
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment).commit();
                        return true;
                }else if (itemId == R.id.tip){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,adviceAndTipsFragment).commit();
                    return true;
                } else if (itemId == R.id.baby) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,babyVisualizationFragment).commit();
                    return true;
                }else if (itemId == R.id.mom){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,momVisualizationFragment).commit();
                    return true;
                }

                return false;
            }
        });
    }

}