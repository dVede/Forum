package com.example.forum.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.forum.model.Resource
import com.example.forum.api.RetrofitClient
import com.example.forum.UserPreferences
import com.example.forum.databinding.LoginFragmentBinding
import com.example.forum.handleApiError
import com.example.forum.repository.AuthRepository
import com.example.forum.viewModel.LoginViewModel
import com.example.forum.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment: FragmentPattern<LoginViewModel, LoginFragmentBinding, AuthRepository>() {

    override val viewModel: LoginViewModel by viewModels { ViewModelFactory(getRepository() ) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getBinding(inflater, container)
        binding.lifecycleOwner = this
        binding.loginViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        UserPreferences(requireContext()).saveAuthToken(it.value.jwt)
                        val intent = Intent(context, MainMenuActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
                is Resource.Failure -> handleApiError(it, binding.root)
            }
        }
    }

    override fun getViewModel(): Class<LoginViewModel> =
        LoginViewModel::class.java

    override fun getRepository(): AuthRepository =
        AuthRepository(RetrofitClient().getApi(requireContext()))

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): LoginFragmentBinding =
        LoginFragmentBinding.inflate(inflater, container, false)
}