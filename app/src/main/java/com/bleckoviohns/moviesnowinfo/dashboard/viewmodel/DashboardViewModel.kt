package com.bleckoviohns.moviesnowinfo.dashboard.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bleckoviohns.moviesnowinfo.dashboard.data.repository.DashboardRepositoryImpl
import com.bleckoviohns.moviesnowinfo.data.model.MediaVideo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.bleckoviohns.moviesnowinfo.util.LiveDataState
import com.bleckoviohns.moviesnowinfo.util.MutableLiveDataState
import com.bleckoviohns.moviesnowinfo.util.StateApp

class DashboardViewModel: ViewModel() {
    private val dashboardRepository by lazy{DashboardRepositoryImpl()}

    private val _listPopularMovies: MutableLiveDataState<ArrayList<MediaVideo>> by lazy { MutableLiveDataState() }
    val listPopularMovies: LiveDataState<ArrayList<MediaVideo>> = _listPopularMovies

    private val _listTopMovies: MutableLiveDataState<ArrayList<MediaVideo>> by lazy { MutableLiveDataState() }
    val listTopMovies: LiveDataState<ArrayList<MediaVideo>> = _listTopMovies

    private val _listPopularTvSeries: MutableLiveDataState<ArrayList<MediaVideo>> by lazy { MutableLiveDataState() }
    val listPopularTvSeries: LiveDataState<ArrayList<MediaVideo>> = _listPopularTvSeries

    private val _listTopTvSeries: MutableLiveDataState<ArrayList<MediaVideo>> by lazy { MutableLiveDataState() }
    val listTopTvSeries: LiveDataState<ArrayList<MediaVideo>> = _listTopTvSeries


    fun getListPopularMovies(){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _listPopularMovies.postValue(StateApp.Error(throwable))
        }){
            val response = dashboardRepository.getListMovies("popular",1)
            _listPopularMovies.postValue(StateApp.Success(response.results))
        }
    }

    fun getListTopMovies(){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _listTopMovies.postValue(StateApp.Error(throwable))
        }){
            val response = dashboardRepository.getListMovies("top_rated",1)
            _listTopMovies.postValue(StateApp.Success(response.results))
        }
    }

    fun getListPopularTvSeries(){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _listPopularTvSeries.postValue(StateApp.Error(throwable))
        }){
            val response = dashboardRepository.getListTvSeries("popular",1)
            _listPopularTvSeries.postValue(StateApp.Success(response.results))
        }
    }

    fun getListTopTvSeries(){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _listTopTvSeries.postValue(StateApp.Error(throwable))
        }){
            val response = dashboardRepository.getListTvSeries("top_rated",1)
            _listTopTvSeries.postValue(StateApp.Success(response.results))
        }
    }
}