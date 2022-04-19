package com.example.forum

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.forum.model.Resource
import com.example.forum.ui.LoginFragment
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

fun Fragment.handleApiError(failure: Resource.Failure, retry: (() -> Unit)? = null) {
    when {
        failure.isNetworkError -> requireView().snackbar("Network error", retry)
        failure.errorCode == 401 ->
            if (this is LoginFragment) requireView().snackbar("Wrong username or password")

            else requireView().snackbar("Unauthorized response")

        else -> requireView().snackbar(failure.errorBody?.string().toString())
    }
}

fun AppCompatActivity.handleApiError(failure: Resource.Failure, retry: (() -> Unit)? = null) {
    val view: View = findViewById(android.R.id.content)
    when {
        failure.isNetworkError -> view.snackbar("Network error", retry)
        failure.errorCode == 401 -> view.snackbar("Unauthorized response")
        else -> view.snackbar(failure.errorBody?.string().toString())
    }
}

fun View.snackbar(msg: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, msg, Snackbar.LENGTH_INDEFINITE).setDuration(10000)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}


