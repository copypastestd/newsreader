package com.perfilyev.newsreader.network;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.perfilyev.newsreader.models.ApiResponse;
import com.perfilyev.newsreader.models.Article;
import com.perfilyev.newsreader.models.Spotlight;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Класс, служащий для запросов и парсинга API.
 */
public class Medsolutions {

    private OkHttpClient client;
    private MedsolutionsService service;
    private Retrofit retrofit;
    private NewsUpdatedListener listener;

    private static final int PAGE = 1;
    private static final int LIMIT = 5;
    private static final String ORDER_BY = "created_at";
    private static final String ORDER = "desc";
    private static final String BASE_URL = "http://medsolutions.uxp.ru";
    private static final String TAG = Medsolutions.class.getSimpleName();

    public Medsolutions() {
        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("API-KEY", "secret_key")
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        service = retrofit.create(MedsolutionsService.class);
        listener = null;
    }

    public void setListener(NewsUpdatedListener listener) {
        this.listener = listener;
    }

    /**
     * Метод, который загружает список новостей в бэкграундном потоке, парсит их и отправляет во
     * фрагмент.
     */
    public void loadNewsFeed(int page) {
        Call<ApiResponse> call = service.listNews(page, LIMIT, ORDER_BY, ORDER);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (listener != null && response.body() != null) {
                    listener.onNewsLoaded(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    /**
     * Метод, который загружает новость в бэкграундном потоке, парсит ее и отправляет во фрагмент.
     */
    public void loadArticle(int id) {
        Call<ApiResponse> call = service.getArticle(id);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (listener != null && response.body() != null) {
                    listener.onArticleLoaded(response.body().getData(), response.body().getSpotlight());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    /**
     * Интерфейс для взаимодействия с фрагментами.
     */
    public interface NewsUpdatedListener {
        void onNewsLoaded(List<Article> articles);
        void onArticleLoaded(List<Article> articles, List<Spotlight> spotlights);
    }
}
