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

import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.adapter.PinAdapter;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.data.interfaces.OnPinClickListener;
import com.nhanph.doanandroid.databinding.FragmentHomeForyouBinding;

import java.util.ArrayList;
import java.util.List;

public class ForYouFragment extends Fragment {
    private ForYouViewModel viewModel;
    private List<Pin> pinList = new ArrayList<>();

    private PinAdapter pinAdapter;
    private FragmentHomeForyouBinding binding;

    private OnPinClickListener onPinClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeForyouBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(ForYouViewModel.class);

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
                PinDetailFragment pinDetailFragment = new PinDetailFragment();
                pinDetailFragment.setPin(pinList.get(position));

                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_right,  // enter
                                R.anim.slide_out_left,  // exit
                                R.anim.slide_in_left,   // pop enter (khi quay lại)
                                R.anim.slide_out_right  // pop exit
                        )
                        .add(binding.pinDetailContainer.getId(), pinDetailFragment, "pin-detail")
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onPinLongClick(int position) {

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
