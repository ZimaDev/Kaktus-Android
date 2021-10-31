package kg.zima.gesvitaly.zanozakg.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;
import kg.zima.gesvitaly.zanozakg.flycobannerimpl.FullscreenImageSlider;
import kg.zima.gesvitaly.zanozakg.flycobannerimpl.SliderItem;

public class CarouselFragment extends BaseFragment {
    private ArrayList<SliderItem> sliderList = new ArrayList<>();
    private int position;

    public void setSliderList(ArrayList<SliderItem> sliderList) {
        this.sliderList = sliderList;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            sliderList = savedInstanceState.getParcelableArrayList("sliderList");
            position = savedInstanceState.getInt("position");
        }

        MainActivity mainActivity = (MainActivity) getActivity();
        changeDrawerIconToBackArrow(mainActivity);
        changeArrowColor(mainActivity);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_carousel, container, false);
        FullscreenImageSlider fullscreenImageSlider = (FullscreenImageSlider) rootView.findViewById(R.id.fullscreen_carousel);
        TextView sliderTextIndicator = (TextView) rootView.findViewById(R.id.slider_text_indicator);
        fullscreenImageSlider.setFullscreenSliderWrapper(rootView);

        if (sliderList.isEmpty()) fullscreenImageSlider.setBackground(getResources().getDrawable(R.drawable.background_no_photo));

        if (sliderList.size() < 10) {
            fullscreenImageSlider
                    .setSource(sliderList)
                    .startScroll();
            sliderTextIndicator.setVisibility(View.GONE);
            if (position != 0) fullscreenImageSlider.setCurrentPosition(position);
        } else {
            fullscreenImageSlider
                    .setSource(sliderList)
                    .setIndicatorShow(false)
                    .startScroll();
            sliderTextIndicator.setVisibility(View.VISIBLE);
            if (position != 0) fullscreenImageSlider.setCurrentPosition(position);
            String indicatorText = fullscreenImageSlider.getCurrentPosition() + 1 + " / " + sliderList.size();
            sliderTextIndicator.setText(indicatorText);
        }

        fullscreenImageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                CarouselFragment.this.position = position;
                if (sliderList.size() >= 10) {
                    String s = position + 1 + " / " + sliderList.size();
                    sliderTextIndicator.setText(s);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("sliderList", sliderList);
        outState.putInt("position", position);
    }
}
