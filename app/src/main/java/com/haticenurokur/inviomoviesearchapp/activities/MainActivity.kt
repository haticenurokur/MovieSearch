package com.haticenurokur.inviomoviesearchapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.haticenurokur.inviomoviesearchapp.R
import com.haticenurokur.inviomoviesearchapp.fragments.MovieSearchFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(
            R.id.mainFrame,
            MovieSearchFragment()
        ).disallowAddToBackStack().commit()

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }
    }
}