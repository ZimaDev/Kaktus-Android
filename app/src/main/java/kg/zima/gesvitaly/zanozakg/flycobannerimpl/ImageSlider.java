package kg.zima.gesvitaly.zanozakg.flycobannerimpl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;

import kg.zima.gesvitaly.zanozakg.R;

public class ImageSlider extends BaseIndicatorBanner<SliderItem, ImageSlider> {
    private ColorDrawable colorDrawable;

    public ImageSlider(Context context) {
        this(context, null, 0);
    }

    public ImageSlider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSlider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSelect(TextView tv, int position) {
        final SliderItem item = mDatas.get(position);
        tv.setText(item.getTitle());
    }

    @Override
    public View onCreateItemView(int position) {
        View itemView = View.inflate(mContext, R.layout.image_slider_item, null);
        ImageView iv = (ImageView) itemView.findViewById(R.id.image_slider_iv);

        final SliderItem item = mDatas.get(position);
        int itemWidth = mDisplayMetrics.widthPixels;
        int itemHeight = (int) (itemWidth * 360 * 1.0f / 640);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));

        String imgUrl = item.getImgUrl();

        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(mContext)
                    .load(imgUrl)
                    .override(itemWidth, itemHeight)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(colorDrawable)
                    .into(iv);
        } else {
            iv.setImageDrawable(colorDrawable);
        }

        return itemView;
    }
}
