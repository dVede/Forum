package com.example.forum.viewModel

import android.view.View
import androidx.lifecycle.*
import com.example.forum.LiveDataValidator
import com.example.forum.model.Resource
import com.example.forum.repository.AuthRepository
import com.example.forum.model.OkAuth
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private var isValidUsername: Boolean = false
    private var isValidPassword: Boolean = false
    private val isValid: Boolean
        get() = isValidUsername && isValidPassword

    val usernameLiveData = MutableLiveData<String>()
    val usernameValidator = LiveDataValidator(usernameLiveData).apply {
        addRule("username is required") { it.isNullOrBlank() }
        addRule("username length must be more then 3") { it?.length ?: 0 < 3 }
        addRule("username length must be less then 17") { it?.length ?: 0 > 16 }
    }
    val passwordLiveData = MutableLiveData<String>()
    val passwordValidator = LiveDataValidator(passwordLiveData).apply {
        addRule("password is required") { it.isNullOrBlank() }
        addRule("password length must be more then 3") { it?.length ?: 0 < 3 }
        addRule("Must Contain 1 Upper-case Character") {
            !it!!.matches(".*[A-Z].*".toRegex())
        }
        addRule("Must Contain 1 Lower-case Character") {
            !it!!.matches(".*[a-z].*".toRegex())
        }
        addRule("Must Contain 1 Special Character (@#\\\$%^&+=)") {
            !it!!.matches(".*[@#\$%^&+=].*".toRegex())
        }
        addRule("password length must be less then 17") { it?.length ?: 0 > 16 }
    }

    var isLoginFormValidMediator = MediatorLiveData<Boolean>()

    init {
        isLoginFormValidMediator.value = false
        isLoginFormValidMediator.addSource(usernameLiveData) {
            isValidUsername = usernameValidator.isValid()
            isLoginFormValidMediator.value = isValid
        }
        isLoginFormValidMediator.addSource(passwordLiveData) {
            isValidPassword = passwordValidator.isValid()
            isLoginFormValidMediator.value = isValid
        }
    }

    private val _loginResponse : MutableLiveData<Resource<OkAuth>> = MutableLiveData()
    val loginResponse: LiveData<Resource<OkAuth>>
        get() = _loginResponse

    private fun login(username: String, pwd: String) {
        viewModelScope.launch {
            _loginResponse.value = repository.login(username,pwd)
        }
    }

    fun onButtonClick(view: View) {
        val username = usernameLiveData.value
        val password = passwordLiveData.value
        login(username!!, password!!)
    }
}