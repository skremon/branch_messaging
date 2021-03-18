package com.remon.branchdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.remon.branchdemo.databinding.ActivityThreadsBinding
import com.remon.branchdemo.network.ApiService

class ThreadsActivity : AppCompatActivity() {

    lateinit var viewModel: ThreadsViewModel
    lateinit var binding: ActivityThreadsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThreadsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val threadsAdapter = ThreadsAdapter(object: MessageClickListener {
            override fun onClick(msg: com.remon.branchdemo.data.Message) {
                onThreadItemClick(msg.threadId)
            }
        })
        binding.threadsList.adapter = threadsAdapter


        viewModel = ViewModelProvider(this, DependencyProvider.provideThreadsViewModelFactory())
            .get(ThreadsViewModel::class.java)

        viewModel.allThreads.observe(this) {
            it?.let {
                // display messages
                threadsAdapter.submitData(it)
            }
        }

        viewModel.requestThreads()
    }

    fun onThreadItemClick(threadId: Int) {
        val intent = Intent(this, MessageThreadActivity::class.java).apply {
            putExtra(MessageThreadActivity.THREAD_ID, threadId)
        }
        startActivity(intent)
    }

    val TAG = "ThreadsActivity"
}

