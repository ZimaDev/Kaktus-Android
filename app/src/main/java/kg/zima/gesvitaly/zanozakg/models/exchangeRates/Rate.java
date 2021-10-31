package kg.zima.gesvitaly.zanozakg.models.exchangeRates;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Rate implements Parcelable {
    @SerializedName("buy")
    private String buy;
    @SerializedName("sell")
    private String sell;

    public Rate(String buy, String sell) {
        this.buy = buy;
        this.sell = sell;
    }

    public String getBuy() {
        return buy;
    }

    public String getSell() {
        return sell;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.buy);
        dest.writeString(this.sell);
    }

    protected Rate(Parcel in) {
        this.buy = in.readString();
        this.sell = in.readString();
    }

    public static final Creator<Rate> CREATOR = new Creator<Rate>() {
        @Override
        public Rate createFromParcel(Parcel source) {
            return new Rate(source);
        }

        @Override
        public Rate[] newArray(int size) {
            return new Rate[size];
        }
    };
}
