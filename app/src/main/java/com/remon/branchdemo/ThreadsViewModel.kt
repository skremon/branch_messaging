package com.remon.branchdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.remon.branchdemo.data.Message

class ThreadsViewModel(val repository: ThreadsRepository): ViewModel() {

    class Factory(val repository: ThreadsRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ThreadsViewModel(repository) as T
        }
    }

    private var threadId: Int? = null
    private val threadMap = HashMap<Int, MutableList<Message>>()

    private val _request = MutableLiveData<Boolean>()

    private val _allMessages = Transformations.switchMap(_request) {
        if (it != null) {
            repository.requestMessages()
        } else null
    }

    val allThreads = Transformations.map(_allMessages) {
        it?.let {
            threadMap.clear()
            filterLatestThreads(it)
        }
    }

    private fun filterLatestThreads(allMessages: List<Message>) : List<Message>{
        for (msg in allMessages) {
            val threadList = threadMap.getOrPut(msg.threadId) {
                mutableListOf()
            }
            threadList.add(msg)
        }

        val latestThreads = mutableListOf<Message>()

        for (threads in threadMap.values) {
            threads.sortWith { o1, o2 ->
                    o2.timestamp.compareTo(o1.timestamp)
            }
        }

        for (threads in threadMap.values) {
            latestThreads.add(threads[0])
        }

        return latestThreads
    }

    val messagesForThread = Transformations.map(allThreads) {
        if (threadId != null) {
            getMessagesFor(threadId!!)
        } else null
    }

    fun requestThreads() {
        _request.value = true
    }

    private fun getMessagesFor(threadId: Int) : List<Message>? {
        return if (threadMap.size > 0 && threadMap.containsKey(threadId)) {
            threadMap[threadId]
        } else null
    }

    fun requestThread(id: Int) {
        if (_request.value != true) {
            _request.value = true
        }
        threadId = id
    }
}