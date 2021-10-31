package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import kg.zima.gesvitaly.zanozakg.R;

class ContentHtmlVH extends RecyclerView.ViewHolder {
    WebView webView;

    ContentHtmlVH(View itemView) {
        super(itemView);
        webView = (WebView) itemView.findViewById(R.id.iframe_webview);
    }
}
