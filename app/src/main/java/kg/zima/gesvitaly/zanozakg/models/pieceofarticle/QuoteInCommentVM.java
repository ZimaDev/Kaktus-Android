package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import kg.zima.gesvitaly.zanozakg.models.Topic;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.QUOTE_IN_COMMENT;

public class QuoteInCommentVM implements PieceOfArticle {
    private String text;
    private Topic topic;

    public QuoteInCommentVM(String text, Topic topic) {
        this.text = text;
        this.topic = topic;
    }

    @Override
    public int getType() {
        return QUOTE_IN_COMMENT;
    }

    public String getText() {
        return text;
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
        dest.writeString(this.text);
        dest.writeParcelable(this.topic, flags);
    }

    protected QuoteInCommentVM(Parcel in) {
        this.text = in.readString();
        this.topic = in.readParcelable(Topic.class.getClassLoader());
    }

    public static final Creator<QuoteInCommentVM> CREATOR = new Creator<QuoteInCommentVM>() {
        @Override
        public QuoteInCommentVM createFromParcel(Parcel source) {
            return new QuoteInCommentVM(source);
        }

        @Override
        public QuoteInCommentVM[] newArray(int size) {
            return new QuoteInCommentVM[size];
        }
    };
}
