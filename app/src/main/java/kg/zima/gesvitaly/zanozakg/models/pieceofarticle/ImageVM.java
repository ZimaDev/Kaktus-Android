package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.models.Author;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.IMAGE;

public class ImageVM implements PieceOfArticle {
    private int mediaType;
    private String smallImgUrl;
    private String mediumImgUrl;
    private String bigImgUrl;
    private ArrayList<Author> authors;

    public ImageVM(int mediaType, String smallImgUrl, String mediumImgUrl, String bigImgUrl, ArrayList<Author> authors) {
        this.mediaType = mediaType;
        this.smallImgUrl = smallImgUrl;
        this.mediumImgUrl = mediumImgUrl;
        this.bigImgUrl = bigImgUrl;
        this.authors = authors;
    }

    @Override
    public int getType() {
        return IMAGE;
    }

    public int getMediaType() {
        return mediaType;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mediaType);
        dest.writeString(this.smallImgUrl);
        dest.writeString(this.mediumImgUrl);
        dest.writeString(this.bigImgUrl);
        dest.writeTypedList(this.authors);
    }

    protected ImageVM(Parcel in) {
        this.mediaType = in.readInt();
        this.smallImgUrl = in.readString();
        this.mediumImgUrl = in.readString();
        this.bigImgUrl = in.readString();
        this.authors = in.createTypedArrayList(Author.CREATOR);
    }

    public static final Creator<ImageVM> CREATOR = new Creator<ImageVM>() {
        @Override
        public ImageVM createFromParcel(Parcel source) {
            return new ImageVM(source);
        }

        @Override
        public ImageVM[] newArray(int size) {
            return new ImageVM[size];
        }
    };
}
