package com.nhanph.doanandroid.view.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.adapter.HomePagerAdapter;
import com.nhanph.doanandroid.databinding.ActivityHomeBinding;
import com.nhanph.doanandroid.utility.helper.PinActionHelper;
import com.nhanph.doanandroid.view.home.profile.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private ShareDataHomeViewModel shareDataHomeViewModel;
    private ActivityHomeBinding binding;
    private HomePagerAdapter pagerAdapter;

    private final float sin225 = 76.5f;
    private final float cos225 = 184.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initEvent();
    }

    private void initView() {
        shareDataHomeViewModel = new ViewModelProvider(this).get(ShareDataHomeViewModel.class);

        pagerAdapter = new HomePagerAdapter(this);
        binding.mainContent.setAdapter(pagerAdapter);

        setupTabs();
    }

    private void setupTabs() {
        new TabLayoutMediator(binding.floatingDock, binding.mainContent, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_black_home);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_black_search);
                    break;
                case 2:
                    tab.setIcon(R.drawable.ic_black_add);
                    break;
                case 3:
                    setupProfileTab(tab);
                    break;
            }
        }).attach();
    }

    private void setupProfileTab(TabLayout.Tab tab) {
        try {
            View tabView = LayoutInflater.from(this).inflate(R.layout.tablayout_tab_custom, null);
            ImageView avatar = tabView.findViewById(R.id.tab_image);
            MainApplication.loadAvatarIntoView(this, avatar);
            tab.setCustomView(tabView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initEvent() {
        observePinLongClick();
        observePinRelease();
        setupTabReselect();
    }

    private void observePinLongClick() {
        shareDataHomeViewModel.getPinLongClickState().observe(this, state -> {
            if (!state.visible) {
                binding.hiddenFrame.setVisibility(View.GONE);
                return;
            }

            binding.hiddenFrame.setVisibility(View.VISIBLE);
            binding.hiddenFrame.setElevation(8f);

            binding.hiddenFrame.setOnClickListener(v -> binding.hiddenFrame.setVisibility(View.GONE));

            int[] location = new int[2];
            binding.hiddenFrame.getLocationOnScreen(location);
            float relativeX = state.x - location[0];
            float relativeY = state.y - location[1];

            showFloatingButtons(relativeX, relativeY);
        });
    }

    private void showFloatingButtons(float x, float y) {
        animateFloatingButton(binding.hideButton, x, y, -sin225, -cos225);
        animateFloatingButton(binding.saveButton, x, y, sin225, -cos225);
        animateFloatingButton(binding.shareButton, x, y, cos225, -sin225);
        animateFloatingButton(binding.likeButton, x, y, cos225, sin225);
    }

    private void animateFloatingButton(CardView button, float startX, float startY, float dx, float dy) {
        button.post(() -> {
            float halfWidth = button.getWidth() / 2f;
            float halfHeight = button.getHeight() / 2f;

            button.setTranslationX(startX - halfWidth);
            button.setTranslationY(startY - halfHeight);
            button.setVisibility(View.VISIBLE);
            button.animate().translationXBy(dx).translationYBy(dy).setDuration(300).start();
        });
    }

    private void observePinRelease() {
        shareDataHomeViewModel.getPinReleaseState().observe(this, state -> {
            if (state.pin == null) {
                binding.hiddenFrame.setVisibility(View.GONE);
                return;
            }

            if (isTouching(binding.hideButton, state.x, state.y)) {
                showToast("Hide");
            } else if (isTouching(binding.saveButton, state.x, state.y)) {
                showToast("Save");
                PinActionHelper.savePin(state.pin, null);
            } else if (isTouching(binding.shareButton, state.x, state.y)) {
                showToast("Share");
            } else if (isTouching(binding.likeButton, state.x, state.y)) {
                showToast("Like");
            } else {
                binding.hiddenFrame.setVisibility(View.GONE);
            }
        });
    }

    private void setupTabReselect() {
        binding.floatingDock.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {}

            @Override public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 3) { // Profile tab
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("f3");
                    if (fragment instanceof ProfileFragment) {
                        ((ProfileFragment) fragment).resetToRoot();
                    }
                }
            }
        });
    }

    private boolean isTouching(View view, float x, float y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];
        int right = left + view.getWidth();
        int bottom = top + view.getHeight();

        return (x >= left && x <= right && y >= top && y <= bottom);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
