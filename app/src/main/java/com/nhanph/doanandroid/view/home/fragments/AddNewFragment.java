package com.nhanph.doanandroid.view.home.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.view.home.CreateNew.CreateBoardActivity;
import com.nhanph.doanandroid.view.home.CreateNew.CreatePinActivity;

public class AddNewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_addnew, container, false);

        LinearLayout btnCreatePin = view.findViewById(R.id.btn_create_pin);
        LinearLayout btnCreateBoard = view.findViewById(R.id.btn_create_board);

        btnCreatePin.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CreatePinActivity.class));
            getActivity().finish();
        });

        btnCreateBoard.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CreateBoardActivity.class));
            getActivity().finish();
        });

        return view;
    }
}
