package kg.zima.gesvitaly.zanozakg.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsPreview implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("date")
    private int date;
    @SerializedName("name")
    private String name;
    @SerializedName("img")
    private String img;
    @SerializedName("lables")
    private ArrayList<Integer> tagsIds;
    @SerializedName("count_message")
    private int countOfComments;
    @SerializedName("count_view")
    private int countOfViews;

    public NewsPreview(int id, int date, String name, String img, ArrayList<Integer> tagsIds, int countOfComments, int countOfViews) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.img = img;
        this.tagsIds = tagsIds;
        this.countOfComments = countOfComments;
        this.countOfViews = countOfViews;
    }

    public int getId() {
        return id;
    }
    public int getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public String getImg() {
        return img;
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

    public void setId(int id) {
        this.id = id;
    }
    public void setDate(int date) {
        this.date = date;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImg(String img) {
        this.img = img;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.date);
        dest.writeString(this.name);
        dest.writeString(this.img);
        dest.writeList(this.tagsIds);
        dest.writeInt(this.countOfComments);
        dest.writeInt(this.countOfViews);
    }

    protected NewsPreview(Parcel in) {
        this.id = in.readInt();
        this.date = in.readInt();
        this.name = in.readString();
        this.img = in.readString();
        this.tagsIds = new ArrayList<Integer>();
        in.readList(this.tagsIds, Integer.class.getClassLoader());
        this.countOfComments = in.readInt();
        this.countOfViews = in.readInt();
    }

    public static final Creator<NewsPreview> CREATOR = new Creator<NewsPreview>() {
        @Override
        public NewsPreview createFromParcel(Parcel source) {
            return new NewsPreview(source);
        }

        @Override
        public NewsPreview[] newArray(int size) {
            return new NewsPreview[size];
        }
    };
}
