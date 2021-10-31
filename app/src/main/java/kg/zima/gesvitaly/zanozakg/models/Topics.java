package kg.zima.gesvitaly.zanozakg.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Topics implements Parcelable {
    @SerializedName("query0")
    private ArrayList<NewsPreview> query0;

    @SerializedName("query1")
    private ArrayList<NewsPreview> query1;

    public Topics(ArrayList<NewsPreview> query0, ArrayList<NewsPreview> query1) {
        this.query0 = query0;
        this.query1 = query1;
    }

    public ArrayList<NewsPreview> getQuery0() {
        return query0;
    }

    public void setQuery0(ArrayList<NewsPreview> query0) {
        this.query0 = query0;
    }

    public ArrayList<NewsPreview> getQuery1() {
        return query1;
    }

    public void setQuery1(ArrayList<NewsPreview> query1) {
        this.query1 = query1;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.query0);
        dest.writeList(this.query1);
    }

    protected Topics(Parcel in) {
        this.query0 = new ArrayList<NewsPreview>();
        in.readList(this.query0, NewsPreview.class.getClassLoader());
        this.query1 = new ArrayList<NewsPreview>();
        in.readList(this.query1, NewsPreview.class.getClassLoader());
    }

    public static final Creator<Topics> CREATOR = new Creator<Topics>() {
        @Override
        public Topics createFromParcel(Parcel source) {
            return new Topics(source);
        }

        @Override
        public Topics[] newArray(int size) {
            return new Topics[size];
        }
    };
}
