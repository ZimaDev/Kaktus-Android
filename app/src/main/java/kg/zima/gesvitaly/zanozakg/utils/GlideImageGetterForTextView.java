package kg.zima.gesvitaly.zanozakg.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.HashSet;
import java.util.Set;

import kg.zima.gesvitaly.zanozakg.R;

public final class GlideImageGetterForTextView implements Html.ImageGetter, Drawable.Callback {

    private final Context mContext;

    private final TextView mTextView;

    private final Set<ImageGetterViewTarget> mTargets;

    private boolean needResizeImage = true;

    public GlideImageGetterForTextView(Context context, TextView textView, boolean needResizeImage) {
        this.needResizeImage = needResizeImage;
        this.mContext = context;
        this.mTextView = textView;

        clear(); // Cancel all previous request
        mTargets = new HashSet<>();
        mTextView.setTag(R.id.drawable_callback_tag, this);
    }

    public GlideImageGetterForTextView(Context context, TextView textView) {
        this.mContext = context;
        this.mTextView = textView;

        clear(); // Cancel all previous request
        mTargets = new HashSet<>();
        mTextView.setTag(R.id.drawable_callback_tag, this);
    }

    public static GlideImageGetterForTextView get(View view) {
        return (GlideImageGetterForTextView)view.getTag(R.id.drawable_callback_tag);
    }

    public void clear() {
        GlideImageGetterForTextView prev = get(mTextView);
        if (prev == null) return;

        for (ImageGetterViewTarget target : prev.mTargets) {
            System.out.println("Cleared!");
            Glide.clear(target);
        }
    }
    @Override
    public Drawable getDrawable(String url) {
        final UrlDrawable urlDrawable = new UrlDrawable();

        System.out.println("Downloading from: " + url);
        Glide.with(mContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new ImageGetterViewTarget(mTextView, urlDrawable));

        return urlDrawable;
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        mTextView.invalidate();
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

    }

    private class ImageGetterViewTarget extends ViewTarget<TextView, GlideDrawable> {

        private final UrlDrawable mDrawable;
        private Request request;

        private ImageGetterViewTarget(TextView view, UrlDrawable drawable) {
            super(view);
            mTargets.add(this); // Add ViewTarget into Set
            this.mDrawable = drawable;
        }

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            // Resize images - Scale image proportionally to fit TextView width
            float width;
            float height;
            float tinyPicHeight = mContext.getResources().getDimensionPixelSize(R.dimen.tiny_pic_height);
            float smallPicHeight = mContext.getResources().getDimensionPixelSize(R.dimen.small_pic_height);
            int imgWidth = resource.getIntrinsicWidth();
            int viewWidth = getView().getWidth();

            // Если ширина картинка больше ширины вьюшки, то уменьшить ее до ширины вьюшки
            if (imgWidth >= viewWidth) {
                float downScale = (float) imgWidth / viewWidth;
                width = (float) imgWidth / downScale;
                height = (float) resource.getIntrinsicHeight() / downScale;
            } else {
                // Если картинка - очень маленькая, то увеличить ее до tinyPicHeight
                if (needResizeImage && resource.getIntrinsicHeight() < tinyPicHeight) {
                    float multiplier = tinyPicHeight / resource.getIntrinsicHeight();
                    width = (float) imgWidth * multiplier;
                    height = (float) resource.getIntrinsicHeight() * multiplier;
                }
                // Если картинка - маленькая, то увеличить ее до tinyPicHeight
                else if (needResizeImage && resource.getIntrinsicHeight() < smallPicHeight) {
                    float multiplier = smallPicHeight / resource.getIntrinsicHeight();
                    width = (float) imgWidth * multiplier;
                    height = (float) resource.getIntrinsicHeight() * multiplier;
                }
                // Иначе увеличить картинку до ширины вьюшки
                else {
                    float multiplier = (float) viewWidth / imgWidth;
                    width = (float) imgWidth * multiplier;
                    height = (float) resource.getIntrinsicHeight() * multiplier;
                }
            }
            Rect rect = new Rect(0, 0, Math.round(width), Math.round(height));

            resource.setBounds(rect);

            mDrawable.setBounds(rect);
            mDrawable.setDrawable(resource);

            if (resource.isAnimated()) {
                // set callback to drawable in order to
                // signal its container to be redrawn
                // to show the animated GIF

                mDrawable.setCallback(get(getView()));
                resource.setLoopCount(GlideDrawable.LOOP_FOREVER);
                resource.start();
            }

            getView().setText(getView().getText());
            getView().invalidate();
        }

        @Override
        public Request getRequest() {
            return request;
        }

        @Override
        public void setRequest(Request request) {
            this.request = request;
        }
    }
}
