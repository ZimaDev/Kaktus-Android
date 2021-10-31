package kg.zima.gesvitaly.zanozakg.models.exchangeRates;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ExchangeRate implements Parcelable {
    @SerializedName("new")
    private Rate newRate;
    @SerializedName("old")
    private Rate oldRate;
    @SerializedName("diff")
    private RateDiff rateDiff;

    public ExchangeRate(Rate newRate, Rate oldRate, RateDiff rateDiff) {
        this.newRate = newRate;
        this.oldRate = oldRate;
        this.rateDiff = rateDiff;
    }

    public Rate getNewRate() {
        return newRate;
    }

    public Rate getOldRate() {
        return oldRate;
    }

    public RateDiff getRateDiff() {
        return rateDiff;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.newRate, flags);
        dest.writeParcelable(this.oldRate, flags);
        dest.writeParcelable(this.rateDiff, flags);
    }

    protected ExchangeRate(Parcel in) {
        this.newRate = in.readParcelable(Rate.class.getClassLoader());
        this.oldRate = in.readParcelable(Rate.class.getClassLoader());
        this.rateDiff = in.readParcelable(RateDiff.class.getClassLoader());
    }

    public static final Parcelable.Creator<ExchangeRate> CREATOR = new Parcelable.Creator<ExchangeRate>() {
        @Override
        public ExchangeRate createFromParcel(Parcel source) {
            return new ExchangeRate(source);
        }

        @Override
        public ExchangeRate[] newArray(int size) {
            return new ExchangeRate[size];
        }
    };
}
