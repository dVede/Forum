package com.example.forum.viewModel

import android.view.View
import androidx.lifecycle.*
import com.example.forum.LiveDataValidator
import com.example.forum.model.Resource
import com.example.forum.repository.AuthRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private var isValidUsername: Boolean = false
    private var isValidPassword: Boolean = false
    private var isValidRepeatPassword: Boolean = false
    private val isValid: Boolean
        get() = isValidUsername && isValidPassword && isValidRepeatPassword

    val usernameLiveData = MutableLiveData<String>()
    val usernameValidator = LiveDataValidator(usernameLiveData).apply {
        addRule("username is required") { it.isNullOrBlank() }
        addRule("username contains whitespace") { it!!.contains(" ") }
        addRule("username length must be more then 3") { it?.length ?: 0 < 3 }
        addRule("username length must be less then 17") { it?.length ?: 0 > 16 }
    }
    val passwordLiveData = MutableLiveData<String>()
    val passwordValidator = LiveDataValidator(passwordLiveData).apply {
        addRule("password is required") { it.isNullOrBlank() }
        addRule("password contains whitespace") { it!!.contains(" ")}
        addRule("password length must be more then 3") {
            it?.length ?: 0 < 3
        }
        addRule("Must Contain 1 Upper-case Character") {
            !it!!.matches(".*[A-Z].*".toRegex())
        }
        addRule("Must Contain 1 Lower-case Character") {
            !it!!.matches(".*[a-z].*".toRegex())
        }
        addRule("Must Contain 1 Special Character (@#\\\$%^&+=)") {
            !it!!.matches(".*[@#\$%^&+=].*".toRegex())
        }
        addRule("password length must be less then 17") {
            it?.length ?: 0 > 16
        }
    }
    val passwordRepeatLiveData = MutableLiveData<String>()
    val passwordRepeatValidator = LiveDataValidator(passwordRepeatLiveData).apply {
        addRule("password is required") { it.isNullOrBlank() }
        addRule("passwords not match") { !it.equals(passwordLiveData.value) }
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
            isValidRepeatPassword = passwordRepeatValidator.isValid()
            isLoginFormValidMediator.value = isValid
        }
        isLoginFormValidMediator.addSource(passwordRepeatLiveData) {
            isValidRepeatPassword = passwordRepeatValidator.isValid()
            isLoginFormValidMediator.value = isValid
        }
    }

    private val _registerResponse : MutableLiveData<Resource<ResponseBody>> = MutableLiveData()
    val registerResponse: LiveData<Resource<ResponseBody>>
        get() = _registerResponse

    fun register(username: String, pwd: String) {
        viewModelScope.launch {
            _registerResponse.value = repository.register(username, pwd) as Resource<ResponseBody>
        }
    }
}