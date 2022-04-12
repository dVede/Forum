package com.example.forum

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.forum.model.Resource
import com.example.forum.ui.LoginFragment
import com.google.android.material.snackbar.Snackbar

const val DEFAULT_PORT = 8080
const val DEFAULT_HOST = "26.11.70.132"

fun Fragment.handleApiError(failure: Resource.Failure) {
    val msg = when {
        failure.isNetworkError ->
            Snackbar.make(requireView(), "Network error", Snackbar.LENGTH_SHORT)
        failure.errorCode == 401 ->
            if (this is LoginFragment) {
                Snackbar.make(requireView(), "Wrong username or password", Snackbar.LENGTH_SHORT)
            }
            else {
                Snackbar.make(requireView(), "Unauthorized response", Snackbar.LENGTH_SHORT)
            }
        else ->
            Snackbar.make(requireView(), failure.errorBody?.string().toString(), Snackbar.LENGTH_SHORT)
    }
    msg.show()
}

fun AppCompatActivity.handleApiError(failure: Resource.Failure) {
    val msg = when {
        failure.isNetworkError ->
            Snackbar.make(findViewById(android.R.id.content),
                "Network error", Snackbar.LENGTH_SHORT)
        failure.errorCode == 401 ->
            Snackbar.make(findViewById(android.R.id.content),
                "Unauthorized response", Snackbar.LENGTH_SHORT)
        else ->
            Snackbar.make(findViewById(android.R.id.content),
                failure.errorBody?.string().toString(), Snackbar.LENGTH_SHORT)
    }
    msg.show()
}


