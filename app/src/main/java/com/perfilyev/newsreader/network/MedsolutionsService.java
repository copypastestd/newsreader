package com.perfilyev.newsreader.network;

import com.perfilyev.newsreader.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MedsolutionsService {

    @GET("/api/v1/news")
    Call<ApiResponse> listNews(@Query("page") int page,
                              @Query("limit") int limit,
                              @Query("order_by") String orderBy,
                              @Query("order") String order);

    @GET("/api/v1/news/{id}")
    Call<ApiResponse> getArticle(@Path("id") int id);
}
