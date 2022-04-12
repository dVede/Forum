package com.example.forum.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forum.model.Resource
import com.example.forum.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _logoutResponse : MutableLiveData<Resource<ResponseBody>> = MutableLiveData()
    val logoutResponse: LiveData<Resource<ResponseBody>>
        get() = _logoutResponse

    fun logout() {
        viewModelScope.launch {
            _logoutResponse.value = repository.logout() as Resource<ResponseBody>
        }
    }
}