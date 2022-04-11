package com.example.forum.ui

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.forum.model.MessageModel
import com.example.forum.model.Resource
import com.example.forum.adapter.MessageAdapter
import com.example.forum.api.RetrofitClient
import com.example.forum.databinding.ActivityMessageBinding
import com.example.forum.handleApiError
import com.example.forum.repository.UserRepository
import com.example.forum.viewModel.MessageViewModel
import com.example.forum.viewModel.ViewModelFactory


class MessageActivity : AppCompatActivity() {
    private val viewModel: MessageViewModel by viewModels { ViewModelFactory(getRepository() ) }
    private val binding: ActivityMessageBinding by viewBinding()
    private var isAtBottom: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: MessageActivityArgs by navArgs()
        supportActionBar?.title = args.threadName
        binding.lifecycleOwner = this
        binding.messageViewModel = viewModel
        setContentView(binding.root)
        val adapter = MessageAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter

        val linearManager = LinearLayoutManager(this)
        linearManager.stackFromEnd = true
        val observer: AdapterDataObserver = object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (isAtBottom) {
                    recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
                }
            }
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                isAtBottom = !recyclerView.canScrollVertically(1)
            }
        })
        adapter.registerAdapterDataObserver(observer)
        recyclerView.layoutManager = linearManager
        viewModel.messagesResponse.observe(this) {
            when (it) {
                is Resource.Failure -> {
                    handleApiError(it, binding.root)
                    finish()
                }
                is Resource.Success -> adapter.submitList(it.value.messages)

            }
        }
        viewModel.messageSendResponse.observe(this) {
            when (it) {
                is Resource.Failure ->
                    handleApiError(it, binding.root)
                is Resource.Success ->
                    viewModel.messageLiveData.value = ""
            }
            binding.sendButton.isClickable = true
        }
        binding.sendButton.setOnClickListener {
            val msg = viewModel.messageLiveData.value
            when {
                msg.isNullOrBlank() -> {
                    binding.editText.error = "Empty"
                    return@setOnClickListener
                }
                msg.trim().length > 300 -> {
                    binding.editText.error = "Message too long (300)"
                    return@setOnClickListener
                }
            }
            viewModel.send(MessageModel(args.threadName, msg!!.trim()))
            binding.sendButton.isClickable = false
        }

        viewModel.job = viewModel.updateMessages(args.threadName)
    }

    private fun getRepository(): UserRepository =
        UserRepository(RetrofitClient().getApi(this))
}