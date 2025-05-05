package com.nhanph.doanandroid.data.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nhanph.doanandroid.data.interfaces.OnPinClickListener;
import com.nhanph.doanandroid.view.home.profile.profile_view.ProfileBoardFragment;
import com.nhanph.doanandroid.view.home.profile.profile_view.ProfilePinFragment;

public class ProfilePagerAdapter extends FragmentStateAdapter {
    public ProfilePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }
    private OnPinClickListener pinClickListener;
    public ProfilePagerAdapter(@NonNull Fragment fragment, OnPinClickListener pinClickListener){
        super(fragment);
        this.pinClickListener = pinClickListener;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new ProfileBoardFragment();
        }
        else{
            ProfilePinFragment profilePinFragment = new ProfilePinFragment(2, true);
            profilePinFragment.setPinClickListener(pinClickListener);

            return  profilePinFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Số tab
    }
}
