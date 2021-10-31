package kg.zima.gesvitaly.zanozakg.flycobannerimpl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;

import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;
import kg.zima.gesvitaly.zanozakg.photoView.PhotoView;
import kg.zima.gesvitaly.zanozakg.utils.FullscreenUtils;

public class FullscreenImageSlider extends BaseIndicatorBanner<SliderItem, FullscreenImageSlider> {
    private ColorDrawable colorDrawable;
    private ViewGroup fullscreenSliderWrapper;

    public void setFullscreenSliderWrapper(ViewGroup fullscreenSliderWrapper) {
        this.fullscreenSliderWrapper = fullscreenSliderWrapper;
    }

    public FullscreenImageSlider(Context context) {
        this(context, null, 0);
    }

    public FullscreenImageSlider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullscreenImageSlider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void setCurrentIndicator(int position) {
        super.setCurrentIndicator(position);
    }

    @Override
    public View onCreateItemView(int position) {
        ViewGroup itemView = (ViewGroup) View.inflate(mContext, R.layout.fullscreen_image_slider_item, null);
        PhotoView iv = (PhotoView) itemView.findViewById(R.id.image_slider_iv);
//        LinearLayout fullscreenImageWrapper = (LinearLayout) itemView.findViewById(R.id.fullscreen_image_wrapper);



        final SliderItem item = mDatas.get(position);
        String imgUrl = item.getImgUrl();

        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(mContext)
                    .load(imgUrl)
                    .placeholder(colorDrawable)
                    .fitCenter()
                    .into(iv);
        } else {
            iv.setImageDrawable(colorDrawable);
        }

        if (Build.VERSION.SDK_INT > 19) {
            FullscreenUtils.fullScreen(mContext, fullscreenSliderWrapper);
            iv.setOnViewTapListener((view, x, y) -> FullscreenUtils.fullScreen(mContext, fullscreenSliderWrapper));
        }
        else {
            ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
            if (actionBar != null) actionBar.hide();
            ((MainActivity) mContext).hideShadowView();
        }

        return itemView;
    }
}
