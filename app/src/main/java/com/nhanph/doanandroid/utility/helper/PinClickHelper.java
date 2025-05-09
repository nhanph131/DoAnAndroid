package com.nhanph.doanandroid.utility.helper;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.view.home.ShareDataHomeViewModel;
import com.nhanph.doanandroid.view.home.feed.PinDetailFragment;

public class PinClickHelper {
    public static void handlePinClick(Fragment root, int id, Pin pin) {
        PinDetailFragment pinDetailFragment = new PinDetailFragment();
        pinDetailFragment.setPin(pin);

        root.getChildFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,  // enter
                        R.anim.slide_out_left,  // exit
                        R.anim.slide_in_left,   // pop enter (khi quay lại)
                        R.anim.slide_out_right  // pop exit
                )
                .add(id, pinDetailFragment, "pin-detail")
                .addToBackStack(null)
                .commit();
    }

    public static void handlePinLongClick(Pin pin, ShareDataHomeViewModel viewModel, RecyclerView recyclerView, int position, float x, float y) {
        View pinView = recyclerView.getLayoutManager().findViewByPosition(position);


        viewModel.showHiddenFrame(pin, x, y);

        if (pinView != null) {
            pinView.animate().scaleX(0.975f).scaleY(0.975f).setDuration(200).start();
            pinView.setElevation(12f);
        }
    }

    public static void handlePinLongClickRelease(Pin pin, ShareDataHomeViewModel viewModel, RecyclerView recyclerView, int position, float x, float y) {
        View pinView = recyclerView.getLayoutManager().findViewByPosition(position);

        viewModel.setPinReleaseState(pin, x, y);

        assert pinView != null;
        pinView.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
        pinView.setElevation(0f);
        viewModel.hideHiddenFrame();
    }
}
