package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import kg.zima.gesvitaly.zanozakg.models.Comment;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.COMMENT;

public class CommentVM implements PieceOfArticle {
    private int date;
    private String text;
    private String user;
    private String photo;

    public CommentVM(Comment comment) {
        date = comment.getDate();
        text = comment.getText();
        user = comment.getUser();
        photo = comment.getPhoto();
    }

    @Override
    public int getType() {
        return COMMENT;
    }

    public int getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.date);
        dest.writeString(this.text);
        dest.writeString(this.user);
        dest.writeString(this.photo);
    }

    protected CommentVM(Parcel in) {
        this.date = in.readInt();
        this.text = in.readString();
        this.user = in.readString();
        this.photo = in.readString();
    }

    public static final Creator<CommentVM> CREATOR = new Creator<CommentVM>() {
        @Override
        public CommentVM createFromParcel(Parcel source) {
            return new CommentVM(source);
        }

        @Override
        public CommentVM[] newArray(int size) {
            return new CommentVM[size];
        }
    };
}
