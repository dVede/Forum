package com.example.forum.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forum.model.Resource
import com.example.forum.repository.UserRepository
import com.example.forum.model.MessageModel
import com.example.forum.model.OkListOfMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class MessageViewModel(private val repository: UserRepository) : ViewModel() {

    lateinit var job: Job
    val messageLiveData = MutableLiveData<String>()

    private val _messagesResponse : MutableLiveData<Resource<OkListOfMessage>> = MutableLiveData()
    val messagesResponse: LiveData<Resource<OkListOfMessage>>
        get() = _messagesResponse

    private val _messageSendResponse : MutableLiveData<Resource<ResponseBody>> = MutableLiveData()
    val messageSendResponse: LiveData<Resource<ResponseBody>>
        get() = _messageSendResponse

    fun updateMessages(threadName: String): Job {
        return viewModelScope.launch {
            while(isActive) {
                getMessages(threadName)
                delay(1000)
            }
        }
    }

    private fun getMessages(threadName: String) {
        viewModelScope.launch {
            _messagesResponse.value = repository.getMessages(threadName) as Resource<OkListOfMessage>
        }
    }

    fun send(message: MessageModel) {
        viewModelScope.launch {
            _messageSendResponse.value = repository.sendMessage(message) as Resource<ResponseBody>
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}