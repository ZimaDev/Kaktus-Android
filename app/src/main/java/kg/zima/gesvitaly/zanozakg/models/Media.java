package kg.zima.gesvitaly.zanozakg.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Media implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private int type;
    @SerializedName("enable")
    private boolean showInCarousel;
    @SerializedName("url1")
    private String smallImgUrl;
    @SerializedName("url2")
    private String mediumImgUrl;
    @SerializedName("url3")
    private String bigImgUrl;
    @SerializedName("authors")
    private ArrayList<Author> authors;
    @SerializedName("desc")
    private String description;

    public Media(int id, int type, boolean showInCarousel, String smallImgUrl, String mediumImgUrl, String bigImgUrl, ArrayList<Author> authors, String description) {
        this.id = id;
        this.type = type;
        this.showInCarousel = showInCarousel;
        this.smallImgUrl = smallImgUrl;
        this.mediumImgUrl = mediumImgUrl;
        this.bigImgUrl = bigImgUrl;
        this.authors = authors;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public int getType() {
        return type;
    }
    public boolean isShowInCarousel() {
        return showInCarousel;
    }
    public String getSmallImgUrl() {
        return smallImgUrl;
    }
    public String getMediumImgUrl() {
        return mediumImgUrl;
    }
    public String getBigImgUrl() {
        return bigImgUrl;
    }
    public ArrayList<Author> getAuthors() {
        return authors;
    }
    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setShowInCarousel(boolean showInCarousel) {
        this.showInCarousel = showInCarousel;
    }
    public void setSmallImgUrl(String smallImgUrl) {
        this.smallImgUrl = smallImgUrl;
    }
    public void setMediumImgUrl(String mediumImgUrl) {
        this.mediumImgUrl = mediumImgUrl;
    }
    public void setBigImgUrl(String bigImgUrl) {
        this.bigImgUrl = bigImgUrl;
    }
    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.type);
        dest.writeByte(this.showInCarousel ? (byte) 1 : (byte) 0);
        dest.writeString(this.smallImgUrl);
        dest.writeString(this.mediumImgUrl);
        dest.writeString(this.bigImgUrl);
        dest.writeTypedList(this.authors);
        dest.writeString(this.description);
    }

    protected Media(Parcel in) {
        this.id = in.readInt();
        this.type = in.readInt();
        this.showInCarousel = in.readByte() != 0;
        this.smallImgUrl = in.readString();
        this.mediumImgUrl = in.readString();
        this.bigImgUrl = in.readString();
        this.authors = in.createTypedArrayList(Author.CREATOR);
        this.description = in.readString();
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
