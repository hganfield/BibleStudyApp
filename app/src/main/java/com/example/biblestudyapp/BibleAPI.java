package com.example.biblestudyapp;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface BibleAPI {

    @Headers("api-key: 62250295c32a29422b4ce36c4c2a4d30")
    @GET("v1/bibles/")
    Call<Object> getBibles();

    @Headers("api-key: " + "PUT_YOUR_API_KEY")
    @GET("books/")
    Call<ArrayList<Bible>> getBooks();


}
