package kg.zima.gesvitaly.zanozakg.models.exchangeRates;

import android.os.Parcel;
import android.os.Parcelable;

public class ExchangeRatesVM implements Parcelable {
    private String usdNbkr;
    private String eurNbkr;
    private String rubNbkr;
    private String kztNbkr;
    private String usdBanksSell;
    private String usdBanksBuy;
    private String eurBanksSell;
    private String eurBanksBuy;
    private String rubBanksSell;
    private String rubBanksBuy;
    private String kztBanksSell;
    private String kztBanksBuy;

    private ExchangeRatesVM(Builder builder) {
        usdNbkr = builder.usdNbkr;
        eurNbkr = builder.eurNbkr;
        rubNbkr = builder.rubNbkr;
        kztNbkr = builder.kztNbkr;
        usdBanksSell = builder.usdBanksSell;
        usdBanksBuy = builder.usdBanksBuy;
        eurBanksSell = builder.eurBanksSell;
        eurBanksBuy = builder.eurBanksBuy;
        rubBanksSell = builder.rubBanksSell;
        rubBanksBuy = builder.rubBanksBuy;
        kztBanksSell = builder.kztBanksSell;
        kztBanksBuy = builder.kztBanksBuy;
    }

    public String getUsdNbkr() {
        return usdNbkr;
    }

    public String getEurNbkr() {
        return eurNbkr;
    }

    public String getRubNbkr() {
        return rubNbkr;
    }

    public String getUsdBanksSell() {
        return usdBanksSell;
    }

    public String getUsdBanksBuy() {
        return usdBanksBuy;
    }

    public String getEurBanksSell() {
        return eurBanksSell;
    }

    public String getEurBanksBuy() {
        return eurBanksBuy;
    }

    public String getRubBanksSell() {
        return rubBanksSell;
    }

    public String getRubBanksBuy() {
        return rubBanksBuy;
    }

    public String getKztNbkr() {
        return kztNbkr;
    }

    public String getKztBanksSell() {
        return kztBanksSell;
    }

    public String getKztBanksBuy() {
        return kztBanksBuy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.usdNbkr);
        dest.writeString(this.eurNbkr);
        dest.writeString(this.rubNbkr);
        dest.writeString(this.kztNbkr);
        dest.writeString(this.usdBanksSell);
        dest.writeString(this.usdBanksBuy);
        dest.writeString(this.eurBanksSell);
        dest.writeString(this.eurBanksBuy);
        dest.writeString(this.rubBanksSell);
        dest.writeString(this.rubBanksBuy);
        dest.writeString(this.kztBanksSell);
        dest.writeString(this.kztBanksBuy);
    }

    public ExchangeRatesVM() {
    }

    protected ExchangeRatesVM(Parcel in) {
        this.usdNbkr = in.readString();
        this.eurNbkr = in.readString();
        this.rubNbkr = in.readString();
        this.kztNbkr = in.readString();
        this.usdBanksSell = in.readString();
        this.usdBanksBuy = in.readString();
        this.eurBanksSell = in.readString();
        this.eurBanksBuy = in.readString();
        this.rubBanksSell = in.readString();
        this.rubBanksBuy = in.readString();
        this.kztBanksSell = in.readString();
        this.kztBanksBuy = in.readString();
    }

    public static final Creator<ExchangeRatesVM> CREATOR = new Creator<ExchangeRatesVM>() {
        @Override
        public ExchangeRatesVM createFromParcel(Parcel source) {
            return new ExchangeRatesVM(source);
        }

        @Override
        public ExchangeRatesVM[] newArray(int size) {
            return new ExchangeRatesVM[size];
        }
    };

    public static final class Builder {
        private String usdNbkr;
        private String eurNbkr;
        private String rubNbkr;
        private String kztNbkr;
        private String usdBanksSell;
        private String usdBanksBuy;
        private String eurBanksSell;
        private String eurBanksBuy;
        private String rubBanksSell;
        private String rubBanksBuy;
        private String kztBanksSell;
        private String kztBanksBuy;

        public Builder() {
        }

        public Builder usdNbkr(String val) {
            usdNbkr = val;
            return this;
        }

        public Builder eurNbkr(String val) {
            eurNbkr = val;
            return this;
        }

        public Builder rubNbkr(String val) {
            rubNbkr = val;
            return this;
        }

        public Builder kztNbkr(String val) {
            kztNbkr = val;
            return this;
        }

        public Builder usdBanksSell(String val) {
            usdBanksSell = val;
            return this;
        }

        public Builder usdBanksBuy(String val) {
            usdBanksBuy = val;
            return this;
        }

        public Builder eurBanksSell(String val) {
            eurBanksSell = val;
            return this;
        }

        public Builder eurBanksBuy(String val) {
            eurBanksBuy = val;
            return this;
        }

        public Builder rubBanksSell(String val) {
            rubBanksSell = val;
            return this;
        }

        public Builder rubBanksBuy(String val) {
            rubBanksBuy = val;
            return this;
        }

        public Builder kztBanksSell(String val) {
            kztBanksSell = val;
            return this;
        }

        public Builder kztBanksBuy(String val) {
            kztBanksBuy = val;
            return this;
        }

        public ExchangeRatesVM build() {
            return new ExchangeRatesVM(this);
        }
    }
}
