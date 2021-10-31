package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.models.Media;

import static kg.zima.gesvitaly.zanozakg.CarouselFlags.CarouselFlag;
import static kg.zima.gesvitaly.zanozakg.CarouselFlags.carouselFlagTranslate;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.CAROUSEL;

public class CarouselVM implements PieceOfArticle {
    private ArrayList<Media> medias;
    private @CarouselFlag int flag;

    public CarouselVM(ArrayList<Media> medias, @CarouselFlag int flag) {
        this.medias = medias;
        this.flag = flag;
    }

    @Override
    public int getType() {
        return CAROUSEL;
    }

    public ArrayList<Media> getMedias() {
        return medias;
    }

    public @CarouselFlag int getFlag() {
        return flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.medias);
        dest.writeInt(this.flag);
    }

    protected CarouselVM(Parcel in) {
        this.medias = in.createTypedArrayList(Media.CREATOR);
        this.flag = carouselFlagTranslate(in.readInt());
    }

    public static final Creator<CarouselVM> CREATOR = new Creator<CarouselVM>() {
        @Override
        public CarouselVM createFromParcel(Parcel source) {
            return new CarouselVM(source);
        }

        @Override
        public CarouselVM[] newArray(int size) {
            return new CarouselVM[size];
        }
    };
}
