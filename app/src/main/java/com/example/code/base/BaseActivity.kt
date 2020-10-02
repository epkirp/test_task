package com.example.code.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.code.base.utils.observe
import com.example.code.base.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

abstract class BaseActivity<VM: BaseViewModel>(private val layoutId: Int, private val viewModelClass: Class<VM>) : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    override val coroutineContext = job + Dispatchers.Main
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: VM

    abstract fun injectComponent()
    abstract fun clearComponent()
    abstract fun setUpUI()
    abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        injectComponent()
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]
        setContentView(layoutId)
        setUpUI()
        observeViewModel()
        observeToastMessage()
    }


    override fun onDestroy() {
        super.onDestroy()
        clearComponent()
        job.cancel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    private fun observeToastMessage() {
        observe(viewModel.toastMessage) {
            it.getContentIfNotHandled()?.let { message ->
                toast(message)
            }
        }
    }

}



