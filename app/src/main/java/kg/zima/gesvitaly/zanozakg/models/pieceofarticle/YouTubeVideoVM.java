package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.YOUTUBE_VIDEO;

public class YouTubeVideoVM implements PieceOfArticle {
    private String youtubeVideoId;

    public YouTubeVideoVM(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    @Override
    public int getType() {
        return YOUTUBE_VIDEO;
    }

    public String getVideoId() {
        return youtubeVideoId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.youtubeVideoId);
    }

    protected YouTubeVideoVM(Parcel in) {
        this.youtubeVideoId = in.readString();
    }

    public static final Creator<YouTubeVideoVM> CREATOR = new Creator<YouTubeVideoVM>() {
        @Override
        public YouTubeVideoVM createFromParcel(Parcel source) {
            return new YouTubeVideoVM(source);
        }

        @Override
        public YouTubeVideoVM[] newArray(int size) {
            return new YouTubeVideoVM[size];
        }
    };
}
