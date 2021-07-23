package com.bleckoviohns.moviesnowinfo.listofvideo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bleckoviohns.moviesnowinfo.R
import com.bleckoviohns.moviesnowinfo.adapters.ItemMediaVideoAdapter
import com.bleckoviohns.moviesnowinfo.dashboard.ui.DashboardFragment
import com.bleckoviohns.moviesnowinfo.data.model.MediaVideo
import com.bleckoviohns.moviesnowinfo.databinding.FragmentListOfVideosBinding
import com.bleckoviohns.moviesnowinfo.listofvideo.viewmodel.ListOfVideosViewModel
import com.bleckoviohns.moviesnowinfo.util.Constants.CATEGORY
import com.bleckoviohns.moviesnowinfo.util.Constants.TITLE_TOOLBAR
import com.bleckoviohns.moviesnowinfo.util.Constants.TYPE_OF_VIDEO
import com.bleckoviohns.moviesnowinfo.util.Constants.TYPE_TV_SERIES
import com.bleckoviohns.moviesnowinfo.util.Constants.TYPE_MOVIE
import com.bleckoviohns.moviesnowinfo.util.Constants.TYPE_SEARCH
import com.bleckoviohns.moviesnowinfo.util.commonviews.RecyclerViewOnScroll
import com.bleckoviohns.moviesnowinfo.videodetail.ui.VideoDetailFragment

class ListOfVideosFragment: Fragment() {
    private lateinit var binding: FragmentListOfVideosBinding
    private val viewModel by viewModels<ListOfVideosViewModel>()
    private val mediaVideoAdapter by lazy{ ItemMediaVideoAdapter() }

    private var typeOfVideo = ""
    private var category = ""
    private var titleToolbar = ""
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            typeOfVideo =  this.getString(TYPE_OF_VIDEO)?:""
            category = this.getString(CATEGORY)?:""
            titleToolbar = this.getString(TITLE_TOOLBAR)?:""
            getDataVideo()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_of_videos,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
        init()
    }

    private fun init(){
        binding.run {
            tvTitleToolbar.text = titleToolbar

            ivOnBackPressed.setOnClickListener {
                activity?.onBackPressed()
            }

            mediaVideoAdapter.setOnClickListener { mediaVideo, title ->
                navigateVideoDetail(bundleOf(
                    TYPE_OF_VIDEO to mediaVideo.mediaType,
                    VideoDetailFragment.ID_VIDEO to mediaVideo.id,
                    VideoDetailFragment.URL_POSTER to mediaVideo.getPosterImage(),
                    VideoDetailFragment.TITLE to title
                ))
            }
            rvListOfVideos.layoutManager = GridLayoutManager(requireContext(),2)
            rvListOfVideos.adapter = mediaVideoAdapter
            rvListOfVideos.addOnScrollListener(object : RecyclerViewOnScroll() {
                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

                override fun loadMoreItems() {
                    isLoading = true
                    getDataVideo()
                }
            })
        }

        viewModel.listDataForSearching.observe(viewLifecycleOwner){state ->
            state.fold({
                setDataSearching(it)
            }, { e, retry ->
                Log.e(DashboardFragment::class.java.simpleName ,e.message.toString())
            })
        }
    }

    private fun navigateVideoDetail(bundle: Bundle){
        findNavController().navigate(R.id.action_listOfVideosFragment_to_videoDetailFragment,bundle)
    }

    private fun getDataVideo(){
        when(typeOfVideo){
            TYPE_MOVIE -> { viewModel.getListMoviesVideoX(category) }
            TYPE_TV_SERIES -> { viewModel.getListTvSeriesVideoX(category) }
            TYPE_SEARCH -> {viewModel.getFindMoviesAndTvSeries(titleToolbar)}
        }
    }

    private fun setDataSearching(data:ArrayList<MediaVideo>){
        binding.run {
            isLoading = false
            isLastPage = viewModel?.isLastPage?:false
            mediaVideoAdapter.setData(data)
        }
    }
}