package kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.models.Quiz;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface QuizApiService {
    @FormUrlEncoded
    @POST("api.php")
    Call<Quiz> loadQuiz(
            @Field("id") int id
    );
    @FormUrlEncoded
    @POST("api.php")
    Call<Quiz> vote(
            @Field("vote") String vote,
            @Field("variant") ArrayList<Integer> variants_ids
    );
}
