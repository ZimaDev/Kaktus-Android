package kg.zima.gesvitaly.zanozakg.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import kg.zima.gesvitaly.zanozakg.utils.YouTubeInterface;

public class YoutubeActivity extends Activity {
    private WebView webView;

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onResume() {
        webView.onResume();
        super.onResume();
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String youTubeVideoId = getIntent().getStringExtra("youTubeVideoId");
        if (youTubeVideoId != null) {
            webView = new WebView(this);
            webView.addJavascriptInterface(new YouTubeInterface(youTubeVideoId), "android");
            setContentView(webView);

            final WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);

            webView.setWebChromeClient(new WebChromeClient());
            webView.setPadding(0, 0, 0, 0);

            webView.loadUrl("file:///android_asset/youtube.html");
        }

    }

}
