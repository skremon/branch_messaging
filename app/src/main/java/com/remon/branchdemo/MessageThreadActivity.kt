package com.remon.branchdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.remon.branchdemo.data.Message
import com.remon.branchdemo.databinding.ActivityMessagesBinding

class MessageThreadActivity: AppCompatActivity() {

    lateinit var viewModel: ThreadsViewModel
    lateinit var binding: ActivityMessagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val threadsAdapter = ThreadsAdapter(object: MessageClickListener {
            override fun onClick(msg: Message) {

            }
        })
        binding.messageList.adapter = threadsAdapter

        viewModel = ViewModelProvider(this, DependencyProvider.provideThreadsViewModelFactory())
            .get(ThreadsViewModel::class.java)

        val threadId = intent.getIntExtra(Companion.THREAD_ID, 0)

        viewModel.messagesForThread.observe(this) {
            it?.let {
                // display messages
                threadsAdapter.submitData(it)
            }
        }

        viewModel.requestThread(threadId)
    }

    companion object {
        val THREAD_ID = "THREAD_ID"
    }
}