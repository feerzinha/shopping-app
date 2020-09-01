package com.moya.shopping.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import coil.api.load

/**
 * Used to load a image resource using url string from an XML binding.
 */
@BindingAdapter("loadImage")
fun ImageView.loadImage(imageUrl: String) {
    load(imageUrl)
}

/**
 * Used to set text or hide view if the text is null or empty
 */
@BindingAdapter("textOrHide")
fun TextView.textOrHide(description: String?) {
    if (!description.isNullOrEmpty()) text = description
    else visibility = View.GONE

}
fun SearchView.setQueryListener(listener: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (!query.isNullOrEmpty()) {
                listener(query)
            }
            //TODO: Show a message to the user if the query is empty
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }
    })
}
