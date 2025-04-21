package com.nhanph.doanandroid.data.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nhanph.doanandroid.view.home.fragments.CreateNewFragment;
import com.nhanph.doanandroid.view.home.fragments.ForYouFragment;
import com.nhanph.doanandroid.view.home.fragments.ProfileFragment;
import com.nhanph.doanandroid.view.home.fragments.SearchFragment;

public class HomePagerAdapter extends FragmentStateAdapter {
    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ForYouFragment(); // Tab "All"
            case 1:
                return new SearchFragment(); // Tab "Create"
            case 2:
                return new CreateNewFragment(); // Tab "Online Levels"
            case 3:
                return new ProfileFragment() ; // Tab "Multiplayer"
            default:
                return new ForYouFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Số tab
    }
}
