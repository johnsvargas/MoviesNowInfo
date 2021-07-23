package com.bleckoviohns.moviesnowinfo.dashboard.data.repository

import com.bleckoviohns.moviesnowinfo.data.model.*

interface DashboardRepository {
    suspend fun getListMovies(typeFilter:String,page: Int):ResponseMediaVideo
    suspend fun getListTvSeries(typeFilter:String,page: Int):ResponseMediaVideo
}