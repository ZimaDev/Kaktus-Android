package kg.zima.gesvitaly.zanozakg.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NewsTag implements Parcelable {
    @SerializedName("tag_id")
    private int id;
    @SerializedName("cat_id")
    private int catId;
    @SerializedName("tag_name")
    private String name;

    public NewsTag(int id, int catId, String name) {
        this.id = id;
        this.catId = catId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getCatId() {
        return catId;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Позволяет корректно сравнивать теги
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NewsTag) {
            NewsTag newsTag = (NewsTag) obj;
            return this.id == newsTag.getId() && this.name.equals(newsTag.getName());
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.catId);
        dest.writeString(this.name);
    }

    protected NewsTag(Parcel in) {
        this.id = in.readInt();
        this.catId = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<NewsTag> CREATOR = new Creator<NewsTag>() {
        @Override
        public NewsTag createFromParcel(Parcel source) {
            return new NewsTag(source);
        }

        @Override
        public NewsTag[] newArray(int size) {
            return new NewsTag[size];
        }
    };
}
