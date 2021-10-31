package kg.zima.gesvitaly.zanozakg.models.exchangeRates;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class RateDiff extends Rate {
    @SerializedName("type")
    private String type;

    public RateDiff(String buy, String sell, String type) {
        super(buy, sell);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.type);
    }

    protected RateDiff(Parcel in) {
        super(in);
        this.type = in.readString();
    }

    public static final Creator<RateDiff> CREATOR = new Creator<RateDiff>() {
        @Override
        public RateDiff createFromParcel(Parcel source) {
            return new RateDiff(source);
        }

        @Override
        public RateDiff[] newArray(int size) {
            return new RateDiff[size];
        }
    };
}
