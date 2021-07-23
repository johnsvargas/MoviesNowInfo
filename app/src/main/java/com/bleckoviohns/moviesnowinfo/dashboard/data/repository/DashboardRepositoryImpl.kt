package com.bleckoviohns.moviesnowinfo.dashboard.data.repository

import com.bleckoviohns.moviesnowinfo.data.model.*
import com.bleckoviohns.moviesnowinfo.util.NetworkClient
import com.bleckoviohns.moviesnowinfo.util.ServiceApi
import retrofit2.create

class DashboardRepositoryImpl:DashboardRepository {
    private val service = NetworkClient.buildRetrofitClient().create<ServiceApi>()

    override suspend fun getListMovies(typeFilter: String,page: Int): ResponseMediaVideo {
        val response = service.getListMoviesX(typeFilter = typeFilter, page = page)
        response.results.forEach {
            it.mediaType = MediaType.Movie.value
        }
        return response
    }

    override suspend fun getListTvSeries(typeFilter: String,page: Int): ResponseMediaVideo {
        val response = service.getListTvSeriesX(typeFilter = typeFilter, page = page)
        response.results.forEach {
            it.mediaType = MediaType.Tv.value
        }
        return  response
    }
}