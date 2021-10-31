package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import kg.zima.gesvitaly.zanozakg.ContentTypes;
import kg.zima.gesvitaly.zanozakg.models.Topic;

public class SpoilerVM implements PieceOfArticle {
    private String title;
    private Topic topic;

    public SpoilerVM(String title, Topic topic) {
        this.title = title;
        this.topic = topic;
    }

    @Override
    public int getType() {
        return ContentTypes.SPOILER;
    }

    public String getTitle() {
        return title;
    }

    public Topic getTopic() {
        return topic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeParcelable(this.topic, flags);
    }

    protected SpoilerVM(Parcel in) {
        this.title = in.readString();
        this.topic = in.readParcelable(Topic.class.getClassLoader());
    }

    public static final Creator<SpoilerVM> CREATOR = new Creator<SpoilerVM>() {
        @Override
        public SpoilerVM createFromParcel(Parcel source) {
            return new SpoilerVM(source);
        }

        @Override
        public SpoilerVM[] newArray(int size) {
            return new SpoilerVM[size];
        }
    };
}
