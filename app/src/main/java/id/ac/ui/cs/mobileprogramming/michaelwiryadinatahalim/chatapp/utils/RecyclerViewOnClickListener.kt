package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.utils

import android.view.View

interface RecyclerViewOnClickListener<T> {
    fun onItemClicked(view: View, data: T)
}
