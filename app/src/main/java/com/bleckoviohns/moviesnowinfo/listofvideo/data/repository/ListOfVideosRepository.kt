package com.bleckoviohns.moviesnowinfo.listofvideo.data.repository

import com.bleckoviohns.moviesnowinfo.data.model.ResponseMediaVideo

interface ListOfVideosRepository {
    suspend fun getFindMoviesAndTvSeries(query:String,page:Int=1): ResponseMediaVideo
}