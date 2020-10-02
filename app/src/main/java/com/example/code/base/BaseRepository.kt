package com.example.code.base

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseRepository<VM: BaseViewModel> {



    var viewModel: VM? = null

    fun attachViewModel(viewModel: VM) {
        this.viewModel = viewModel
    }

    fun detachViewModel() {
        viewModel = null
    }

}