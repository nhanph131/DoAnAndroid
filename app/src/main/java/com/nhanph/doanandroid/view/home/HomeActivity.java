package com.nhanph.doanandroid.view.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.adapter.HomePagerAdapter;
import com.nhanph.doanandroid.databinding.ActivityHomeBinding;
import com.nhanph.doanandroid.view.home.fragments.SearchFragment;


public class HomeActivity extends AppCompatActivity {

    private HomeViewModel viewModel;
    private SearchFragment searchFragment;
    private ActivityHomeBinding binding;
    private final String[] floatingDock = new String[] { "Home", "Find", "Add", "Profile" };

    private HomePagerAdapter pagerAdapter;
    private com.nhanph.doanandroid.view.home.fragments.AddNewFragment addNewFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initEvent();
    }

    private void initView(){
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        pagerAdapter = new HomePagerAdapter(this);
        binding.mainContent.setAdapter(pagerAdapter);

        new TabLayoutMediator(binding.floatingDock, binding.mainContent, (tab, position) ->
        {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_black_home);
                    tab.setText(floatingDock[0]);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_black_search);
                    tab.setText(floatingDock[1]);
                    break;
                case 2:
                    tab.setIcon(R.drawable.ic_black_add);
                    tab.setText(floatingDock[2]);
                    break;
                case 3:
                    tab.setText(floatingDock[3]);
                    break;
                default:
                    break;
            }
        }).attach();
    }


    private void initEvent() {
        binding.floatingDock.getTabAt(2).view.setOnClickListener(v -> {
            if (binding.fragmentContainerOverlay.getVisibility() == View.VISIBLE) {
                hideAddNewFragment();
            } else {
                showAddNewFragment();
            }
        });
        // Thêm sự kiện cho tab Search (position 1)
        binding.floatingDock.getTabAt(1).view.setOnClickListener(v -> {
            if (searchFragment == null) {
                searchFragment = new SearchFragment();
            }

            if (binding.fragmentContainerOverlay.getVisibility() != View.VISIBLE) {
                showSearchFragment();
            } else {
                hideSearchFragment();
            }
        });
    }


    public void showAddNewFragment() {
        if (addNewFragment == null) {
            addNewFragment = new com.nhanph.doanandroid.view.home.fragments.AddNewFragment();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_overlay, addNewFragment);
        transaction.commit();

        binding.fragmentContainerOverlay.setVisibility(View.VISIBLE);
        binding.fragmentContainerOverlay.setAlpha(0f);
        binding.fragmentContainerOverlay.animate()
                .alpha(1f)
                .setDuration(300)
                .start();
    }

    public void hideAddNewFragment() {
        binding.fragmentContainerOverlay.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction(() -> {
                    binding.fragmentContainerOverlay.setVisibility(View.GONE);
                })
                .start();
    }

    @Override
    public void onBackPressed() {
        if (binding.fragmentContainerOverlay.getVisibility() == View.VISIBLE) {
            hideAddNewFragment();
        } else {
            super.onBackPressed();
        }
    }
    private void showSearchFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_overlay, searchFragment);
        transaction.commit();

        binding.fragmentContainerOverlay.setVisibility(View.VISIBLE);
        binding.fragmentContainerOverlay.setAlpha(0f);
        binding.fragmentContainerOverlay.animate()
                .alpha(1f)
                .setDuration(300)
                .start();
    }

    private void hideSearchFragment() {
        binding.fragmentContainerOverlay.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction(() -> {
                    binding.fragmentContainerOverlay.setVisibility(View.GONE);
                })
                .start();
    }
}