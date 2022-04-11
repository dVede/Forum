package com.example.forum.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forum.model.Resource
import com.example.forum.api.RetrofitClient
import com.example.forum.adapter.ThreadsAdapter
import com.example.forum.databinding.FragmentThreadsBinding
import com.example.forum.handleApiError
import com.example.forum.repository.UserRepository
import com.example.forum.viewModel.ThreadsViewModel
import com.example.forum.viewModel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class SubThreadsFragment : FragmentPattern<ThreadsViewModel, FragmentThreadsBinding, UserRepository>() {

    override val viewModel: ThreadsViewModel by activityViewModels{ ViewModelFactory(getRepository()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipeRefreshLayout = binding.swiperLayout

        val shimmer = binding.threadShimmer
        shimmer.startShimmer()
        val list: List<String>? = when (val response = viewModel.hierarchyResponse.value) {
            is Resource.Success -> response.value.hierarchy[viewModel.thread]
            is Resource.Failure -> {
                Snackbar.make(view, "No internet connection", Snackbar.LENGTH_SHORT).show()
                emptyList()
            }
            else -> {
                Snackbar.make(view, "No internet connection", Snackbar.LENGTH_SHORT).show()
                emptyList()
            }
        }
        val adapter = ThreadsAdapter { startThread(it) }
        adapter.submitList(list)

        val recyclerView = binding.threadsRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        viewModel.hierarchyResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure -> handleApiError(it, binding.root)
                is Resource.Success -> {
                    val threads = it.value.hierarchy[viewModel.thread] ?: emptyList()
                    adapter.submitList(threads)
                }
            }
            swipeRefreshLayout.isRefreshing = false
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            viewModel.getHierarchy()
        }
    }

    private fun startThread(subThreadName: String) {
        val action = SubThreadsFragmentDirections
            .actionSubThreadsFragmentToMessageActivity(subThreadName)
        findNavController().navigate(action)
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentThreadsBinding =
        FragmentThreadsBinding.inflate(inflater, container, false)

    override fun getViewModel(): Class<ThreadsViewModel> =
        ThreadsViewModel::class.java

    override fun getRepository(): UserRepository =
        UserRepository(RetrofitClient().getApi(requireContext()))
}