package kg.zima.gesvitaly.zanozakg.restApi.kursRestApi;

import kg.zima.gesvitaly.zanozakg.models.exchangeRates.ExchangeRatesPair;
import retrofit2.Call;
import retrofit2.http.GET;

public interface KursApiService {
    @GET("rateJson")
    Call<ExchangeRatesPair> loadExchangeRates();
}
