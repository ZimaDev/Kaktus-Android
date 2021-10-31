package kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ZanozaApiService {

    @FormUrlEncoded
    @POST("other/android.php?event=get_topics")
    Call<ResponseForLoadNewsPreviewsList> loadNewsPreviewsList(
            @Field("querys") String querys
    );

    @FormUrlEncoded
    @POST("other/android.php?event=get_topic")
    Call<ResponseForLoadPieceOfArticle> loadPieceOfArticle(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("other/android.php?event=set_rate")
    Call<ResponseForSendRate> sendRate(
            @Field("rate_user_uuid") String userUUID,
            @Field("rate_value") double rateValue,
            @Field("rate_text") String text,
            @Field("rate_app_version") String appVersion,
            @Field("rate_sdk_version") int sdkVersion,
            @Field("rate_android_version") String androidVersion,
            @Field("rate_screen_width") int screenWidth,
            @Field("rate_screen_height") int screenHeight,
            @Field("rate_phone_manufacturer") String phoneManufacturer,
            @Field("rate_phone_model") String phoneModel,
            @Field("rate_phone_is_rooted") int isRooted,
            @Field("rate_hardware") String hardware,
            @Field("rate_processor") String processor,
            @Field("rate_package_name") String package_name
    );
}
