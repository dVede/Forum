package com.example.forum.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.forum.model.Resource
import com.example.forum.api.RetrofitClient
import com.example.forum.databinding.RegisterFragmentBinding
import com.example.forum.handleApiError
import com.example.forum.repository.AuthRepository
import com.example.forum.viewModel.RegisterViewModel
import com.example.forum.viewModel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class RegisterFragment: FragmentPattern<RegisterViewModel, RegisterFragmentBinding, AuthRepository>() {

    override val viewModel: RegisterViewModel by viewModels { ViewModelFactory(getRepository()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getBinding(inflater, container)
        binding.lifecycleOwner = this
        binding.registerViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.registerResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success ->
                    Snackbar.make(view, "Account created", Snackbar.LENGTH_SHORT).show()
                is Resource.Failure ->
                    handleApiError(it, binding.root)
            }
            binding.registerButton.isEnabled = true
        }
    }

    override fun getViewModel(): Class<RegisterViewModel> =
        RegisterViewModel::class.java

    override fun getRepository(): AuthRepository =
        AuthRepository(RetrofitClient().getApi(requireContext()))

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): RegisterFragmentBinding =
        RegisterFragmentBinding.inflate(inflater, container, false)
}