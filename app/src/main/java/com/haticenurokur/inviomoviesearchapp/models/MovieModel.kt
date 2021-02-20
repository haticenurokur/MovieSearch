package com.haticenurokur.inviomoviesearchapp.models

import java.io.Serializable

/**
 * Created by haticenurokur on 2/19/21.
 */

data class MovieModel(
    var title: String? = null,
    var year: String? = null,
    var imdbID: String? = null,
    var type: String? = null,
    var poster: String? = null

) : Serializable {
    override fun toString(): String {
        return """
            
            Movie{Title='$title', Year='$year', imdbID='$imdbID', Type='$type', Poster='$poster'}
            """.trimIndent()
    }
}