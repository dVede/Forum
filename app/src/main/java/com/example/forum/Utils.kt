package com.example.forum

import android.annotation.SuppressLint
import android.view.View
import com.example.forum.model.Resource
import com.google.android.material.snackbar.Snackbar

const val DEFAULT_PORT = 8080
const val DEFAULT_HOST = "26.11.70.132"

@SuppressLint("ShowToast")
fun handleApiError(
    failure: Resource.Failure,
    view: View
) {
    val msg = when {
        failure.isNetworkError ->
            Snackbar.make(view, "Network error", Snackbar.LENGTH_SHORT)
        failure.errorCode == 401 ->
            Snackbar.make(view, "Unauthorized response", Snackbar.LENGTH_SHORT)
        else ->
            Snackbar.make(view, failure.errorBody?.string().toString(), Snackbar.LENGTH_SHORT)
    }
    msg.show()
}


