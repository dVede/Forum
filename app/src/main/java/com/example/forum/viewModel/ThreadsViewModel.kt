package com.example.forum.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forum.model.Resource
import com.example.forum.repository.UserRepository
import com.example.forum.model.OkHierarchy
import kotlinx.coroutines.launch

class ThreadsViewModel(private val repository: UserRepository): ViewModel() {
    lateinit var thread: String
    private val _hierarchyResponse : MutableLiveData<Resource<OkHierarchy>> = MutableLiveData()
    val hierarchyResponse: LiveData<Resource<OkHierarchy>>
        get() = _hierarchyResponse

    fun getHierarchy() {
        viewModelScope.launch {
            _hierarchyResponse.value = repository.getHierarchy() as Resource<OkHierarchy>
        }
    }
}