package com.nhanph.doanandroid.view.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.adapter.HomePagerAdapter;
import com.nhanph.doanandroid.databinding.ActivityHomeBinding;
import com.nhanph.doanandroid.view.home.profile.ProfileFragment;


public class HomeActivity extends AppCompatActivity {

    private HomeViewModel viewModel;
    private ActivityHomeBinding binding;
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
                    //tab.setText(floatingDock[0]);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_black_search);
                    //tab.setText(floatingDock[1]);
                    break;
                case 2:
                    tab.setIcon(R.drawable.ic_black_add);
                    //tab.setText(floatingDock[2]);
                    break;
                case 3:
                    Bitmap bitmap = MainApplication.loadAvatarFromInternalStorage(this);

                    View tabView = LayoutInflater.from(this).inflate(R.layout.tablayout_tab_custom, null);
                    ImageView avatar = tabView.findViewById(R.id.tab_image);

                    avatar.setImageBitmap(bitmap);

                    tab.setCustomView(tabView);

                    break;
                default:
                    break;
            }
        }).attach();
    }

    private void initEvent(){
        binding.floatingDock.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            int lastTabSelectedIndex = -1;
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                lastTabSelectedIndex = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 3 /* ví dụ tab Profile là tab 2 */) {
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("f3"); // vị trí tab = f{index}
                    if (fragment instanceof ProfileFragment) {
                        ((ProfileFragment) fragment).resetToRoot();
                    }
                }
            }
        });
    }
}
