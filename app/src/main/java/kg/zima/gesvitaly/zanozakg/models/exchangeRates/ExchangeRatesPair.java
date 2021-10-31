package kg.zima.gesvitaly.zanozakg.models.exchangeRates;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ExchangeRatesPair implements Parcelable {
    @SerializedName("bank")
    private ExchangeRates banks;
    @SerializedName("nbkr")
    private ExchangeRates nbkr;

    private ExchangeRatesPair(Builder builder) {
        banks = builder.banks;
        nbkr = builder.nbkr;
    }

    public ExchangeRates getBanks() {
        return banks;
    }

    public ExchangeRates getNbkr() {
        return nbkr;
    }

    public ExchangeRatesPair(ExchangeRates banks, ExchangeRates nbkr) {

        this.banks = banks;
        this.nbkr = nbkr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.banks, flags);
        dest.writeParcelable(this.nbkr, flags);
    }

    protected ExchangeRatesPair(Parcel in) {
        this.banks = in.readParcelable(ExchangeRates.class.getClassLoader());
        this.nbkr = in.readParcelable(ExchangeRates.class.getClassLoader());
    }

    public static final Creator<ExchangeRatesPair> CREATOR = new Creator<ExchangeRatesPair>() {
        @Override
        public ExchangeRatesPair createFromParcel(Parcel source) {
            return new ExchangeRatesPair(source);
        }

        @Override
        public ExchangeRatesPair[] newArray(int size) {
            return new ExchangeRatesPair[size];
        }
    };

    public static final class Builder {
        private ExchangeRates banks;
        private ExchangeRates nbkr;

        public Builder() {
        }

        public Builder banks(ExchangeRates val) {
            banks = val;
            return this;
        }

        public Builder nbkr(ExchangeRates val) {
            nbkr = val;
            return this;
        }

        public ExchangeRatesPair build() {
            return new ExchangeRatesPair(this);
        }
    }
}
