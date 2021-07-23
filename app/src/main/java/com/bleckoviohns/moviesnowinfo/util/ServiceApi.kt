package com.bleckoviohns.moviesnowinfo.util

import com.bleckoviohns.moviesnowinfo.data.model.*
import com.bleckoviohns.moviesnowinfo.videodetail.data.model.MovieNow
import com.bleckoviohns.moviesnowinfo.videodetail.data.model.TvSeriesNow
import retrofit2.http.*

interface  ServiceApi {
    @GET("search/multi")
     suspend fun findMoviesAndTvSeries(@Query("language") language:String="es-US",@Query("page") page:Int=1,@Query("query") query:String): ResponseMediaVideo

    @GET("movie/{typeFilter}")
    suspend fun getListMoviesX(@Path("typeFilter" ) typeFilter:String, @Query("language") language:String="es-US",@Query("page") page:Int=1 ): ResponseMediaVideo

    @GET("tv/{typeFilter}")
    suspend fun getListTvSeriesX(@Path("typeFilter" ) typeFilter:String, @Query("language") language:String="es-US",@Query("page") page:Int=1 ): ResponseMediaVideo

    @GET("movie/{id}")
    suspend fun getDetailMovie(@Path("id" ) id:Long, @Query("language") language:String="es-MX"):MovieNow

    @GET("tv/{id}")
    suspend fun getDetailTvSeries(@Path("id" ) id:Long, @Query("language") language:String="es-MX"): TvSeriesNow


    @GET("movie/{id}/videos")
    suspend fun getVideosMovie(@Path("id" ) id:Long, @Query("language") language:String="es-US"): ResponseMediaVideo

    @GET("tv/{id}/videos")
    suspend fun getVideosTvSeries(@Path("id" ) id:Long, @Query("language") language:String="es-US"): ResponseMediaVideo

}