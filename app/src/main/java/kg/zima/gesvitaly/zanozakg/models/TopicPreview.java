package kg.zima.gesvitaly.zanozakg.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

// Данные для ссылки на другую новость, показанной во врезе
public class TopicPreview implements Parcelable {
    @SerializedName("topic_id")
    private int id;
    @SerializedName("topic_name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("media_name")
    private ImgUrls imgUrls;
    @SerializedName("topic_count_message")
    private int commentsCount;

    public TopicPreview(int id, String name, String url, ImgUrls imgUrls, int commentsCount) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.imgUrls = imgUrls;
        this.commentsCount = commentsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImgUrls getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(ImgUrls imgUrls) {
        this.imgUrls = imgUrls;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeParcelable(this.imgUrls, flags);
        dest.writeInt(this.commentsCount);
    }

    protected TopicPreview(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.url = in.readString();
        this.imgUrls = in.readParcelable(ImgUrls.class.getClassLoader());
        this.commentsCount = in.readInt();
    }

    public static final Creator<TopicPreview> CREATOR = new Creator<TopicPreview>() {
        @Override
        public TopicPreview createFromParcel(Parcel source) {
            return new TopicPreview(source);
        }

        @Override
        public TopicPreview[] newArray(int size) {
            return new TopicPreview[size];
        }
    };
}
