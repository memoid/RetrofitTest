package com.example.gmoro.retrofittest;

import android.util.Log;

import com.example.gmoro.retrofittest.entities.RelatedTopic;
import com.example.gmoro.retrofittest.entities.Result;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gmoro on 3/11/2016.
 */
public class    RetrofitHelper {

    private static final String DATA_API = "http://api.duckduckgo.com/";

    public static void main(String[] args) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DATA_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DuckService duckService = retrofit.create(DuckService.class);

        Call<Result> listCall = duckService.listCharacters("game of thrones characters");

        Result results = null;

        try {
            results = listCall.execute().body();
        } catch (Exception e) {
            Log.e("Error", "Error: " + e.toString());
        }

        for (RelatedTopic relatedTopic : results.getRelatedTopics())
            System.out.println(relatedTopic.getText());

    }

}
