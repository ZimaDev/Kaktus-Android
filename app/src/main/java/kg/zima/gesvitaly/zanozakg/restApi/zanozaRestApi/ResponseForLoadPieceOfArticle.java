package kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.models.NewsTag;
import kg.zima.gesvitaly.zanozakg.models.Topic;

public class ResponseForLoadPieceOfArticle {
    @SerializedName("success")
    private String success;
    @SerializedName("lables")
    private ArrayList<NewsTag> newsTags;
    @SerializedName("topic")
    private Topic topic;

    public String getSuccess() {
        return success;
    }

    public ArrayList<NewsTag> getNewsTags() {
        return newsTags;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setNewsTags(ArrayList<NewsTag> newsTags) {
        this.newsTags = newsTags;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
