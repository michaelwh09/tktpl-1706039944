package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel() {
    private val counter = MutableLiveData<Int>()

    val counterData: LiveData<Int>
        get() = counter

    init {
        counter.value = 5
    }
}
