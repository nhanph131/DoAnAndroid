package com.nhanph.doanandroid.view.home.profile.profile_child_fragment.profile_setting_child;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.adapter.ProfileViewPagerAdapter;
import com.nhanph.doanandroid.data.apiservice.response.user.UserLoginResponse;
import com.nhanph.doanandroid.data.apiservice.response.user.UserProfileResponse;
import com.nhanph.doanandroid.data.apiservice.retrofit.ResponseApi;
import com.nhanph.doanandroid.databinding.FragmentHomeProfileviewBinding;
import com.nhanph.doanandroid.utility.helper.PinClickHelper;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;
import com.nhanph.doanandroid.view.home.feed.PinDetailFragment;
import com.nhanph.doanandroid.view.home.profile.profile_child_fragment.profile_setting_child.profile_view_child.CreatedPinFragment;
import com.nhanph.doanandroid.view.home.profile.profile_child_fragment.profile_setting_child.profile_view_child.SavedPinFragment;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewFragment extends Fragment {

    private ProfileViewPagerAdapter profileviewPagerAdapter;
    private FragmentHomeProfileviewBinding binding;

    private ProfileShareDataViewModel shareDataViewModel;

    private String userId;

    public ProfileViewFragment(String userId) {
        super();
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeProfileviewBinding.inflate(inflater, container, false);

        shareDataViewModel = new ViewModelProvider(requireActivity()).get(ProfileShareDataViewModel.class);

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

    private void getProfile() {
        MainApplication.getApiService().getProfileById(userId).enqueue(new Callback<>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseApi<UserProfileResponse>> call, Response<ResponseApi<UserProfileResponse>> response) {
                if (response.isSuccessful()) {
                    ResponseApi<UserProfileResponse> apiResponse = response.body();

                    if (apiResponse != null) {
                        if (apiResponse.isSuccess()) {
                            UserProfileResponse userProfileResponse = apiResponse.getData();
                            if (userProfileResponse != null) {
                                Glide.with(requireContext())
                                        .load(userProfileResponse.getAvatarUrl())
                                        .fitCenter()
                                        .into(binding.avatar);

                                binding.uid.setText(userProfileResponse.getUserId());
                                binding.nickname.setText(userProfileResponse.getNickname());
                                binding.bio.setText(userProfileResponse.getBio());

                                binding.followerCount.setText(userProfileResponse.getFollowerCount() + " followers - " + userProfileResponse.getFollowingCount() + " following" );

                                Log.d("APISERVICE_GETPROFILE", "onResponse: " + userProfileResponse.getAvatarUrl());
                            }
                        } else {
                            Log.d("APISERVICE_GETPROFILE", "Lay that bai: " + apiResponse.getMessage());
                        }
                    } else {
                        Log.d("APISERVICE_GETPR0FILE", "Response body null");
                    }
                } else {
                    Log.d("APISERVICE_GETPROFILE", "HTTP Error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ResponseApi<UserProfileResponse>> call, Throwable t) {
                Log.d("APISERVICE_GETPROFILE", "onFailure: " + t.getMessage());
            }
        });
    }

    private void initView(View view) {

        getProfile();

        shareDataViewModel.setUserId(userId);

        profileviewPagerAdapter = new ProfileViewPagerAdapter(this);
        binding.mainContent.setAdapter(profileviewPagerAdapter);

        binding.mainContent.setOffscreenPageLimit(profileviewPagerAdapter.getItemCount());

        new TabLayoutMediator(binding.tabFilter, binding.mainContent, ((tab, position) -> {
            if (position == 0){
                tab.setText("Created");
            }
            else tab.setText("Saved");
        })).attach();
    }

    private void initEvent(View view) {
        shareDataViewModel.getSelectedPin().observe(getViewLifecycleOwner(), pin -> {
                    if (pin != null) {
                        PinClickHelper.handlePinClick(this, binding.childFragment.getId(), pin);
                    }
                });

        binding.backBtn.setOnClickListener(v -> backToParentFragment());

        binding.backBtnCopy.setOnClickListener(v -> backToParentFragment());

        CreatedPinFragment createdPinFragment = (CreatedPinFragment) profileviewPagerAdapter.getFragmentAt(0);
        SavedPinFragment savedPinFragment = (SavedPinFragment) profileviewPagerAdapter.getFragmentAt(1);

        binding.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (binding.scrollView.isHeaderSticky()){
                createdPinFragment.enableNestedScroll();
                savedPinFragment.enableNestedScroll();
                binding.backBtnCopy.setVisibility(View.VISIBLE);
            }
            else {
                createdPinFragment.disableNestedScroll();
                savedPinFragment.disableNestedScroll();
                binding.backBtnCopy.setVisibility(View.GONE);
            }
        });
    }

    private void backToParentFragment(){
        getParentFragmentManager().popBackStack();
    }
}
