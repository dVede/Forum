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

    protected var _binding: B? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getBinding(inflater, container)
        return binding.root
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): B
    abstract fun getRepository(): R

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}