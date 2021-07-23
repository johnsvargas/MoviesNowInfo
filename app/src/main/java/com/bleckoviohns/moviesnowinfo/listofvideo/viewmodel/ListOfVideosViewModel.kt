package com.bleckoviohns.moviesnowinfo.listofvideo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bleckoviohns.moviesnowinfo.dashboard.data.repository.DashboardRepositoryImpl
import com.bleckoviohns.moviesnowinfo.data.model.MediaType
import com.bleckoviohns.moviesnowinfo.data.model.MediaVideo
import com.bleckoviohns.moviesnowinfo.listofvideo.data.repository.ListOfVideosRepositoryImpl
import com.bleckoviohns.moviesnowinfo.util.LiveDataState
import com.bleckoviohns.moviesnowinfo.util.MutableLiveDataState
import com.bleckoviohns.moviesnowinfo.util.StateApp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListOfVideosViewModel: ViewModel() {
    private val dashboardRepository by lazy { DashboardRepositoryImpl() }
    private val listOfVideosRepository by lazy {ListOfVideosRepositoryImpl()}
    private val _listDataForSearching: MutableLiveDataState<ArrayList<MediaVideo>> by lazy { MutableLiveDataState() }
    val listDataForSearching: LiveDataState<ArrayList<MediaVideo>> = _listDataForSearching

    var isLastPage = false
    private var numPage = 1

    fun getFindMoviesAndTvSeries(query: String){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler{_,throwable ->
            _listDataForSearching.postValue(StateApp.Error(throwable))
        }){
            val response = listOfVideosRepository.getFindMoviesAndTvSeries(query,numPage)
            checkLastPage(response.totalPages)
            _listDataForSearching.postValue(StateApp.Success(response.results))
        }
    }

    fun getListMoviesVideoX(category: String){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _listDataForSearching.postValue(StateApp.Error(throwable))
        }){
            val response = dashboardRepository.getListMovies(category,numPage)
            checkLastPage(response.totalPages)
            _listDataForSearching.postValue(StateApp.Success(response.results))
        }
    }

    fun getListTvSeriesVideoX(category: String){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _listDataForSearching.postValue(StateApp.Error(throwable))
        }){
            val response = dashboardRepository.getListTvSeries(category,numPage)
            checkLastPage(response.totalPages)
            _listDataForSearching.postValue(StateApp.Success(response.results))
        }
    }

    private fun checkLastPage(totalPages:Long){
        if(numPage <= totalPages){
            numPage += 1
        }else{
            isLastPage = true
        }
    }
}
