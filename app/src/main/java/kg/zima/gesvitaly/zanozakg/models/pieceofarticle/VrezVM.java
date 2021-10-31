package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import kg.zima.gesvitaly.zanozakg.models.Topic;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.VREZ;

public class VrezVM implements PieceOfArticle {
    private Topic topic;

    public VrezVM(Topic topic) {
        this.topic = topic;
    }

    @Override
    public int getType() {
        return VREZ;
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
        dest.writeParcelable(this.topic, flags);
    }

    protected VrezVM(Parcel in) {
        this.topic = in.readParcelable(Topic.class.getClassLoader());
    }

    public static final Creator<VrezVM> CREATOR = new Creator<VrezVM>() {
        @Override
        public VrezVM createFromParcel(Parcel source) {
            return new VrezVM(source);
        }

        @Override
        public VrezVM[] newArray(int size) {
            return new VrezVM[size];
        }
    };
}
