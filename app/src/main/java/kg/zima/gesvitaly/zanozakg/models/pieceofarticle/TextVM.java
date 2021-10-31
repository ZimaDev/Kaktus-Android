package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.TEXT;

public class TextVM implements PieceOfArticle {
    private String text;

    public TextVM(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return TEXT;
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

    protected TextVM(Parcel in) {
        this.text = in.readString();
    }

    public static final Creator<TextVM> CREATOR = new Creator<TextVM>() {
        @Override
        public TextVM createFromParcel(Parcel source) {
            return new TextVM(source);
        }

        @Override
        public TextVM[] newArray(int size) {
            return new TextVM[size];
        }
    };
}
