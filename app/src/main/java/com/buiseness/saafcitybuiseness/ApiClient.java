package com.buiseness.saafcitybuiseness;

import com.buiseness.saafcitybuiseness.Interface.API.ApiService;
import com.buiseness.saafcitybuiseness.Model.EnvVariables;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;

public class ApiClient {
    private static Retrofit retrofit = null;

    private static final String BASE_URL =EnvVariables.MY_IP_ADDRESS ;


    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
