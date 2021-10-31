package kg.zima.gesvitaly.zanozakg.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImgUrls implements Parcelable {
    @SerializedName("small")
    private String smallImgUrl;
    @SerializedName("medium")
    private String mediumImgUrl;
    @SerializedName("big")
    private String bigImgUrl;
    @SerializedName("original")
    private String sourceImgUrl;

    public ImgUrls(String smallImgUrl, String mediumImgUrl, String bigImgUrl, String sourceImgUrl) {
        this.smallImgUrl = smallImgUrl;
        this.mediumImgUrl = mediumImgUrl;
        this.bigImgUrl = bigImgUrl;
        this.sourceImgUrl = sourceImgUrl;
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

    public String getSourceImgUrl() {
        return sourceImgUrl;
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

    public void setSourceImgUrl(String sourceImgUrl) {
        this.sourceImgUrl = sourceImgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.smallImgUrl);
        dest.writeString(this.mediumImgUrl);
        dest.writeString(this.bigImgUrl);
        dest.writeString(this.sourceImgUrl);
    }

    protected ImgUrls(Parcel in) {
        this.smallImgUrl = in.readString();
        this.mediumImgUrl = in.readString();
        this.bigImgUrl = in.readString();
        this.sourceImgUrl = in.readString();
    }

    public static final Creator<ImgUrls> CREATOR = new Creator<ImgUrls>() {
        @Override
        public ImgUrls createFromParcel(Parcel source) {
            return new ImgUrls(source);
        }

        @Override
        public ImgUrls[] newArray(int size) {
            return new ImgUrls[size];
        }
    };
}
