package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.BULLET_LIST_ITEM;

public class BulletListItemVM implements PieceOfArticle {
    private String text;

    public BulletListItemVM(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return BULLET_LIST_ITEM;
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

    protected BulletListItemVM(Parcel in) {
        this.text = in.readString();
    }

    public static final Creator<BulletListItemVM> CREATOR = new Creator<BulletListItemVM>() {
        @Override
        public BulletListItemVM createFromParcel(Parcel source) {
            return new BulletListItemVM(source);
        }

        @Override
        public BulletListItemVM[] newArray(int size) {
            return new BulletListItemVM[size];
        }
    };
}
