package com.bleckoviohns.moviesnowinfo.videodetail.data.repository

import com.bleckoviohns.moviesnowinfo.videodetail.data.model.MovieNow
import com.bleckoviohns.moviesnowinfo.videodetail.data.model.TvSeriesNow

interface VideoDetailRepository {
    suspend fun getMovieDetail(id: Long): MovieNow
    suspend fun getTvSeriesDetail(id: Long): TvSeriesNow
}