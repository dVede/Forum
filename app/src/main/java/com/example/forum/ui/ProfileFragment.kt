package com.example.forum.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.forum.*
import com.example.forum.model.Resource
import com.example.forum.api.RetrofitClient
import com.example.forum.databinding.FragmentProfileBinding
import com.example.forum.repository.UserRepository
import com.example.forum.viewModel.ProfileViewModel
import com.example.forum.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : FragmentPattern<ProfileViewModel, FragmentProfileBinding, UserRepository>(),
    View.OnClickListener{

    override val viewModel: ProfileViewModel by viewModels { ViewModelFactory(getRepository()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.logoutResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> lifecycleScope.launch { logout() }
                is Resource.Failure -> handleApiError(it)
            }
            binding.logoutButton.isEnabled = true
        }
        binding.logoutButton.setOnClickListener(this)
    }

    private suspend fun logout() {
        val userPreferences = UserPreferences(requireContext())
        userPreferences.clear()
        val intent = Intent(context, AuthActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding =
        FragmentProfileBinding.inflate(inflater, container, false)

    override fun getRepository(): UserRepository =
        UserRepository(RetrofitClient().getApi(requireContext()))

    override fun onClick(p0: View?) {
        binding.logoutButton.isEnabled = false
        viewModel.logout()
    }
}