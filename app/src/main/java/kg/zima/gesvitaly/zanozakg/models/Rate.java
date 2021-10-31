package kg.zima.gesvitaly.zanozakg.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Rate implements Parcelable {
    @SerializedName("rate_phone_serial")
    private String userUUID;
    @SerializedName("rate_value")
    private double rateValue;
    @SerializedName("rate_text")
    private String text;
    @SerializedName("rate_app_version")
    private String appVersion;
    @SerializedName("rate_sdk_version")
    private int sdkVersion;
    @SerializedName("rate_android_version")
    private String androidVersion;
    @SerializedName("rate_screen_width")
    private int screenWidth;
    @SerializedName("rate_screen_height")
    private int screenHeight;
    @SerializedName("rate_phone_manufacturer")
    private String phoneManufacturer;
    @SerializedName("rate_phone_model")
    private String phoneModel;
    @SerializedName("rate_is_rooted")
    private int isRooted;
    @SerializedName("rate_hardware")
    private String hardware;
    @SerializedName("rate_processor")
    private String processor;
    @SerializedName("rate_package_name")
    private String packageName;

    private Rate() {};

    public Rate(String userUUID, double rateValue, String text, String appVersion, int sdkVersion, String androidVersion, int screenWidth, int screenHeight, String phoneManufacturer, String phoneModel, int isRooted, String hardware, String processor, String packageName) {
        this.userUUID = userUUID;
        this.rateValue = rateValue;
        this.text = text;
        this.appVersion = appVersion;
        this.sdkVersion = sdkVersion;
        this.androidVersion = androidVersion;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.phoneManufacturer = phoneManufacturer;
        this.phoneModel = phoneModel;
        this.isRooted = isRooted;
        this.hardware = hardware;
        this.processor = processor;
        this.packageName = packageName;
    }

    public String getUserUUID() {
        return userUUID;
    }

    private void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public double getRateValue() {
        return rateValue;
    }

    private void setRateValue(double rateValue) {
        this.rateValue = rateValue;
    }

    public String getText() {
        return text;
    }

    private void setText(String text) {
        this.text = text;
    }

    public String getAppVersion() {
        return appVersion;
    }

    private void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getSdkVersion() {
        return sdkVersion;
    }

    private void setSdkVersion(int sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    private void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    private void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    private void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getPhoneManufacturer() {
        return phoneManufacturer;
    }

    private void setPhoneManufacturer(String phoneManufacturer) {
        this.phoneManufacturer = phoneManufacturer;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    private void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public int getIsRooted() {
        return isRooted;
    }

    private void setIsRooted(int isRooted) {
        this.isRooted = isRooted;
    }

    public String getHardware() {
        return hardware;
    }

    private void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getProcessor() {
        return processor;
    }

    private void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getPackageName() {
        return packageName;
    }

    private void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    public static final class Builder {
        String userUUID;
        double rateValue;
        String text;
        String appVersion;
        int sdkVersion;
        String androidVersion;
        int screenWidth;
        int screenHeight;
        String phoneManufacturer;
        String phoneModel;
        int isRooted;
        String hardware;
        String processor;
        String packageName;

        public Builder() {
        }

        public Builder userUUID(String userUUID) {
            this.userUUID = userUUID;
            return this;
        }

        public Builder rateValue(double rateValue) {
            this.rateValue = rateValue;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder appVersion(String appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        public Builder sdkVersion(int sdkVersion) {
            this.sdkVersion = sdkVersion;
            return this;
        }

        public Builder androidVersion(String androidVersion) {
            this.androidVersion = androidVersion;
            return this;
        }

        public Builder screenWidth(int screenWidth) {
            this.screenWidth = screenWidth;
            return this;
        }

        public Builder screenHeight(int screenHeight) {
            this.screenHeight = screenHeight;
            return this;
        }

        public Builder phoneManufacturer(String phoneManufacturer) {
            this.phoneManufacturer = phoneManufacturer;
            return this;
        }

        public Builder phoneModel(String phoneModel) {
            this.phoneModel = phoneModel;
            return this;
        }

        public Builder isRooted(int isRooted) {
            this.isRooted = isRooted;
            return this;
        }

        public Builder hardware(String hardware) {
            this.hardware = hardware;
            return this;
        }

        public Builder processor(String processor) {
            this.processor = processor;
            return this;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Rate build() {
            Rate rate = new Rate();
            rate.setUserUUID(userUUID);
            rate.setRateValue(rateValue);
            rate.setText(text);
            rate.setAppVersion(appVersion);
            rate.setSdkVersion(sdkVersion);
            rate.setAndroidVersion(androidVersion);
            rate.setScreenWidth(screenWidth);
            rate.setScreenHeight(screenHeight);
            rate.setPhoneManufacturer(phoneManufacturer);
            rate.setPhoneModel(phoneModel);
            rate.setIsRooted(isRooted);
            rate.setHardware(hardware);
            rate.setProcessor(processor);
            rate.setPackageName(packageName);
            return rate;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userUUID);
        dest.writeDouble(this.rateValue);
        dest.writeString(this.text);
        dest.writeString(this.appVersion);
        dest.writeInt(this.sdkVersion);
        dest.writeString(this.androidVersion);
        dest.writeInt(this.screenWidth);
        dest.writeInt(this.screenHeight);
        dest.writeString(this.phoneManufacturer);
        dest.writeString(this.phoneModel);
        dest.writeInt(this.isRooted);
        dest.writeString(this.hardware);
        dest.writeString(this.processor);
        dest.writeString(this.packageName);
    }

    private Rate(Parcel in) {
        this.userUUID = in.readString();
        this.rateValue = in.readDouble();
        this.text = in.readString();
        this.appVersion = in.readString();
        this.sdkVersion = in.readInt();
        this.androidVersion = in.readString();
        this.screenWidth = in.readInt();
        this.screenHeight = in.readInt();
        this.phoneManufacturer = in.readString();
        this.phoneModel = in.readString();
        this.isRooted = in.readInt();
        this.hardware = in.readString();
        this.processor = in.readString();
        this.packageName = in.readString();
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