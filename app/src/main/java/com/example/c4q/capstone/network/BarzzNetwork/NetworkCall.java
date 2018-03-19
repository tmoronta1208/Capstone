package com.example.c4q.capstone.network.BarzzNetwork;

import android.util.Log;

import com.example.c4q.capstone.network.BarzzNetwork.model.BarzzModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by c4q on 3/17/18.
 */

public class NetworkCall {

    private static String type;
    private static String amenities;
    private static String zipCode;


    public static final  String BARZZ_KEY = "96b42918912ec3f1d85660e18356617c";
    public static final String BARZZ_URL = "https://api.barzz.net/api/";

    public NetworkCall(String zipCode) {
        this.zipCode =zipCode;
    }

    public NetworkCall(String zipCode, String type) {
        this.zipCode = zipCode;
        this.type = type;
    }

    public NetworkCall(String type, String amenities, String zipCode) {
        this.type = type;
        this.amenities = amenities;
        this.zipCode = zipCode;
    }

    public static void start() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BARZZ_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        BarzzService barzzService = retrofit.create(BarzzService.class);

        Call<BarzzModel> call = barzzService.getBarzz(zipCode, type);

        call.enqueue(new Callback<BarzzModel>() {
            @Override
            public void onResponse(Call<BarzzModel> call, Response<BarzzModel> response) {

                Log.d("SUCCESSSS!!!!!!!", response.body().toString());
                Log.d("First Item: ", response.body().getSuccess().getResults().get(0).getName() );

            }

            @Override
            public void onFailure(Call<BarzzModel> call, Throwable t) {

                t.printStackTrace();

            }
        });
    }


}
