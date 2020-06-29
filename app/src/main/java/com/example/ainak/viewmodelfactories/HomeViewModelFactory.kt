package com.example.ainak.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ainak.data.remote.RemoteRepositoryClass
import com.example.ainak.viewmodels.HomeViewModel

/**
 * ViewModel Factory For HomeViewModel
 */
class HomeViewModelFactory(private val remoteRepositoryClass: RemoteRepositoryClass) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                remoteRepositoryClass
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}