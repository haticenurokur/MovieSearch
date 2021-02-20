package com.haticenurokur.inviomoviesearchapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.haticenurokur.inviomoviesearchapp.R
import com.haticenurokur.inviomoviesearchapp.models.MovieModel
import com.squareup.picasso.Picasso

/**
 * Created by haticenurokur on 2/19/21.
 */

class SearchResultAdapter(
    context: Context,
    private val movieList: List<MovieModel>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private lateinit var ivPoster: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvYear: TextView
    private lateinit var tvDetail: TextView

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.search_result_item, parent, false)

        ivPoster = view.findViewById(R.id.ivPoster)

        tvName = view.findViewById(R.id.tvName)
        tvYear = view.findViewById(R.id.tvYear)
        tvDetail = view.findViewById(R.id.tvDetail)

        val movie = movieList[position]

        Picasso.get().load(movie.poster).into(ivPoster)

        tvName.text = movie.title
        tvYear.text = movie.year
        tvDetail.text = movie.type

        return view
    }

    override fun getItem(position: Int): MovieModel {
        return movieList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return movieList.size
    }
}