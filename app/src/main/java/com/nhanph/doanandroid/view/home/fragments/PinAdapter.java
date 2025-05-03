package com.nhanph.doanandroid.view.home.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.entities.Pin;

import java.util.List;

// PinAdapter.java
public class PinAdapter extends ArrayAdapter<Pin> {
    private Context context;
    private List<Pin> pins;

    public PinAdapter(Context context, List<Pin> pins) {
        super(context, R.layout.item_pin, pins);
        this.context = context;
        this.pins = pins;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pin, parent, false);
        }

        Pin pin = pins.get(position);

        ImageView imgPin = convertView.findViewById(R.id.img_pin);
        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        TextView tvDescription = convertView.findViewById(R.id.tv_description);

        // Load ảnh từ Cloudinary bằng Glide
        Glide.with(context)
                .load(pin.getImageUrl()) // URL từ Cloudinary
                .placeholder(R.drawable.placeholder_image)
                .into(imgPin);

        tvTitle.setText(pin.getTitle());
        tvDescription.setText(pin.getDescription());

        return convertView;
    }
}