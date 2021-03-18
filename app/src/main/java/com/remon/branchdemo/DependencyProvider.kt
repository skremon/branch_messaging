package com.remon.branchdemo

import com.remon.branchdemo.network.ApiService

object DependencyProvider {

    fun provideThreadsViewModelFactory() =
        ThreadsViewModel.Factory(provideThreadsRepository())

    private fun provideThreadsRepository(): ThreadsRepository {
        return ThreadsRepository(ApiService.AUTH_TOKEN, ApiService.create())
    }

}