package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.DATE_AND_AUTHORS;

public class DateAndAuthorsVM implements PieceOfArticle {
    private String text;

    public DateAndAuthorsVM(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return DATE_AND_AUTHORS;
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

    protected DateAndAuthorsVM(Parcel in) {
        this.text = in.readString();
    }

    public static final Creator<DateAndAuthorsVM> CREATOR = new Creator<DateAndAuthorsVM>() {
        @Override
        public DateAndAuthorsVM createFromParcel(Parcel source) {
            return new DateAndAuthorsVM(source);
        }

        @Override
        public DateAndAuthorsVM[] newArray(int size) {
            return new DateAndAuthorsVM[size];
        }
    };
}
