package kg.zima.gesvitaly.zanozakg.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Quiz implements Parcelable {
    @SerializedName("quiz_id")
    private int id;
    @SerializedName("quiz_name")
    private String name;
    @SerializedName("quiz_type")
    private String type;
    @SerializedName("is_end")
    private int isEnded;
    @SerializedName("variants")
    private ArrayList<Variant> variants;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public int getIsEnded() {
        return isEnded;
    }
    public ArrayList<Variant> getVariants() {
        return variants;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setIsEnded(int isEnded) {
        this.isEnded = isEnded;
    }
    public void setVariants(ArrayList<Variant> variants) {
        this.variants = variants;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeInt(this.isEnded);
        dest.writeList(this.variants);
    }

    public Quiz() {
    }

    protected Quiz(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.type = in.readString();
        this.isEnded = in.readInt();
        this.variants = new ArrayList<Variant>();
        in.readList(this.variants, Variant.class.getClassLoader());
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel source) {
            return new Quiz(source);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };
}
