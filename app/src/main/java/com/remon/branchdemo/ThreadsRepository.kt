package com.remon.branchdemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.remon.branchdemo.data.Message
import com.remon.branchdemo.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThreadsRepository(val authToken: String, val apiService: ApiService) {

    private val TAG = "ThreadsRepository"

    private val messageThreads = MutableLiveData<List<Message>>()

    fun requestMessages() : LiveData<List<Message>> {
        apiService.getAllMessages(authToken).enqueue(object: Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.isSuccessful) {
                    messageThreads.value = response.body()
                } else {
                    // todo
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                // todo
                Log.e(TAG, "error")
            }

        })

        return messageThreads
    }
}