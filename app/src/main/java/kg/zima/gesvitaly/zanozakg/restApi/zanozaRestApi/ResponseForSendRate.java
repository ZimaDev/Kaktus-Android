package kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi;

import com.google.gson.annotations.SerializedName;

public class ResponseForSendRate {
    @SerializedName("success")
    private String success;
    @SerializedName("rate_id")
    private int rateId;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }
}
