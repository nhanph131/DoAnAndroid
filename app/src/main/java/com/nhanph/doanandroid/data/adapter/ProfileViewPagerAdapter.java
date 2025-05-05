package com.nhanph.doanandroid.data.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nhanph.doanandroid.view.home.profile.profile_view.ProfileBoardFragment;
import com.nhanph.doanandroid.view.home.profile.profile_view.ProfilePinFragment;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    public ProfileViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
        fragmentList.add(new ProfilePinFragment(3, false));
        fragmentList.add(new ProfileBoardFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return 2; // Số tab
    }

    public Fragment getFragmentAt(int position) {
        return fragmentList.get(position);
    }
}
