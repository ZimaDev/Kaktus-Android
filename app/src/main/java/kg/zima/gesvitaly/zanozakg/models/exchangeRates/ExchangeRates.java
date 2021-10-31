package kg.zima.gesvitaly.zanozakg.models.exchangeRates;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ExchangeRates implements Parcelable {
    @SerializedName("usa")
    private ExchangeRate usd;
    @SerializedName("eur")
    private ExchangeRate eur;
    @SerializedName("rus")
    private ExchangeRate rub;
    @SerializedName("kzt")
    private ExchangeRate kzt;

    private ExchangeRates(Builder builder) {
        usd = builder.usd;
        eur = builder.eur;
        rub = builder.rub;
        kzt = builder.kzt;
    }

    public ExchangeRate getUsd() {
        return usd;
    }

    public ExchangeRate getEur() {
        return eur;
    }

    public ExchangeRate getRub() {
        return rub;
    }

    public ExchangeRate getKzt() {
        return kzt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.usd, flags);
        dest.writeParcelable(this.eur, flags);
        dest.writeParcelable(this.rub, flags);
        dest.writeParcelable(this.kzt, flags);
    }

    public ExchangeRates() {
    }

    protected ExchangeRates(Parcel in) {
        this.usd = in.readParcelable(ExchangeRate.class.getClassLoader());
        this.eur = in.readParcelable(ExchangeRate.class.getClassLoader());
        this.rub = in.readParcelable(ExchangeRate.class.getClassLoader());
        this.kzt = in.readParcelable(ExchangeRate.class.getClassLoader());
    }

    public static final Creator<ExchangeRates> CREATOR = new Creator<ExchangeRates>() {
        @Override
        public ExchangeRates createFromParcel(Parcel source) {
            return new ExchangeRates(source);
        }

        @Override
        public ExchangeRates[] newArray(int size) {
            return new ExchangeRates[size];
        }
    };

    public static final class Builder {
        private ExchangeRate usd;
        private ExchangeRate eur;
        private ExchangeRate rub;
        private ExchangeRate kzt;

        public Builder() {
        }

        public Builder usd(ExchangeRate val) {
            usd = val;
            return this;
        }

        public Builder eur(ExchangeRate val) {
            eur = val;
            return this;
        }

        public Builder rub(ExchangeRate val) {
            rub = val;
            return this;
        }

        public Builder kzt(ExchangeRate val) {
            kzt = val;
            return this;
        }

        public ExchangeRates build() {
            return new ExchangeRates(this);
        }
    }
}
