package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import kotlinx.android.synthetic.main.chat_fragment.view.*

@AndroidEntryPoint
class ChatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
