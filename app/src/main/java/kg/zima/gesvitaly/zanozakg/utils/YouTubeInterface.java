package kg.zima.gesvitaly.zanozakg.utils;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class YouTubeInterface {
    private String youTubeId;

    /** Instantiate the interface and set the context */
    public YouTubeInterface(String youTubeId) {
        this.youTubeId = youTubeId;
    }

    /** Pass youTubeId to web page */
    @JavascriptInterface
    public String getYouTubeId() {
        return youTubeId;
    }
}
