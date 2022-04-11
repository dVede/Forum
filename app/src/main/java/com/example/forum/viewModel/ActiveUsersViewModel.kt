package com.example.forum.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forum.model.Resource
import com.example.forum.repository.UserRepository
import com.example.forum.model.OKActivityUsers
import kotlinx.coroutines.launch

class ActiveUsersViewModel(private val repository: UserRepository) : ViewModel() {
    private val _getUsersResponse : MutableLiveData<Resource<OKActivityUsers>> = MutableLiveData()
    val getUsersResponse: LiveData<Resource<OKActivityUsers>>
        get() = _getUsersResponse

    fun getUsers() {
        viewModelScope.launch {
            _getUsersResponse.value = repository.getUsers() as Resource<OKActivityUsers>
        }
    }
}