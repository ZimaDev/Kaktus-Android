package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.TITLE;

public class TitleVM implements PieceOfArticle {
    private String text;

    public TitleVM(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return TITLE;
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

    protected TitleVM(Parcel in) {
        this.text = in.readString();
    }

    public static final Creator<TitleVM> CREATOR = new Creator<TitleVM>() {
        @Override
        public TitleVM createFromParcel(Parcel source) {
            return new TitleVM(source);
        }

        @Override
        public TitleVM[] newArray(int size) {
            return new TitleVM[size];
        }
    };
}
