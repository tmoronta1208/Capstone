package com.example.c4q.capstone.network.BarzzNetwork;

import com.example.c4q.capstone.network.BarzzNetwork.model.BarzzModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by c4q on 3/17/18.
 */

public interface BarzzService {

    @GET("search?&user_key=" + NetworkCall.BARZZ_KEY)
    Call<BarzzModel> getBarzz(@Query("zip") String zipCode);

    @GET("search?&user_key=" + NetworkCall.BARZZ_KEY)
    Call<BarzzModel> getBarzz(@Query("zip") String zipCode,
                              @Query("type") String getType);


    @GET("search?&user_key=" + NetworkCall.BARZZ_KEY)
    Call<BarzzModel> getBarzz(@Query("zip") String zipCode,
                              @Query("type") String type,
                              @Query("amenity") String amenities);

}
