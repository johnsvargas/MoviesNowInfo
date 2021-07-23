package com.bleckoviohns.moviesnowinfo.dashboard.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bleckoviohns.moviesnowinfo.R
import com.bleckoviohns.moviesnowinfo.adapters.ItemMediaVideoAdapter
import com.bleckoviohns.moviesnowinfo.databinding.FragmentDashboardBinding
import com.bleckoviohns.moviesnowinfo.dashboard.viewmodel.DashboardViewModel
import com.bleckoviohns.moviesnowinfo.data.model.MediaVideo
import com.bleckoviohns.moviesnowinfo.util.Constants.CATEGORY
import com.bleckoviohns.moviesnowinfo.util.Constants.CAT_POPULAR
import com.bleckoviohns.moviesnowinfo.util.Constants.CAT_TOP
import com.bleckoviohns.moviesnowinfo.util.Constants.TITLE_TOOLBAR
import com.bleckoviohns.moviesnowinfo.util.Constants.TYPE_MOVIE
import com.bleckoviohns.moviesnowinfo.util.Constants.TYPE_OF_VIDEO
import com.bleckoviohns.moviesnowinfo.util.Constants.TYPE_SEARCH
import com.bleckoviohns.moviesnowinfo.util.Constants.TYPE_TV_SERIES
import com.bleckoviohns.moviesnowinfo.videodetail.ui.VideoDetailFragment

class DashboardFragment: Fragment() {
    private lateinit var binding : FragmentDashboardBinding
    private val viewModel by viewModels<DashboardViewModel>()

    private val popularMoviesAdapter by lazy{ ItemMediaVideoAdapter() }
    private val topMoviesAdapter by lazy{ ItemMediaVideoAdapter()}
    private val popularTvSeriesAdapter by lazy{ ItemMediaVideoAdapter()}
    private val topTvSeriesAdapter by lazy{ ItemMediaVideoAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListPopularMovies()
        viewModel.getListTopMovies()
        viewModel.getListPopularTvSeries()
        viewModel.getListTopTvSeries()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard,container,false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        viewModel.listPopularMovies.observe(viewLifecycleOwner){state ->
            state.fold({
                setPopularMovies(it)
            }, { e, retry ->
                Log.e(DashboardFragment::class.java.simpleName ,e.message.toString())
            })
        }

        viewModel.listTopMovies.observe(viewLifecycleOwner){ state ->
            state.fold({
                setTopMovies(it)
            }, { e, retry ->
                Log.e(DashboardFragment::class.java.simpleName ,e.message.toString())
            })
        }

        viewModel.listPopularTvSeries.observe(viewLifecycleOwner){ state ->
            state.fold({
                setPopularTvSeries(it)
            }, { e, retry ->
                Log.e(DashboardFragment::class.java.simpleName ,e.message.toString())
            })
        }

        viewModel.listTopTvSeries.observe(viewLifecycleOwner){ state ->
            state.fold({
                setTopTvSeries(it)
            }, { e, retry ->
                Log.e(DashboardFragment::class.java.simpleName ,e.message.toString())
            })
        }

        binding.run{
            etDashboardFind.setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        val queryToSearch = etDashboardFind.text.toString()
                        if(queryToSearch.isNotEmpty()){
                            navigateListOfVideos(bundleOf(
                                TYPE_OF_VIDEO to TYPE_SEARCH,
                                TITLE_TOOLBAR to queryToSearch
                            ))
                        }
                        true
                    }
                    else -> false
                }
            }

            llDashboardPopularMovies.setOnClickListener {
                navigateListOfVideos(bundleOf(
                    TYPE_OF_VIDEO to TYPE_MOVIE,
                    CATEGORY to CAT_POPULAR,
                    TITLE_TOOLBAR to resources.getString(R.string.label_dashboard_popular_movies)
                ))

            }

            llDashboardTopMovies.setOnClickListener {
                navigateListOfVideos(bundleOf(
                    TYPE_OF_VIDEO to TYPE_MOVIE,
                    CATEGORY to CAT_TOP,
                    TITLE_TOOLBAR to resources.getString(R.string.label_dashboard_top_movies)
                ))
            }

            llDashboardPopularTvSeries.setOnClickListener {
                navigateListOfVideos(bundleOf(
                    TYPE_OF_VIDEO to TYPE_TV_SERIES,
                    CATEGORY to CAT_POPULAR,
                    TITLE_TOOLBAR to resources.getString(R.string.label_dashboard_popular_tv_series)
                ))
            }

            llDashboardTopTvSeries.setOnClickListener {
                navigateListOfVideos(bundleOf(
                    TYPE_OF_VIDEO to TYPE_TV_SERIES,
                    CATEGORY to CAT_TOP,
                    TITLE_TOOLBAR to resources.getString(R.string.label_dashboard_top_tv_series)
                ))
            }
        }
    }

    private fun navigateListOfVideos(bundle: Bundle){
        findNavController().navigate(R.id.action_dashboardFragment_to_listOfVideosFragment,bundle)
    }

    private fun navigateVideoDetail(bundle: Bundle){
        findNavController().navigate(R.id.action_dashboardFragment_to_videoDetailFragment,bundle)
    }

    private fun setPopularMovies(popularMovies:ArrayList<MediaVideo>){
        binding.run {
            popularMoviesAdapter.setData(popularMovies)
            popularMoviesAdapter.setOnClickListener { mediaVideo,title ->
                navigateVideoDetail(bundleOf(
                    TYPE_OF_VIDEO to TYPE_MOVIE,
                    VideoDetailFragment.ID_VIDEO to mediaVideo.id,
                    VideoDetailFragment.URL_POSTER to mediaVideo.getPosterImage(),
                    VideoDetailFragment.TITLE to title
                ))
            }
            rvDashboardMostPopularMovies.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rvDashboardMostPopularMovies.adapter = popularMoviesAdapter
        }
    }

    private fun setTopMovies(topMovies:ArrayList<MediaVideo>){
        binding.run {
            topMoviesAdapter.setData(topMovies)
            topMoviesAdapter.setOnClickListener { mediaVideo,title ->
                navigateVideoDetail(bundleOf(
                    TYPE_OF_VIDEO to TYPE_MOVIE,
                    VideoDetailFragment.ID_VIDEO to mediaVideo.id,
                    VideoDetailFragment.URL_POSTER to mediaVideo.getPosterImage(),
                    VideoDetailFragment.TITLE to title
                ))
            }
            rvDashboardTopMovies.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rvDashboardTopMovies.adapter = topMoviesAdapter
        }
    }

    private fun setPopularTvSeries(popularTvSeries:ArrayList<MediaVideo>){
        binding.run {
            popularTvSeriesAdapter.setData(popularTvSeries)
            popularTvSeriesAdapter.setOnClickListener { mediaVideo,title ->
                navigateVideoDetail(bundleOf(
                    TYPE_OF_VIDEO to TYPE_TV_SERIES,
                    VideoDetailFragment.ID_VIDEO to mediaVideo.id,
                    VideoDetailFragment.URL_POSTER to mediaVideo.getPosterImage(),
                    VideoDetailFragment.TITLE to title
                ))
            }
            rvDashboardMostPopularTvSeries.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rvDashboardMostPopularTvSeries.adapter = popularTvSeriesAdapter
        }
    }

    private fun setTopTvSeries(topTvSeries:ArrayList<MediaVideo>){
        binding.run {
            topTvSeriesAdapter.setData(topTvSeries)
            topTvSeriesAdapter.setOnClickListener { mediaVideo,title ->
                navigateVideoDetail(bundleOf(
                    TYPE_OF_VIDEO to TYPE_TV_SERIES,
                    VideoDetailFragment.ID_VIDEO to mediaVideo.id,
                    VideoDetailFragment.URL_POSTER to mediaVideo.getPosterImage(),
                    VideoDetailFragment.TITLE to title
                ))
            }
            rvDashboardTopTvSeries.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rvDashboardTopTvSeries.adapter = topTvSeriesAdapter
        }
    }
}