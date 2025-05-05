package com.nhanph.doanandroid.data.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nhanph.doanandroid.view.home.create.CreateNewFragment;
import com.nhanph.doanandroid.view.home.feed.ForYouFragment;
import com.nhanph.doanandroid.view.home.profile.ProfileFragment;
import com.nhanph.doanandroid.view.home.search.SearchFragment;

public class HomePagerAdapter extends FragmentStateAdapter {
    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new SearchFragment();
            case 2:
                return new CreateNewFragment();
            case 3:
                return new ProfileFragment() ;
            default:
                return new ForYouFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Số tab
    }
}
