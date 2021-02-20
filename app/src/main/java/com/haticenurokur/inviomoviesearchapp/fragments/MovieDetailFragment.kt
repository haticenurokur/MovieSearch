package com.haticenurokur.inviomoviesearchapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.haticenurokur.inviomoviesearchapp.R
import com.haticenurokur.inviomoviesearchapp.models.MovieModel
import com.squareup.picasso.Picasso


class MovieDetailFragment : Fragment() {

    private lateinit var ivPoster: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvYear: TextView
    private lateinit var tvDetail: TextView
    private lateinit var toolbar: Toolbar

    private val movie = MovieModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)

        initViews(view)
        initToolbar()

        if (arguments != null) {
            val bundleMovie = arguments!!.getSerializable(MOVIE) as MovieModel
            setMovie(bundleMovie)
            setViewFromMovie()
        }

        return view
    }

    private fun initViews(view: View) {
        ivPoster = view.findViewById(R.id.ivPoster)
        tvName = view.findViewById(R.id.tvName)
        tvYear = view.findViewById(R.id.tvYear)
        tvDetail = view.findViewById(R.id.tvDetail)
    }

    private fun initToolbar() {
        toolbar = activity!!.findViewById(R.id.toolbar)
        toolbar.navigationIcon = activity!!.getDrawable(R.drawable.ic_arrow_back_24)
    }

    private fun setMovie(bundleMovie: MovieModel) {
        movie.poster = bundleMovie.poster
        movie.type = bundleMovie.type
        movie.title = bundleMovie.title
        movie.year = bundleMovie.year
    }

    private fun setViewFromMovie() {
        toolbar.title = movie.title

        Picasso.get().load(movie.poster).into(ivPoster)

        tvName.text = movie.title
        tvYear.text = movie.year
        tvDetail.text = movie.type
    }

    companion object {
        const val MOVIE = "MOVIE"
        fun newInstance(movie: MovieModel): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val args = Bundle()
            args.putSerializable(MOVIE, movie)
            fragment.arguments = args
            return fragment
        }
    }
}