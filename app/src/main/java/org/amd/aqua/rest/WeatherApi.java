package org.amd.aqua.rest;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Akira on 2018/04/10.
 */

public interface WeatherApi {
    @GET("/data/2.5/{name}")
    public Observable<WeatherResponse> get(@Path("name") String name, @Query("q") String q);
}
