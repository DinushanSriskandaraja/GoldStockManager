package Interfaces;

import Models.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("v1/latest")
    Call<ApiResponse> fetchData(@Query("api_key") String apiKey,
                                       @Query("base") String base,
                                       @Query("currencies") String currencies);
}
