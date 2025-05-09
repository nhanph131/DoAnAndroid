package com.nhanph.doanandroid.data.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nhanph.doanandroid.view.home.profile.profile_child_fragment.ProfileBoardFragment;
import com.nhanph.doanandroid.view.home.profile.profile_child_fragment.ProfilePinFragment;
import com.nhanph.doanandroid.view.home.profile.profile_child_fragment.profile_setting_child.profile_view_child.CreatedPinFragment;
import com.nhanph.doanandroid.view.home.profile.profile_child_fragment.profile_setting_child.profile_view_child.SavedPinFragment;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    public ProfileViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
        fragmentList.add(new CreatedPinFragment(3, false));
        fragmentList.add(new SavedPinFragment(3 , false));
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
