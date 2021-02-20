package com.haticenurokur.inviomoviesearchapp.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.Toolbar
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.haticenurokur.inviomoviesearchapp.R
import com.haticenurokur.inviomoviesearchapp.adapters.SearchResultAdapter
import com.haticenurokur.inviomoviesearchapp.models.MovieModel
import org.json.JSONException
import org.json.JSONObject


class MovieSearchFragment : Fragment() {

    private lateinit var btnSearch: Button
    private lateinit var etSearch: EditText
    private lateinit var lvSearchResult: ListView
    private lateinit var animationView: LottieAnimationView

    private val searchResults = arrayListOf<MovieModel>()
    private lateinit var adapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movie_search, container, false)

        initViews(view)
        initToolbar()
        setClickListeners()

        return view
    }

    private fun initViews(view: View) {
        btnSearch = view.findViewById(R.id.btnSearch)
        etSearch = view.findViewById(R.id.etSearch)
        lvSearchResult = view.findViewById(R.id.lvSearchResult)
        animationView = view.findViewById(R.id.animationView)
    }

    private fun initToolbar() {
        val toolbar = activity!!.findViewById<Toolbar>(R.id.toolbar)
        toolbar.navigationIcon = null
        toolbar.title = getString(R.string.app_name)
    }

    private fun setClickListeners() {
        etSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchButtonClick()
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })

        btnSearch.setOnClickListener {
            searchButtonClick()
        }

        lvSearchResult.setOnItemClickListener { _, _, position, _ ->
            etSearch.text.clear()
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.mainFrame,
                MovieDetailFragment.newInstance(searchResults[position])
            )
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("MovieSearchFragment")
                .commit()
        }
    }

    private fun noResultPopUp() {
        val alert = AlertDialog.Builder(context)

        alert.setTitle("Sorry..")
        alert.setMessage("No result for this keyword")
        alert.setCancelable(false)

        alert.setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        alert.show()
    }

    private fun searchButtonClick() {
        searchResults.clear()
        val imm = activity!!.getSystemService<InputMethodManager>()!!
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        val searchKey = etSearch.text.toString().trim()
        showAnimation(true)
        request(searchKey)
    }

    private fun showAnimation(isAnimating: Boolean) {
        if (isAnimating)
            animationView.visibility = View.VISIBLE
        else
            animationView.visibility = View.GONE
    }

    private fun request(searchKey: String) {
        val queue = Volley.newRequestQueue(context)

        val productUrl = "http://www.omdbapi.com/?s=$searchKey&apikey=95a0810b"

        val request =
            JsonObjectRequest(Request.Method.GET, productUrl, null, Response.Listener { response ->
                try {
                    val responseArray = response.getJSONArray("Search")

                    for (index in 0 until responseArray.length()) {
                        val movie = MovieModel()
                        movie.title = (responseArray[index] as JSONObject).get("Title").toString()
                        movie.year = (responseArray[index] as JSONObject).get("Year").toString()
                        movie.imdbID = (responseArray[index] as JSONObject).get("imdbID").toString()
                        movie.type = (responseArray[index] as JSONObject).get("Type").toString()
                        movie.poster = (responseArray[index] as JSONObject).get("Poster").toString()

                        searchResults.add(movie)
                    }

                    adapter = SearchResultAdapter(context!!, searchResults)
                    adapter.notifyDataSetInvalidated()
                    adapter.notifyDataSetChanged()
                    lvSearchResult.adapter = adapter

                    showAnimation(false)

                } catch (e: JSONException) {
                    noResultPopUp()
                    showAnimation(false)
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> error.printStackTrace() })

        queue.add(request)
    }
}