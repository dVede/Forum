package com.example.forum.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forum.adapter.ActiveUsersAdapter
import com.example.forum.model.Resource
import com.example.forum.api.RetrofitClient
import com.example.forum.databinding.FragmentActiveUsersBinding
import com.example.forum.handleApiError
import com.example.forum.repository.UserRepository
import com.example.forum.viewModel.ActiveUsersViewModel
import com.example.forum.viewModel.ViewModelFactory

class ActiveUsersFragment : FragmentPattern<ActiveUsersViewModel, FragmentActiveUsersBinding, UserRepository>() {

    override val viewModel: ActiveUsersViewModel by viewModels { ViewModelFactory(getRepository()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipeRefreshLayout = binding.swiperLayout

        setHasOptionsMenu(true)
        val shimmer = binding.threadShimmer
        shimmer.startShimmer()

        val recyclerView = binding.usersRecycler
        val adapter = ActiveUsersAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        viewModel.getUsersResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure -> handleApiError(it) { viewModel.getUsers() }
                is Resource.Success -> adapter.submitList(it.value.users)
            }
            swipeRefreshLayout.isRefreshing = false
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        viewModel.getUsers()
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            viewModel.getUsers()
        }
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentActiveUsersBinding =
        FragmentActiveUsersBinding.inflate(inflater, container, false)

    override fun getRepository(): UserRepository =
        UserRepository(RetrofitClient().getApi(requireContext()))

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}