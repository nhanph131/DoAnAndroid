package com.nhanph.doanandroid.view.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.adapter.HomePagerAdapter;
import com.nhanph.doanandroid.databinding.ActivityHomeBinding;


public class HomeActivity extends AppCompatActivity {

    private HomeViewModel viewModel;
    private ActivityHomeBinding binding;
    private final String[] quickFilters = new String[] { "All", "Anime" };

    private final String[] floatingDock = new String[] { "Home", "Find", "Add", "Profile" };

    private HomePagerAdapter pagerAdapter;

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

    private void initEvent(){

    }
}
