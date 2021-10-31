package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.models.NewsTag;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.TAGS_CLOUD;

public class TagsCloudVM implements PieceOfArticle {
    private ArrayList<NewsTag> newsTags;

    public TagsCloudVM(ArrayList<NewsTag> newsTags) {
        this.newsTags = newsTags;
    }

    @Override
    public int getType() {
        return TAGS_CLOUD;
    }

    public ArrayList<NewsTag> getNewsTags() {
        return newsTags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.newsTags);
    }

    protected TagsCloudVM(Parcel in) {
        this.newsTags = in.createTypedArrayList(NewsTag.CREATOR);
    }

    public static final Creator<TagsCloudVM> CREATOR = new Creator<TagsCloudVM>() {
        @Override
        public TagsCloudVM createFromParcel(Parcel source) {
            return new TagsCloudVM(source);
        }

        @Override
        public TagsCloudVM[] newArray(int size) {
            return new TagsCloudVM[size];
        }
    };
}
