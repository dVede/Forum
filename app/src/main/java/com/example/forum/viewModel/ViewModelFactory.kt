package com.example.forum.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forum.repository.AuthRepository
import com.example.forum.repository.BaseRepository
import com.example.forum.repository.UserRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: BaseRepository):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                RegisterViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(repository as UserRepository) as T
            modelClass.isAssignableFrom(ActiveUsersViewModel::class.java) ->
                ActiveUsersViewModel(repository as UserRepository) as T
            modelClass.isAssignableFrom(ThreadsViewModel::class.java) ->
                ThreadsViewModel(repository as UserRepository) as T
            modelClass.isAssignableFrom(MessageViewModel::class.java) ->
                MessageViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("No such ViewModelClass")
        }
    }
}