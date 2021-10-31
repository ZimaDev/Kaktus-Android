package kg.zima.gesvitaly.zanozakg.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Topic implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("url")
    private String url;
    @SerializedName("date")
    private int date;
    @SerializedName("name")
    private String name;
    @SerializedName("lead")
    private String lead;
    @SerializedName("text")
    private String text;
    @SerializedName("img")
    private String img;
    @SerializedName("main")
    private boolean isBold;
    @SerializedName("lables")
    private ArrayList<Integer> tagsIds;
    @SerializedName("count_message")
    private int countOfComments;
    @SerializedName("count_view")
    private int countOfViews;
    @SerializedName("media")
    private ArrayList<Media> media;
    @SerializedName("messages")
    private ArrayList<Comment> comments;
    @SerializedName("bb_topics")
    private ArrayList<TopicPreview> topicPreviews;

    public Topic(String text) {
        this.text = text;
    }

    public Topic(int id, String url, int date, String name, String lead, String text, String img,
                 boolean isBold, ArrayList<Integer> tagsIds, int countOfComments,
                 ArrayList<Media> media, ArrayList<Comment> comments, ArrayList<TopicPreview> topicPreviews) {
        this.id = id;
        this.url = url;
        this.date = date;
        this.name = name;
        this.lead = lead;
        this.text = text;
        this.img = img;
        this.isBold = isBold;
        this.tagsIds = tagsIds;
        this.countOfComments = countOfComments;
        this.media = media;
        this.comments = comments;
        this.topicPreviews = topicPreviews;
    }

    public Topic(Topic topic) {
        this.id = topic.id;
        this.url = topic.url;
        this.date = topic.date;
        this.name = topic.name;
        this.lead = topic.lead;
        this.text = topic.text;
        this.img = topic.img;
        this.isBold = topic.isBold;
        this.tagsIds = topic.tagsIds;
        this.countOfComments = topic.countOfComments;
        this.media = topic.media;
        this.comments = topic.comments;
        this.topicPreviews = topic.topicPreviews;
    }

    //region Геттеры
    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getLead() {
        return lead;
    }

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }

    public boolean isBold() {
        return isBold;
    }

    public ArrayList<Integer> getTagsIds() {
        return tagsIds;
    }

    public int getCountOfComments() {
        return countOfComments;
    }

    public int getCountOfViews() {
        return countOfViews;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<TopicPreview> getTopicPreviews() {
        return topicPreviews;
    }

    //endregion

    //region Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public void setTagsIds(ArrayList<Integer> tagsIds) {
        this.tagsIds = tagsIds;
    }

    public void setCountOfComments(int countOfComments) {
        this.countOfComments = countOfComments;
    }

    public void setCountOfViews(int countOfViews) {
        this.countOfViews = countOfViews;
    }

    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setTopicPreviews(ArrayList<TopicPreview> topicPreviews) {
        this.topicPreviews = topicPreviews;
    }

    //endregion

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.url);
        dest.writeInt(this.date);
        dest.writeString(this.name);
        dest.writeString(this.lead);
        dest.writeString(this.text);
        dest.writeString(this.img);
        dest.writeByte(this.isBold ? (byte) 1 : (byte) 0);
        dest.writeList(this.tagsIds);
        dest.writeInt(this.countOfComments);
        dest.writeInt(this.countOfViews);
        dest.writeTypedList(this.media);
        dest.writeTypedList(this.comments);
        dest.writeTypedList(this.topicPreviews);
    }

    protected Topic(Parcel in) {
        this.id = in.readInt();
        this.url = in.readString();
        this.date = in.readInt();
        this.name = in.readString();
        this.lead = in.readString();
        this.text = in.readString();
        this.img = in.readString();
        this.isBold = in.readByte() != 0;
        this.tagsIds = new ArrayList<Integer>();
        in.readList(this.tagsIds, Integer.class.getClassLoader());
        this.countOfComments = in.readInt();
        this.countOfViews = in.readInt();
        this.media = in.createTypedArrayList(Media.CREATOR);
        this.comments = in.createTypedArrayList(Comment.CREATOR);
        this.topicPreviews = in.createTypedArrayList(TopicPreview.CREATOR);
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };
}
