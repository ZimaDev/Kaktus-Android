package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.COMMENTS_TITLE;

public class CommentsTitleVM implements PieceOfArticle {
    private String text;

    public CommentsTitleVM(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return COMMENTS_TITLE;
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

    protected CommentsTitleVM(Parcel in) {
        this.text = in.readString();
    }

    public static final Creator<CommentsTitleVM> CREATOR = new Creator<CommentsTitleVM>() {
        @Override
        public CommentsTitleVM createFromParcel(Parcel source) {
            return new CommentsTitleVM(source);
        }

        @Override
        public CommentsTitleVM[] newArray(int size) {
            return new CommentsTitleVM[size];
        }
    };
}
