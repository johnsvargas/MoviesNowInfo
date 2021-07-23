package com.bleckoviohns.moviesnowinfo.listofvideo.data.repository

import com.bleckoviohns.moviesnowinfo.data.model.ResponseMediaVideo
import com.bleckoviohns.moviesnowinfo.util.NetworkClient
import com.bleckoviohns.moviesnowinfo.util.ServiceApi
import retrofit2.create

class ListOfVideosRepositoryImpl:ListOfVideosRepository {
    private val service = NetworkClient.buildRetrofitClient().create<ServiceApi>()
    override suspend fun getFindMoviesAndTvSeries(query: String, page: Int): ResponseMediaVideo {
        return service.findMoviesAndTvSeries(query= query, page =  page)
    }
}