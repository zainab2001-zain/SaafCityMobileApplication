package com.buiseness.saafcitybuiseness.Model;

import com.buiseness.saafcitybuiseness.ApiClient;
import com.buiseness.saafcitybuiseness.Interface.API.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ComplainantResponse {


    private ApiService apiService;

    public ComplainantResponse() {
        apiService = ApiClient.getApiClient().create(ApiService.class);
    }

    public void getDetails(Complainant complainant, final OnGetDetailsListener listener) {
        Call<Complainant> call = apiService.getDetails(complainant);
        call.enqueue(new Callback<Complainant>() {
            @Override
            public void onResponse(Call<Complainant> call, Response<Complainant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure("Failed to get details.");
                }
            }

            @Override
            public void onFailure(Call<Complainant> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void login(Complainant complainant, final OnLoginListener listener) {
        Call<String> call = apiService.login(complainant);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure("Failed to login.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void signup(Complainant complainant, final OnSignUpListener listener) {
        Call<Complainant> call = apiService.signup(complainant);
        call.enqueue(new Callback<Complainant>() {
            @Override
            public void onResponse(Call<Complainant> call, Response<Complainant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure("Failed to signup.");
                }
            }

            @Override
            public void onFailure(Call<Complainant> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public interface OnGetDetailsListener {
        void onSuccess(Complainant complainant);
        void onFailure(String errorMessage);
    }

    public interface OnLoginListener {
        void onSuccess(String message);
        void onFailure(String errorMessage);
    }

    public interface OnSignUpListener {
        void onSuccess(Complainant complainant);
        void onFailure(String errorMessage);
    }
}

