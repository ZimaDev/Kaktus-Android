package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import kg.zima.gesvitaly.zanozakg.R;

class IFrameVH extends RecyclerView.ViewHolder {
    WebView iFrameWebView;

    IFrameVH(View itemView) {
        super(itemView);
        iFrameWebView = (WebView) itemView.findViewById(R.id.iframe_webview);
    }
}
