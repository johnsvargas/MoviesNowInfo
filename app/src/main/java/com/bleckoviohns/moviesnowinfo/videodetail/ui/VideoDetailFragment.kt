package com.bleckoviohns.moviesnowinfo.videodetail.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bleckoviohns.moviesnowinfo.R
import com.bleckoviohns.moviesnowinfo.dashboard.ui.DashboardFragment
import com.bleckoviohns.moviesnowinfo.databinding.FragmentVideoDetailBinding
import com.bleckoviohns.moviesnowinfo.util.Constants
import com.bleckoviohns.moviesnowinfo.videodetail.data.model.MovieNow
import com.bleckoviohns.moviesnowinfo.videodetail.data.model.TvSeriesNow
import com.bleckoviohns.moviesnowinfo.videodetail.viewmodel.VideoDetailViewModel
import com.bumptech.glide.Glide


class VideoDetailFragment: Fragment() {
    private lateinit var binding: FragmentVideoDetailBinding
    private val viewModel by viewModels<VideoDetailViewModel>()

    private var typeOfVideo: String = ""
    private var urlPoster:String = ""
    private var idVideo:Long = 0
    private var title:String = ""
    private var description:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply{
            typeOfVideo =  this.getString(Constants.TYPE_OF_VIDEO)?:""
            urlPoster = this.getString(URL_POSTER,"")
            idVideo = this.getLong(ID_VIDEO,0)
            title = this.getString(TITLE,"")
            description = this.getString(DESCRIPTION,"")
            getData()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_detail,container,false)
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
        Glide.with(requireContext())
            .load(urlPoster)
            .placeholder(R.drawable.background_movies)
            .into(binding.ivDetailAppBarImage)

        binding.toolbar.title = title

        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        viewModel.detailMovie.observe(viewLifecycleOwner){state ->
            state.fold({
                setDataMovieInView(it)
            }, { e, retry ->
                Log.e(DashboardFragment::class.java.simpleName ,e.message.toString())
            })
        }

        viewModel.detailTvSeries.observe(viewLifecycleOwner){state ->
            state.fold({
                setDataTvSerieInView(it)
            }, { e, retry ->
                Log.e(DashboardFragment::class.java.simpleName ,e.message.toString())
            })
        }

    }

    fun getData(){
        when(typeOfVideo){
            Constants.TYPE_MOVIE -> { viewModel.getDetailMovie(idVideo)  }
            Constants.TYPE_TV_SERIES -> { viewModel.getDetailTvSeries(idVideo) }
        }
    }

    fun setDataMovieInView(movie: MovieNow){
        binding.run {
            tvDetailOriginalTitle.text = movie.originalTitle
            tvDetailVoteAverage.text = movie.voteAverage.toString()
            tvDetailDescription.text = movie.overview
            var  genresText = ""
            movie.genres.forEach {
                genresText += if(genresText.isEmpty()) it.name else  String.format(", ${it.name}")
            }
            tvDetailGenres.text = genresText
        }
    }

    fun setDataTvSerieInView(serie: TvSeriesNow){
        binding.run {
            tvDetailOriginalTitle.text = serie.originalName
            tvDetailVoteAverage.text = serie.voteAverage.toString()
            tvDetailDescription.text = serie.overview
            var  genresText = ""
            serie.genres.forEach {
                genresText += if(genresText.isEmpty()) it.name else  String.format(", ${it.name}")
            }
            tvDetailGenres.text = genresText
        }
    }

    companion object{
        const val ID_VIDEO = "idVideo"
        const val URL_POSTER = "urlVideo"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
    }
}