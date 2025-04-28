package com.nhanph.doanandroid.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.entities.Pin;

import java.util.List;

public class PinAdapter extends RecyclerView.Adapter<PinAdapter.PinViewHolder> {

    private Context context;
    private List<Pin> pinList;

    private final boolean pinInfoEnabled;

    public PinAdapter(Context context, List<Pin> pinList, boolean pinInfoEnabled) {
        this.context = context;
        this.pinList = pinList;
        this.pinInfoEnabled = pinInfoEnabled;
    }

    @NonNull
    @Override
    public PinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pin, parent, false);
        return new PinViewHolder(view, pinInfoEnabled);
    }

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
    }

    @Override
    public int getItemCount() {
        return pinList.size();
    }

    public static class PinViewHolder extends RecyclerView.ViewHolder{

        ImageView pin_image, avatar, action;
        TextView description;
        public PinViewHolder(@NonNull View itemView, boolean pinInfoEnabled) {
            super(itemView);
            pin_image = itemView.findViewById(R.id.pin_image);
            avatar = itemView.findViewById(R.id.avatar);
            description = itemView.findViewById(R.id.description);
            action = itemView.findViewById(R.id.action);
            if (!pinInfoEnabled){
                itemView.findViewById(R.id.pin_info).setVisibility(View.GONE);
            }
        }
    }
}
