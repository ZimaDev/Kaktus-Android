package kg.zima.gesvitaly.zanozakg.utils.spanutils;

import android.os.Parcel;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import android.text.style.URLSpan;
import android.view.View;

import kg.zima.gesvitaly.zanozakg.utils.ZanozaUtils;

import static androidx.core.content.ContextCompat.startActivity;

public class LinkSpan extends URLSpan {
    private Fragment fragment;

    public LinkSpan(String url, Fragment fragment) {
        super(url);
        this.fragment = fragment;
    }

    // TODO: сделать, чтобы работало с ссылками вида http://zanoza.kg/345672
    @Override
    public void onClick(View view) {
        String url = getURL();
        ZanozaUtils.handleLink(url, fragment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected LinkSpan(Parcel in) {
        super(in);
    }

    public static final Creator<LinkSpan> CREATOR = new Creator<LinkSpan>() {
        @Override
        public LinkSpan createFromParcel(Parcel source) {
            return new LinkSpan(source);
        }

        @Override
        public LinkSpan[] newArray(int size) {
            return new LinkSpan[size];
        }
    };
}
