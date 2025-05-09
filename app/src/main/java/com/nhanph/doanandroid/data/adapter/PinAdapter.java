package com.nhanph.doanandroid.data.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.apiservice.response.pin.PinResponse;
import com.nhanph.doanandroid.data.apiservice.response.user.UserProfileResponse;
import com.nhanph.doanandroid.data.apiservice.retrofit.ResponseApi;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.data.interfaces.OnPinClickListener;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinAdapter extends RecyclerView.Adapter<PinAdapter.PinViewHolder> {

    private final Context context;
    private List<Pin> pinList;

    private final boolean pinInfoEnabled;

    private OnPinClickListener onPinClickListener;

    public PinAdapter(Context context, List<Pin> pinList, boolean pinInfoEnabled, OnPinClickListener onPinClickListener) {
        this.context = context;
        this.pinList = pinList;
        this.pinInfoEnabled = pinInfoEnabled;
        this.onPinClickListener = onPinClickListener;
    }

    @NonNull
    @Override
    public PinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pin, parent, false);
        return new PinViewHolder(view, pinInfoEnabled, onPinClickListener);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull PinViewHolder holder, int position) {
        Pin pin = pinList.get(position);

        //@DrawableRes int resId = context.getResources().getIdentifier(pin.getImageUrl(), "drawable", context.getPackageName());
        Glide.with(context)
                .load(pin.getImageUrl())
                .override(400, 400)
                .fitCenter()
                .into(holder.pin_image);


        holder.description.setText(pin.getDescription());

        MainApplication.getApiService().getProfileById(pin.getUserId()).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseApi<UserProfileResponse>> call, Response<ResponseApi<UserProfileResponse>> response) {
                if (response.isSuccessful()) {
                    ResponseApi<UserProfileResponse> res = response.body();
                    if (res != null) {
                        UserProfileResponse userProfileResponse = res.getData();
                        if (userProfileResponse != null) {
                            Glide.with(context)
                                    .load(userProfileResponse.getAvatarUrl())
                                    .override(400, 400)
                                    .fitCenter()
                                    .into(holder.avatar);
                            Log.d("APISERVICE", "onResponse: " + userProfileResponse.getAvatarUrl());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<UserProfileResponse>> call, Throwable t) {
                Log.d("APISERVICE", "onFailure: " + t.getMessage() );
            }
        });

    }

    @Override
    public int getItemCount() {
        return pinList.size();
    }

    public static class PinViewHolder extends RecyclerView.ViewHolder{

        ImageView pin_image, avatar, action;
        TextView description;
        private boolean isLongPressed = false;
        GestureDetector gestureDetector;
        @SuppressLint("ClickableViewAccessibility")
        public PinViewHolder(@NonNull View itemView, boolean pinInfoEnabled, OnPinClickListener pinClickListener) {
            super(itemView);
            pin_image = itemView.findViewById(R.id.pin_image);
            avatar = itemView.findViewById(R.id.avatar);
            description = itemView.findViewById(R.id.description);
            action = itemView.findViewById(R.id.action);
            if (!pinInfoEnabled){
                itemView.findViewById(R.id.pin_info).setVisibility(View.GONE);
            }

            gestureDetector = new GestureDetector(itemView.getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                        pinClickListener.onPinClick(getBindingAdapterPosition());
                    }
                    return true;
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    disallowAllParentsInterceptTouchEvent(itemView, true);
                    if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                        isLongPressed = true;
                        pinClickListener.onPinLongClick(getBindingAdapterPosition(), e.getRawX(), e.getRawY());
                    }
                }
            });

            itemView.setOnTouchListener((v, event) -> {
                gestureDetector.onTouchEvent(event);


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (isLongPressed) {
                        isLongPressed = false;
                        disallowAllParentsInterceptTouchEvent(v, false);
                        pinClickListener.onPinLongClickRelease(getBindingAdapterPosition(), event.getRawX(), event.getRawY());
                    }
                    return true;
                }
                return true;
            });

        }
        private void disallowAllParentsInterceptTouchEvent(View view, boolean disallow)  {
            ViewParent parent = view.getParent();
            parent.requestDisallowInterceptTouchEvent(disallow);
        }

    }
}
