package com.example.code.base


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.code.base.utils.observe
import com.example.code.base.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject


abstract class BaseFragment<VM: BaseViewModel>(private val layoutId: Int, private val viewModelClass: Class<VM>) : Fragment(), CoroutineScope{

    private val job = Job()
    override val coroutineContext = job + Dispatchers.Main

    abstract fun injectComponent()
    abstract fun clearComponent()
    abstract fun setUpUI(view: View)
    abstract fun observeViewModel()


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: VM


    override fun onAttach(context: Context) {
        injectComponent()
        super.onAttach(context)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]
        observeViewModel()
        observeToastMessage()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        clearComponent()
    }

    private fun observeToastMessage() {
        observe(viewModel.toastMessage) {
            it.getContentIfNotHandled()?.let { message ->
                context.toast(message)
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutId, container, false)
        setUpUI(view)
        return view
    }

}