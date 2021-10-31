package kg.zima.gesvitaly.zanozakg.fragments;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;
import kg.zima.gesvitaly.zanozakg.photoView.PhotoView;
import kg.zima.gesvitaly.zanozakg.utils.FullscreenUtils;

public class FullscreenImageFragment extends BaseFragment {
    String src = "";

    public void setSrc(String src) {
        this.src = src;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        changeDrawerIconToBackArrow(mainActivity);
        changeArrowColor(mainActivity);

        View rootView = inflater.inflate(R.layout.fragment_fullscreen_image, container, false);
        PhotoView fullscreenImage = (PhotoView) rootView.findViewById(R.id.fullscreen_image);
        LinearLayout fullscreenImageWrapper = (LinearLayout) rootView.findViewById(R.id.fullscreen_image_wrapper);

        if (Build.VERSION.SDK_INT > 19) {
            FullscreenUtils.fullScreen(this.getContext(), fullscreenImageWrapper);
            fullscreenImage.setOnViewTapListener((view, x, y) -> FullscreenUtils.fullScreen(this.getContext(), fullscreenImageWrapper));
        }
        else {
            ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
            if (actionBar != null) actionBar.hide();
            ((MainActivity) getActivity()).hideShadowView();
        }

        if (!src.isEmpty()) {
            Glide.with(getContext())
                    .load(src)
                    .fitCenter()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(fullscreenImage);
        }
        return rootView;
    }




}
