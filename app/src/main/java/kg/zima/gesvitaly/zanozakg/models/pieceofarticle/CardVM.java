package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import kg.zima.gesvitaly.zanozakg.models.Topic;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.CARD;

public class CardVM implements PieceOfArticle {
    private String text;
    private Topic topic;
    private String cardNumber;
    private boolean needTopMargin;

    private CardVM(Builder builder) {
        text = builder.text;
        topic = builder.topic;
        cardNumber = builder.cardNumber;
        needTopMargin = builder.needTopMargin;
    }

    public static IText builder() {
        return new Builder();
    }

    @Override
    public int getType() {
        return CARD;
    }

    public String getText() {
        return text;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean needTopMargin() {
        return needTopMargin;
    }

    public interface IBuild {
        CardVM build();
    }

    public interface INeedTopMargin {
        IBuild needTopMargin(boolean val);
    }

    public interface ICardNumber {
        INeedTopMargin cardNumber(String val);
    }

    public interface ITopic {
        ICardNumber topic(Topic val);
    }

    public interface IText {
        ITopic text(String val);
    }

    public static final class Builder implements INeedTopMargin, ICardNumber, ITopic, IText, IBuild {
        private boolean needTopMargin;
        private String cardNumber;
        private Topic topic;
        private String text;

        private Builder() {
        }

        @Override
        public IBuild needTopMargin(boolean val) {
            needTopMargin = val;
            return this;
        }

        @Override
        public INeedTopMargin cardNumber(String val) {
            cardNumber = val;
            return this;
        }

        @Override
        public ICardNumber topic(Topic val) {
            topic = val;
            return this;
        }

        @Override
        public ITopic text(String val) {
            text = val;
            return this;
        }

        public CardVM build() {
            return new CardVM(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeParcelable(this.topic, flags);
        dest.writeString(this.cardNumber);
        dest.writeByte(this.needTopMargin ? (byte) 1 : (byte) 0);
    }

    protected CardVM(Parcel in) {
        this.text = in.readString();
        this.topic = in.readParcelable(Topic.class.getClassLoader());
        this.cardNumber = in.readString();
        this.needTopMargin = in.readByte() != 0;
    }

    public static final Creator<CardVM> CREATOR = new Creator<CardVM>() {
        @Override
        public CardVM createFromParcel(Parcel source) {
            return new CardVM(source);
        }

        @Override
        public CardVM[] newArray(int size) {
            return new CardVM[size];
        }
    };
}
