package com.example.forum.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forum.*
import com.example.forum.model.Resource
import com.example.forum.adapter.ThreadsAdapter
import com.example.forum.api.RetrofitClient
import com.example.forum.databinding.FragmentThreadsBinding
import com.example.forum.repository.UserRepository
import com.example.forum.viewModel.ThreadsViewModel
import com.example.forum.viewModel.ViewModelFactory

class ThreadsFragment : FragmentPattern<ThreadsViewModel, FragmentThreadsBinding, UserRepository>() {

    override val viewModel: ThreadsViewModel by activityViewModels{ ViewModelFactory(getRepository()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipeRefreshLayout = binding.swiperLayout

        val shimmer = binding.threadShimmer
        shimmer.startShimmer()

        val adapterThreads = ThreadsAdapter {
            viewModel.thread = it
            startSubThreads()
        }

        val recyclerView = binding.threadsRecycler
        recyclerView.adapter = adapterThreads
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.hierarchyResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure ->
                    handleApiError(it, binding.root)
                is Resource.Success ->
                    adapterThreads.submitList(it.value.hierarchy.keys.toList())
            }
            swipeRefreshLayout.isRefreshing = false
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
        viewModel.getHierarchy()
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            viewModel.getHierarchy()
        }
    }

    private fun startSubThreads() {
        findNavController().navigate(R.id.subThreadsFragment)
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