package kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.models.NewsTag;
import kg.zima.gesvitaly.zanozakg.models.Topics;

public class ResponseForLoadNewsPreviewsList {
    @SerializedName("success")
    private String success;
    @SerializedName("lables")
    private ArrayList<NewsTag> newsTags;
    @SerializedName("topics")
    private Topics topics;

    public ResponseForLoadNewsPreviewsList(String success, ArrayList<NewsTag> newsTags, Topics topics) {
        this.success = success;
        this.newsTags = newsTags;
        this.topics = topics;
    }

    public String getSuccess() {
        return success;
    }

    public ArrayList<NewsTag> getNewsTags() {
        return newsTags;
    }

    public Topics getTopics() {
        return topics;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setNewsTags(ArrayList<NewsTag> newsTags) {
        this.newsTags = newsTags;
    }

    public void setTopics(Topics topics) {
        this.topics = topics;
    }
}
