package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.chat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.R
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.entity.UserAndRoomChatNullable
import kotlinx.android.synthetic.main.chat_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private val args: ChatFragmentArgs by navArgs()

    @Inject
    lateinit var chatMessageViewModelAssistedFactory: ChatMessageViewModel.AssistedChatMessageViewModelFactory

    @Inject
    lateinit var roomInfoViewModelAssistedFactory: RoomInfoViewModel.AssistedRoomInfoViewModelFactory

    @Inject
    lateinit var sendMessageViewModelAssistedFactory: SendMessageViewModel.AssistedSendMessageViewModelFactory

    private lateinit var currentPhotoUri: Uri

    private lateinit var roomChat: UserAndRoomChatNullable

    private val sendMessageViewModel: SendMessageViewModel by navGraphViewModels(R.id.ChatFragment) {
        SendMessageViewModel.provideFactory(
            sendMessageViewModelAssistedFactory, args.roomUid
        )
    }

    private val chatMessageViewModel: ChatMessageViewModel by navGraphViewModels(R.id.ChatFragment) {
        ChatMessageViewModel.provideFactory(
            chatMessageViewModelAssistedFactory, args.roomUid
        )
    }

    private val roomInfoViewModel: RoomInfoViewModel by navGraphViewModels(R.id.ChatFragment) {
        RoomInfoViewModel.provideFactory(
            roomInfoViewModelAssistedFactory, args.roomUid
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val messageAdapter = MessageAdapter()
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        rv_chat_message.layoutManager = linearLayout
        rv_chat_message.adapter = messageAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            chatMessageViewModel.messages.collectLatest {
                messageAdapter.submitData(it)
                if (messageAdapter.itemCount > 0) {
                    rv_chat_message.smoothScrollToPosition(0)
                }
            }
        }

        chat_fragment.viewTreeObserver.addOnGlobalLayoutListener {
            if (chat_fragment != null) {
                val heightDiff = chat_fragment.rootView.height - chat_fragment.height
                if (heightDiff > 100 && messageAdapter.itemCount > 0) {
                    rv_chat_message.smoothScrollToPosition(0)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            messageAdapter.loadStateFlow.collectLatest {
                message_progress_bar.isVisible = it.refresh is LoadState.Loading
                if (it.refresh is LoadState.Error) {
                    Snackbar.make(
                        chat_fragment, (it.refresh as LoadState.Error).error.message ?: "Unknown error",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        roomInfoViewModel.roomInfo.observe(viewLifecycleOwner, {
            if (it != null) {
                toolbar_chat.title = it.user?.displayName ?: it.roomChat?.userEmailTemp?: it.roomChat?.userUid
                roomChat = it
            }
        })

        button_send_message.setOnClickListener {
            add_message_field.text?.let { text ->
                sendMessageViewModel.sendMessage(
                    text.trim().toString(),
                    (roomChat.user?.uid ?: roomChat.roomChat?.userUid)!!
                )
                add_message_field.text = null
            }
        }

        button_camera.setOnClickListener {
            dispatchTakePictureIntent()
        }

        sendMessageViewModel.error.observe(viewLifecycleOwner, {
            if (it) {
                Snackbar.make(
                    chat_fragment, "Failed to upload photo",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }
    
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Snackbar.make(
                        chat_fragment, "Failed to get photo",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.fileprovider",
                        it
                    )
                    currentPhotoUri = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePictureResult.launch(takePictureIntent)
                }
            }
        }
    }

    private val takePictureResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->

        if (result.resultCode == Activity.RESULT_OK && result.resultCode != Activity.RESULT_CANCELED) {
            activity?.let {
                val data = it.contentResolver.openInputStream(currentPhotoUri)
                data?.let { inputStream ->
                    sendMessageViewModel.sendPicture(currentPhotoUri,
                        (roomChat.user?.uid ?: roomChat.roomChat?.userUid)!!, inputStream
                    )
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "Photo_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }
}
