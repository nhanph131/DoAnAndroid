package com.nhanph.doanandroid.view.home.profile.profile_child_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.nhanph.doanandroid.data.adapter.PinAdapter;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.data.interfaces.OnPinClickListener;
import com.nhanph.doanandroid.databinding.FragmentHomeProfilePinBinding;
import com.nhanph.doanandroid.utility.helper.PinClickHelper;
import com.nhanph.doanandroid.view.home.ShareDataHomeViewModel;
import com.nhanph.doanandroid.view.home.profile.ShareDataViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfilePinFragment extends Fragment {

    private ProfilePinViewModel viewModel;

    private ShareDataViewModel shareDataViewModel;

    private ShareDataHomeViewModel shareDataHomeViewModel;
    private List<Pin> pinList = new ArrayList<>();

    private PinAdapter pinAdapter;
    private FragmentHomeProfilePinBinding binding;

    private OnPinClickListener pinClickListener;

    private final int gridLayout;
    private final  boolean pinInfoEnabled;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeProfilePinBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(ProfilePinViewModel.class);
        shareDataViewModel = new ViewModelProvider(requireActivity()).get(ShareDataViewModel.class);
        shareDataHomeViewModel = new ViewModelProvider(requireActivity()).get(ShareDataHomeViewModel.class);

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

    public ProfilePinFragment(int gridLayout, boolean pinInfoEnabled){
        super();
        this.gridLayout = gridLayout;
        this.pinInfoEnabled = pinInfoEnabled;
    }

    private void initView(View view) {
        pinClickListener = new OnPinClickListener() {
            @Override
            public void onPinClick(int position) {
                shareDataViewModel.setSelectedPin(pinList.get(position));
            }

            @Override
            public void onPinLongClick(int position, float x, float y) {
                Pin pin = pinList.get(position);

                PinClickHelper.handlePinLongClick(pin, shareDataHomeViewModel, binding.pinRecyclerView, position, x, y);
            }

            @Override
            public void onPinLongClickRelease(int position, float x, float y) {
                Pin pin = pinList.get(position);
                PinClickHelper.handlePinLongClickRelease(pin, shareDataHomeViewModel, binding.pinRecyclerView, position, x, y);
            }

        };
        setRecyclerView();
        observedRegister();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            viewModel.getList();
        }, 300);
        disableNestedScroll();
    }

    private void initEvent(View view) {
        binding.swipeRefresh.setOnRefreshListener(() -> {
           viewModel.getList();
           binding.swipeRefresh.setRefreshing(false);
        });
    }


    @SuppressLint("NotifyDataSetChanged")
    private void observedRegister(){
        viewModel.getListPin().observe(getViewLifecycleOwner(), pins -> {
            pinList.clear();
            pinList.addAll(pins);
            pinAdapter.notifyDataSetChanged();
        });

        viewModel.getNotification().observe(getViewLifecycleOwner(), notification -> {
            switch (notification) {
                case GET_LIST_PIN_SUCCESS:
                    //Toast.makeText(requireActivity(), "OKE", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(requireActivity(), notification.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    public void setRecyclerView(){

        pinAdapter = new PinAdapter(requireActivity(), pinList, pinInfoEnabled, pinClickListener);

        binding.pinRecyclerView.setAdapter(pinAdapter);

        binding.pinRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(gridLayout, StaggeredGridLayoutManager.VERTICAL));
    }

    public void disableNestedScroll() {
        binding.pinRecyclerView.setNestedScrollingEnabled(false);
    }

}
