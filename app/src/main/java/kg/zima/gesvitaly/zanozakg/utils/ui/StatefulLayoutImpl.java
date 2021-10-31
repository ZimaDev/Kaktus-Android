package kg.zima.gesvitaly.zanozakg.utils.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.transition.TransitionManager;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cz.kinst.jakub.view.StatefulLayout;
import kg.zima.gesvitaly.zanozakg.R;

public class StatefulLayoutImpl extends StatefulLayout {

    private String mInitialState = State.CONTENT;
    private boolean mTransitionsEnabled = true;


    public StatefulLayoutImpl(Context context) {
        super(context);
        init(context, null);
    }


    public StatefulLayoutImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public StatefulLayoutImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    @Override
    protected void onSetupContentState() {
        super.onSetupContentState();
        if(mInitialState != null)
            setState(mInitialState);
    }


    @Override
    public void setState(String state) {
        if(isTransitionsEnabled())
            TransitionManager.beginDelayedTransition(this);
        super.setState(state);
    }


    public void showContent() {
        setState(State.CONTENT);
    }


    public void showProgress() {
        setState(State.PROGRESS);
    }


    public void showOffline() {
        setState(State.OFFLINE);
    }


    public void showEmpty() {
        setState(State.EMPTY);
    }


    public void showError() {
        setState(State.ERROR);
    }


    public void setTextAppearance(int textAppearance) {
        TextView offlineTextView = getOfflineView().findViewById(R.id.state_text);
        if(offlineTextView != null)
            TextViewCompat.setTextAppearance(offlineTextView, textAppearance);
        TextView emptyTextView = getEmptyView().findViewById(R.id.state_text);
        if(emptyTextView != null)
            TextViewCompat.setTextAppearance(emptyTextView, textAppearance);
        TextView errorTextView = getErrorView().findViewById(R.id.state_text);
        if(errorTextView != null)
            TextViewCompat.setTextAppearance(errorTextView, textAppearance);
    }


    public void setEmptyText(@StringRes int resourceId) {
        setEmptyText(getResources().getString(resourceId));
    }


    public void setEmptyText(CharSequence emptyText) {
        TextView emptyTextView = getEmptyView().findViewById(R.id.state_text);
        if(emptyTextView != null)
            emptyTextView.setText(emptyText);
    }


    public void setEmptyImageDrawable(Drawable drawable) {
        ImageView image = getEmptyView().findViewById(R.id.state_image);
        if(image != null) {
            image.setVisibility(drawable != null ? VISIBLE : GONE);
            image.setImageDrawable(drawable);
        }
    }


    public void setEmptyImageResource(@DrawableRes int resourceId) {
        setEmptyImageDrawable(ResourcesCompat.getDrawable(getResources(), resourceId, null));
    }


    public void setOfflineText(@StringRes int resourceId) {
        setOfflineText(getResources().getString(resourceId));
    }


    public void setOfflineText(CharSequence offlineText) {
        TextView offlineTextView = getOfflineView().findViewById(R.id.state_text);
        if(offlineTextView != null)
            offlineTextView.setText(offlineText);
    }


    public void setOfflineImageDrawable(Drawable drawable) {
        ImageView image = getOfflineView().findViewById(R.id.state_image);
        if(image != null) {
            image.setVisibility(drawable != null ? VISIBLE : GONE);
            image.setImageDrawable(drawable);
        }
    }


    public void setOfflineImageResource(@DrawableRes int resourceId) {
        setOfflineImageDrawable(ResourcesCompat.getDrawable(getResources(), resourceId, null));
    }


    public void setErrorText(@StringRes int resourceId) {
        setErrorText(getResources().getString(resourceId));
    }


    public void setErrorText(CharSequence offlineText) {
        TextView errorTextView = getErrorView().findViewById(R.id.state_text);
        if(errorTextView != null)
            errorTextView.setText(offlineText);
    }


    public void setErrorImageDrawable(Drawable drawable) {
        ImageView image = getErrorView().findViewById(R.id.state_image);
        if(image != null) {
            image.setVisibility(drawable != null ? VISIBLE : GONE);
            image.setImageDrawable(drawable);
        }
    }


    public void setErrorImageResource(@DrawableRes int resourceId) {
        setErrorImageDrawable(ResourcesCompat.getDrawable(getResources(), resourceId, null));
    }


    public void setTextColor(@ColorInt int color) {
        ViewCompat.setBackgroundTintList(getEmptyView().findViewById(R.id.state_image), ColorStateList.valueOf(color));
        ViewCompat.setBackgroundTintList(getOfflineView().findViewById(R.id.state_image), ColorStateList.valueOf(color));
        ViewCompat.setBackgroundTintList(getErrorView().findViewById(R.id.state_image), ColorStateList.valueOf(color));
        ((TextView) getEmptyView().findViewById(R.id.state_text)).setTextColor(color);
        ((TextView) getOfflineView().findViewById(R.id.state_text)).setTextColor(color);
        ((TextView) getErrorView().findViewById(R.id.state_text)).setTextColor(color);
    }


    public View getProgressView() {
        return getStateView(State.PROGRESS);
    }


    public void setProgressView(View progressView) {
        setStateView(State.PROGRESS, progressView);
    }


    public View getOfflineView() {
        return getStateView(State.OFFLINE);
    }


    public void setOfflineView(View offlineView) {
        setStateView(State.OFFLINE, offlineView);
    }


    public View getEmptyView() {
        return getStateView(State.EMPTY);
    }


    public void setEmptyView(View emptyView) {
        setStateView(State.EMPTY, emptyView);
    }


    public View getErrorView() {
        return getStateView(State.ERROR);
    }


    public void setErrorView(View emptyView) {
        setStateView(State.EMPTY, emptyView);
    }


    public boolean isTransitionsEnabled() {
        return mTransitionsEnabled;
    }


    public void setTransitionsEnabled(boolean transitionsEnabled) {
        mTransitionsEnabled = transitionsEnabled;
    }


    public void setOfflineRetryOnClickListener(OnClickListener listener) {
        getOfflineView().findViewById(R.id.state_button).setOnClickListener(listener);
    }


    public void setOfflineRetryText(@StringRes int textResourceId) {
        setOfflineText(getResources().getString(textResourceId));
    }


    public void setOfflineRetryText(CharSequence text) {
        Button button = getOfflineView().findViewById(R.id.state_button);
        if(button != null)
            button.setText(text);
    }


    public void setErrorRetryOnClickListener(OnClickListener listener) {
        getErrorView().findViewById(R.id.state_button).setOnClickListener(listener);
    }


    public void setErrorRetryText(@StringRes int textResourceId) {
        setErrorText(getResources().getString(textResourceId));
    }


    public void setErrorRetryText(CharSequence text) {
        Button button = getErrorView().findViewById(R.id.state_button);
        if(button != null)
            button.setText(text);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SflStatefulLayout);

        int offlineLayoutResource = a.getResourceId(R.styleable.SflStatefulLayout_offlineLayout, R.layout.sfl_placeholder_offline);
        int emptyLayoutResource = a.getResourceId(R.styleable.SflStatefulLayout_emptyLayout, R.layout.sfl_placeholder_empty);
        int progressLayoutResource = a.getResourceId(R.styleable.SflStatefulLayout_progressLayout, R.layout.sfl_placeholder_progress);
        int errorLayoutResource = a.getResourceId(R.styleable.SflStatefulLayout_errorLayout, R.layout.sfl_placeholder_error);

        setStateView(State.OFFLINE, LayoutInflater.from(getContext()).inflate(offlineLayoutResource, null));
        setStateView(State.EMPTY, LayoutInflater.from(getContext()).inflate(emptyLayoutResource, null));
        setStateView(State.PROGRESS, LayoutInflater.from(getContext()).inflate(progressLayoutResource, null));
        setStateView(State.ERROR, LayoutInflater.from(getContext()).inflate(errorLayoutResource, null));

        int textAppearance = a.getResourceId(R.styleable.SflStatefulLayout_stateTextAppearance, R.style.sflStateTextAppearance);
        setTextAppearance(textAppearance);

        // setState custom empty text
        if(a.hasValue(R.styleable.SflStatefulLayout_emptyText)) {
            setEmptyText(a.getString(R.styleable.SflStatefulLayout_emptyText));
        }

        // setState custom offline text
        if(a.hasValue(R.styleable.SflStatefulLayout_offlineText)) {
            setOfflineText(a.getString(R.styleable.SflStatefulLayout_offlineText));
        }

        // setState custom offline retry text
        if(a.hasValue(R.styleable.SflStatefulLayout_offlineRetryText)) {
            setOfflineText(a.getString(R.styleable.SflStatefulLayout_offlineRetryText));
        }

        // setState custom error retry text
        if(a.hasValue(R.styleable.SflStatefulLayout_offlineRetryText)) {
            setErrorText(a.getString(R.styleable.SflStatefulLayout_errorRetryText));
        }

        if(a.hasValue(R.styleable.SflStatefulLayout_offlineImageDrawable)) {
            setOfflineImageResource(a.getResourceId(R.styleable.SflStatefulLayout_offlineImageDrawable, 0));
        }

        if(a.hasValue(R.styleable.SflStatefulLayout_emptyImageDrawable)) {
            setEmptyImageResource(a.getResourceId(R.styleable.SflStatefulLayout_emptyImageDrawable, 0));
        }

        if(a.hasValue(R.styleable.SflStatefulLayout_errorImageDrawable)) {
            setErrorImageResource(a.getResourceId(R.styleable.SflStatefulLayout_errorImageDrawable, 0));
        }

        // getState initial state if setState
        if(a.hasValue(R.styleable.SflStatefulLayout_state)) {
            mInitialState = a.getString(R.styleable.SflStatefulLayout_state);
        }

        a.recycle();
    }


    public class State extends StatefulLayout.State {
        // Note: CONTENT state is inherited from parent
        public static final String PROGRESS = "progress";
        public static final String OFFLINE = "offline";
        public static final String EMPTY = "empty";
        public static final String ERROR = "error";
    }
}
