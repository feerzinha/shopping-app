package com.moya.shopping.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.moya.shopping.R

fun Fragment.handleError(isNoNetwork: Boolean) {
    val errorMessage =
        if (isNoNetwork) R.string.no_connection_error_message else R.string.unexpected_error_message
    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
}
