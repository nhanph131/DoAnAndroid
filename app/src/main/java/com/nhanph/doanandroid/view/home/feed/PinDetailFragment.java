package com.nhanph.doanandroid.view.home.feed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.adapter.PinAdapter;
import com.nhanph.doanandroid.data.apiservice.response.pin.PinDetailResponse;
import com.nhanph.doanandroid.data.apiservice.retrofit.ResponseApi;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.data.interfaces.OnPinClickListener;
import com.nhanph.doanandroid.databinding.FragmentHomeFeedPindetailBinding;
import com.nhanph.doanandroid.utility.helper.PinClickHelper;
import com.nhanph.doanandroid.view.home.ShareDataHomeViewModel;
import com.nhanph.doanandroid.view.home.profile.profile_child_fragment.profile_setting_child.ProfileViewFragment;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinDetailFragment extends Fragment {

    private PinDetailViewModel viewModel;

    private ShareDataHomeViewModel shareDataHomeViewModel;
    private List<Pin> pinList = new ArrayList<>();

    private PinAdapter pinAdapter;
    private FragmentHomeFeedPindetailBinding binding;

    private OnPinClickListener onPinClickListener;

    @Setter
    private Pin pin;

    boolean isSaved;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeFeedPindetailBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(PinDetailViewModel.class);

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
        MainApplication.getApiService().getPinDetail(pin.getPinId(), MainApplication.getUid()).enqueue(new Callback<>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseApi<PinDetailResponse>> call, Response<ResponseApi<PinDetailResponse>> response) {
                if (response.isSuccessful()) {
                    ResponseApi<PinDetailResponse> res = response.body();
                    if (res != null) {
                        PinDetailResponse pinDetailResponse = res.getData();
                        if (pinDetailResponse != null) {
                            Glide.with(requireContext())
                                    .load(pinDetailResponse.getPinImageUrl())
                                    .fitCenter()
                                    .into(binding.pinImage);

                            Glide.with(requireContext())
                                    .load(pinDetailResponse.getAvatarUrl())
                                    .override(400, 400)
                                    .fitCenter()
                                    .into(binding.avatar);

                            binding.username.setText(pinDetailResponse.getUsername());
                            binding.followerCount.setText(pinDetailResponse.getFollowerCount() + " followers");
                            binding.likeCount.setText(pinDetailResponse.getLikeCount() + "");
                            binding.commentCount.setText(pinDetailResponse.getCommentCount() + " comments");
                            binding.saveBtn.setText(pinDetailResponse.isSaved() ? "Bỏ lưu" : "Lưu");
                            isSaved = pinDetailResponse.isSaved();

                            if (isSaved) {
                                binding.saveBtn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button_primary));
                            }


                            Log.d("APISERVICE_PINDETAIL", "onResponse: "+ pinDetailResponse.getPinId()+ "-" + MainApplication.getUid() + "-" + pinDetailResponse.isSaved());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<PinDetailResponse>> call, Throwable t) {
                Log.d("APISERVICE", "onFailure: " + t.getMessage() );
            }
        });

        onPinClickListener = new OnPinClickListener() {
            @Override
            public void onPinClick(int position) {
                PinClickHelper.handlePinClick(requireParentFragment(), binding.pinDetailContainer.getId(), pinList.get(position));
            }

            @Override
            public void onPinLongClick(int position, float x, float y) {
                Pin pin = pinList.get(position);

                PinClickHelper.handlePinLongClick(pin, shareDataHomeViewModel, binding.explorer, position, x, y);
            }

            @Override
            public void onPinLongClickRelease(int position, float x, float y) {
                Pin pin = pinList.get(position);
                PinClickHelper.handlePinLongClickRelease(pin, shareDataHomeViewModel, binding.explorer, position, x, y);
            }

        };

        setRecyclerView();
        observedRegister();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            viewModel.getList();
        }, 300);
    }

    private void initEvent(View view) {
        binding.backBtn.setOnClickListener(v -> {
            getParentFragmentManager().popBackStackImmediate();
        });

        binding.avatar.setOnClickListener(v -> {
            String userId = pin.getUserId();
            getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_right,  // enter
                            R.anim.slide_out_left,  // exit
                            R.anim.slide_in_left,   // pop enter (khi quay lại)
                            R.anim.slide_out_right  // pop exit
                    )
                    .add(binding.pinDetailContainer.getId(), new ProfileViewFragment(userId), "pin-detail")
                    .addToBackStack(null)
                    .commit();
        });

        binding.saveBtn.setOnClickListener(v -> {
            if (isSaved) {
                viewModel.unsavePin(pin);
                isSaved = false;
                binding.saveBtn.setText("Lưu");
                binding.saveBtn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.my_light_primary));

            } else {
                viewModel.savePin(pin);
                isSaved = true;
                binding.saveBtn.setText("Bỏ lưu");
                binding.saveBtn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button_primary));
            }
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

        binding.explorer.setAdapter(pinAdapter);

        binding.explorer.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
}
