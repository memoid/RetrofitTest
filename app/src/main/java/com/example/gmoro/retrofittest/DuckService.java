package com.example.gmoro.retrofittest;

import com.example.gmoro.retrofittest.entities.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gmoro on 3/11/2016.
 */
public interface DuckService {

    @GET("?format=json")
    Call<Result> listCharacters(@Query("q") String q);

}
