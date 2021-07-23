package com.bleckoviohns.moviesnowinfo.videodetail.data.repository

import com.bleckoviohns.moviesnowinfo.util.NetworkClient
import com.bleckoviohns.moviesnowinfo.util.ServiceApi
import com.bleckoviohns.moviesnowinfo.videodetail.data.model.MovieNow
import com.bleckoviohns.moviesnowinfo.videodetail.data.model.TvSeriesNow
import retrofit2.create

class VideoDetailRepositoryImpl:VideoDetailRepository {
    private val service = NetworkClient.buildRetrofitClient().create<ServiceApi>()
    override suspend fun getMovieDetail(id: Long): MovieNow {
        return service.getDetailMovie(id)
    }

    override suspend fun getTvSeriesDetail(id: Long): TvSeriesNow {
        return service.getDetailTvSeries(id)
    }
}