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

class ProfileFragment : FragmentPattern<ProfileViewModel, FragmentProfileBinding, UserRepository>() {

    override val viewModel: ProfileViewModel by viewModels { ViewModelFactory(getRepository()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getBinding(inflater, container)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.profileViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.logoutResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success ->
                    lifecycleScope.launch {
                        val userPreferences = UserPreferences(requireContext())
                        userPreferences.clear()
                        val intent = Intent(context, AuthActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                is Resource.Failure ->
                    handleApiError(it, binding.root)
            }
        }
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding =
        FragmentProfileBinding.inflate(inflater, container, false)

    override fun getViewModel(): Class<ProfileViewModel> =
        ProfileViewModel::class.java

    override fun getRepository(): UserRepository =
        UserRepository(RetrofitClient().getApi(requireContext()))
}