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

class RegisterFragment: FragmentPattern<RegisterViewModel, RegisterFragmentBinding, AuthRepository>(),
    View.OnClickListener{

    override val viewModel: RegisterViewModel by viewModels { ViewModelFactory(getRepository()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getBinding(inflater, container)
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
                is Resource.Failure -> handleApiError(it)
            }
            binding.registerButton.isEnabled = true
        }
        binding.registerButton.setOnClickListener(this)
    }

    override fun getRepository(): AuthRepository =
        AuthRepository(RetrofitClient().getApi(requireContext()))

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): RegisterFragmentBinding =
        RegisterFragmentBinding.inflate(inflater, container, false)

    override fun onClick(p0: View?) {
        binding.registerButton.isEnabled = false
        val username = binding.username.text.toString().trim()
        val password = binding.password.text.toString().trim()
        viewModel.register(username, password)
    }
}