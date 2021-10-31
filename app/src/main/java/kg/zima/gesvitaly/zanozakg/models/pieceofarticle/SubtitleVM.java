package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.SUBTITLE;

public class SubtitleVM implements PieceOfArticle {
    private String text;

    public SubtitleVM(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return SUBTITLE;
    }

    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
    }

    protected SubtitleVM(Parcel in) {
        this.text = in.readString();
    }

    public static final Creator<SubtitleVM> CREATOR = new Creator<SubtitleVM>() {
        @Override
        public SubtitleVM createFromParcel(Parcel source) {
            return new SubtitleVM(source);
        }

        @Override
        public SubtitleVM[] newArray(int size) {
            return new SubtitleVM[size];
        }
    };
}
