package kg.zima.gesvitaly.zanozakg.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("date")
    private int date;
    @SerializedName("text")
    private String text;
    @SerializedName("user")
    private String user;
    @SerializedName("photo")
    private String photo;
    @SerializedName("user_seti")
    @Nullable
    private String socialType;
    @SerializedName("user_seti_id")
    @Nullable
    private String userId;
    @SerializedName("user_link")
    @Nullable
    private String userLink;
    @SerializedName("vote_minus")
    private int minusCount;
    @SerializedName("vote_plus")
    private int plusCount;

    public Comment(int id, int date, String text, String user, String photo, @Nullable String socialType, @Nullable String userId, @Nullable String userLink, int minusCount, int plusCount) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.user = user;
        this.photo = photo;
        this.socialType = socialType;
        this.userId = userId;
        this.userLink = userLink;
        this.minusCount = minusCount;
        this.plusCount = plusCount;
    }

    public int getId() {
        return id;
    }
    public int getDate() {
        return date;
    }
    public String getText() {
        return text;
    }
    public String getUser() {
        return user;
    }
    public String getPhoto() {
        return photo;
    }
    @Nullable
    public String getSocialType() {
        return socialType;
    }
    @Nullable
    public String getUserId() {
        return userId;
    }
    @Nullable
    public String getUserLink() {
        return userLink;
    }
    public int getMinusCount() {
        return minusCount;
    }
    public int getPlusCount() {
        return plusCount;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDate(int date) {
        this.date = date;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void setSocialType(@Nullable String socialType) {
        this.socialType = socialType;
    }
    public void setUserId(@Nullable String userId) {
        this.userId = userId;
    }
    public void setUserLink(@Nullable String userLink) {
        this.userLink = userLink;
    }
    public void setMinusCount(int minusCount) {
        this.minusCount = minusCount;
    }
    public void setPlusCount(int plusCount) {
        this.plusCount = plusCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.date);
        dest.writeString(this.text);
        dest.writeString(this.user);
        dest.writeString(this.photo);
        dest.writeString(this.socialType);
        dest.writeString(this.userId);
        dest.writeString(this.userLink);
        dest.writeInt(this.minusCount);
        dest.writeInt(this.plusCount);
    }

    protected Comment(Parcel in) {
        this.id = in.readInt();
        this.date = in.readInt();
        this.text = in.readString();
        this.user = in.readString();
        this.photo = in.readString();
        this.socialType = in.readString();
        this.userId = in.readString();
        this.userLink = in.readString();
        this.minusCount = in.readInt();
        this.plusCount = in.readInt();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
