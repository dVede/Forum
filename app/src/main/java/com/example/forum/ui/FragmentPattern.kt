package com.example.forum.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.forum.R
import com.example.forum.repository.BaseRepository

abstract class FragmentPattern<VM: ViewModel, B: ViewBinding, R: BaseRepository>: Fragment() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getBinding(inflater, container)
        return binding.root
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): B
    abstract fun getViewModel(): Class<VM>
    abstract fun getRepository(): R

    override fun onDestroyView() {
        super.onDestroyView()
    }
}