package com.nhanph.doanandroid.view.home.profile.profile_view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.adapter.ProfileViewPagerAdapter;
import com.nhanph.doanandroid.databinding.FragmentHomeProfileviewBinding;

public class ProfileViewFragment extends Fragment {

    private ProfileViewPagerAdapter profileviewPagerAdapter;
    private FragmentHomeProfileviewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeProfileviewBinding.inflate(inflater, container, false);

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
        profileviewPagerAdapter = new ProfileViewPagerAdapter(this);
        binding.mainContent.setAdapter(profileviewPagerAdapter);

        MainApplication.loadAvatarIntoView(requireContext(), binding.avatar);

        new TabLayoutMediator(binding.tabFilter, binding.mainContent, ((tab, position) -> {
            if (position == 0){
                tab.setText("Created");
            }
            else tab.setText("Saved");
        })).attach();
    }

    private void initEvent(View view) {
        binding.backBtn.setOnClickListener(v -> backToParentFragment());

        binding.backBtnCopy.setOnClickListener(v -> backToParentFragment());

        ProfilePinFragment profilePinFragment = (ProfilePinFragment) profileviewPagerAdapter.getFragmentAt(0);

        binding.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (binding.scrollView.isHeaderSticky()){
                profilePinFragment.enableNestedScroll();
                binding.backBtnCopy.setVisibility(View.VISIBLE);
            }
            else {
                profilePinFragment.disableNestedScroll();
                binding.backBtnCopy.setVisibility(View.GONE);
            }
        });
    }

    private void backToParentFragment(){
        getParentFragmentManager().popBackStack();
    }
}
