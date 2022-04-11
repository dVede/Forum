package com.example.forum

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) =
    if (errorMessage.isNullOrEmpty()) view.error = null
    else view.error = errorMessage