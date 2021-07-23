package com.bleckoviohns.moviesnowinfo.videodetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bleckoviohns.moviesnowinfo.util.LiveDataState
import com.bleckoviohns.moviesnowinfo.util.MutableLiveDataState
import com.bleckoviohns.moviesnowinfo.util.StateApp
import com.bleckoviohns.moviesnowinfo.videodetail.data.model.MovieNow
import com.bleckoviohns.moviesnowinfo.videodetail.data.model.TvSeriesNow
import com.bleckoviohns.moviesnowinfo.videodetail.data.repository.VideoDetailRepositoryImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class VideoDetailViewModel: ViewModel() {
    private val videoDetailRepository by lazy { VideoDetailRepositoryImpl() }

    private val _detailMovie: MutableLiveDataState<MovieNow> by lazy { MutableLiveDataState() }
    val detailMovie: LiveDataState<MovieNow> = _detailMovie

    private val _detailTvSeries: MutableLiveDataState<TvSeriesNow> by lazy { MutableLiveDataState() }
    val detailTvSeries: LiveDataState<TvSeriesNow> = _detailTvSeries

    fun getDetailMovie(id:Long){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler{ _, throwable ->
            _detailMovie.postValue(StateApp.Error(throwable))
        }){
            val response = videoDetailRepository.getMovieDetail(id)
            _detailMovie.postValue(StateApp.Success(response))
        }
    }

    fun getDetailTvSeries(id:Long){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler{ _, throwable ->
            _detailTvSeries.postValue(StateApp.Error(throwable))
        }){
            val response = videoDetailRepository.getTvSeriesDetail(id)
            _detailTvSeries.postValue(StateApp.Success(response))
        }
    }
}