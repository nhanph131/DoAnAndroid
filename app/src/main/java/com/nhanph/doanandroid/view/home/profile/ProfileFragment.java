package com.nhanph.doanandroid.view.home.profile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayoutMediator;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.adapter.ProfilePagerAdapter;
import com.nhanph.doanandroid.data.interfaces.OnPinClickListener;
import com.nhanph.doanandroid.databinding.FragmentHomeProfileBinding;
import com.nhanph.doanandroid.view.home.feed.PinDetailFragment;
import com.nhanph.doanandroid.view.home.profile.profile_view.ProfileSettingFragment;

public class ProfileFragment extends Fragment {

    private ProfilePagerAdapter profilePagerAdapter;
    private OnPinClickListener pinClickListener;
    private FragmentHomeProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initEvent(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initView(View view) {
        pinClickListener = new OnPinClickListener() {
            @Override
            public void onPinClick(int position) {
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_right,  // enter
                                R.anim.slide_out_left,  // exit
                                R.anim.slide_in_left,   // pop enter (khi quay lại)
                                R.anim.slide_out_right  // pop exit
                        )
                        .add(binding.childFragment.getId(), new PinDetailFragment(), "pin-detail")
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onPinLongClick(int position) {

            }
        };
        profilePagerAdapter = new ProfilePagerAdapter(this, pinClickListener);
        binding.mainContent.setAdapter(profilePagerAdapter);
        MainApplication.loadAvatarIntoView(requireContext(), binding.avatar);

        new TabLayoutMediator(binding.options, binding.mainContent, ((tab, position) -> {
            if (position == 0){
                tab.setText("Pins");
            }
            else tab.setText("Boards");
        })).attach();
    }

    private void initEvent(View view) {
        binding.avatar.setOnClickListener(v -> {
            getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_right,  // enter
                            R.anim.slide_out_left,  // exit
                            R.anim.slide_in_left,   // pop enter (khi quay lại)
                            R.anim.slide_out_right  // pop exit
                    )
                    .add(binding.childFragment.getId(), new ProfileSettingFragment(), "profile_setting")
                    .addToBackStack(null)
                    .commit();
        });
    }

    public void resetToRoot() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager fm = getChildFragmentManager();

            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        }
    }

}
