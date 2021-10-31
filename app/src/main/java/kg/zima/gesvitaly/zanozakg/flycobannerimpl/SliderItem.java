package kg.zima.gesvitaly.zanozakg.flycobannerimpl;

import android.os.Parcel;
import android.os.Parcelable;

public class SliderItem implements Parcelable {
    private String imgUrl;
    private String title;

    public SliderItem(String imgUrl, String title) {
        this.imgUrl = imgUrl;
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgUrl);
        dest.writeString(this.title);
    }

    protected SliderItem(Parcel in) {
        this.imgUrl = in.readString();
        this.title = in.readString();
    }

    public static final Creator<SliderItem> CREATOR = new Creator<SliderItem>() {
        @Override
        public SliderItem createFromParcel(Parcel source) {
            return new SliderItem(source);
        }

        @Override
        public SliderItem[] newArray(int size) {
            return new SliderItem[size];
        }
    };
}
