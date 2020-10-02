package com.example.code.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.code.base.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel: ViewModel(),CoroutineScope {

    private val job = Job()
    override val coroutineContext = job + Dispatchers.Main

    val toastMessage = MutableLiveData<Event<String>>()

    open fun onStart() {

    }

    open fun onStop() {

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
