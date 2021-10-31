package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;
import android.os.Parcelable;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.QUOTE;

public class QuoteVM implements PieceOfArticle {
    private String text;

    public QuoteVM(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return QUOTE;
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

    protected QuoteVM(Parcel in) {
        this.text = in.readString();
    }

    public static final Creator<QuoteVM> CREATOR = new Creator<QuoteVM>() {
        @Override
        public QuoteVM createFromParcel(Parcel source) {
            return new QuoteVM(source);
        }

        @Override
        public QuoteVM[] newArray(int size) {
            return new QuoteVM[size];
        }
    };
}
