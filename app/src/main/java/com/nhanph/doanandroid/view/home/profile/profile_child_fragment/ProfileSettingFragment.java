package com.nhanph.doanandroid.view.home.profile.profile_child_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.databinding.FragmentHomeProfileSettingBinding;
import com.nhanph.doanandroid.databinding.ItemSettingBinding;
import com.nhanph.doanandroid.view.home.profile.profile_child_fragment.profile_setting_child.ProfileViewFragment;
import com.nhanph.doanandroid.view.login.LoginActivity;

public class ProfileSettingFragment extends Fragment {
    private ProfileSettingViewModel viewModel;
    private FragmentHomeProfileSettingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeProfileSettingBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ProfileSettingViewModel.class);

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
        binding.nickname.setText(MainApplication.getNickname());

        ItemSettingBinding profileBinding = binding.profileManagement;
        profileBinding.settingTitle.setText("Cài đặt trang cá nhân");

        ItemSettingBinding accountBinding = binding.accountManagement;
        accountBinding.settingTitle.setText("Cài đặt tài khoản");

        ItemSettingBinding switchAccountBinding = binding.switchAccount;
        switchAccountBinding.settingTitle.setText("Chuyển tài khoản");

        ItemSettingBinding addAccountBinding = binding.addAccount;
        addAccountBinding.settingTitle.setText("Thêm tài khoản");

        ItemSettingBinding logOutBinding = binding.logOut;
        logOutBinding.settingTitle.setText("Đăng xuất");

        MainApplication.loadAvatarIntoView(requireContext(), binding.avatar);
    }

    private void initEvent(View view){
        binding.backBtn.setOnClickListener(v -> {
            getParentFragmentManager().popBackStackImmediate();
        });

        binding.profileView.setOnClickListener(v ->{
            getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_right,  // enter
                            R.anim.slide_out_left,  // exit
                            R.anim.slide_in_left,   // pop enter (khi quay lại)
                            R.anim.slide_out_right  // pop exit
                    )
                    .add(binding.containerProfile.getId(), new ProfileViewFragment(MainApplication.getUid()), "profileview")
                    .addToBackStack(null)
                    .commit();
        });

        binding.logOut.settingTitle.setOnClickListener(v -> {
            MainApplication.logout();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}
