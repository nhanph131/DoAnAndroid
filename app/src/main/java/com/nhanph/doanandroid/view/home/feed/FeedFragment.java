package com.nhanph.doanandroid.view.home.feed;

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
import com.nhanph.doanandroid.databinding.FragmentHomeFeedBinding;
import com.nhanph.doanandroid.utility.helper.PinClickHelper;
import com.nhanph.doanandroid.view.home.ShareDataHomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {
    private FeedViewModel viewModel;
    private ShareDataHomeViewModel shareDataHomeViewModel;
    private List<Pin> pinList = new ArrayList<>();

    private PinAdapter pinAdapter;
    private FragmentHomeFeedBinding binding;

    private OnPinClickListener onPinClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeFeedBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
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
    private void initView(View view) {
        onPinClickListener = new OnPinClickListener() {
            @Override
            public void onPinClick(int position) {
                PinClickHelper.handlePinClick(FeedFragment.this, binding.pinDetailContainer.getId(), pinList.get(position));
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
        pinAdapter = new PinAdapter(requireActivity(), pinList, true, onPinClickListener);

        binding.pinRecyclerView.setAdapter(pinAdapter);

        binding.pinRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
}
