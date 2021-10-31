package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.flycobannerimpl.ImageSlider;

class CarouselVH extends RecyclerView.ViewHolder {
    ImageSlider imageSlider;
    TextView sliderTextIndicator;

    CarouselVH(View itemView) {
        super(itemView);
        imageSlider = (ImageSlider) itemView.findViewById(R.id.image_slider);
        sliderTextIndicator = (TextView) itemView.findViewById(R.id.slider_text_indicator);
    }
}
