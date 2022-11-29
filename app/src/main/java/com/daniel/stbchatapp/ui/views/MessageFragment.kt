package com.daniel.stbchatapp.ui.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.daniel.stbchatapp.databinding.FragmentMessageListBinding
import com.daniel.stbchatapp.ui.Adapter.MessageRecyclerViewAdapter
import com.daniel.stbchatapp.ui.UiState
import com.daniel.stbchatapp.ui.viewmodel.ChatViewModel

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageListBinding? = null

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ChatViewModel::class.java]

    }
    private val binding get() = _binding!!
    private var userName: String? = null
    private val manager = LinearLayoutManager(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(USER)
        }
    }
    lateinit var recyclerViewAdapter: MessageRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessageListBinding.inflate(inflater, container, false)
        recyclerViewAdapter = MessageRecyclerViewAdapter(userName!!, mutableListOf())
        with(binding.list) {
            layoutManager = manager
            adapter = recyclerViewAdapter
        }
        viewModel.auth(::showToast)

        lastMessage()
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    Log.d(TAG, "onCreateView: LOADING")
                }
                is UiState.Error -> {
                    showToast(uiState.throwable.message.toString())

                }
                is UiState.Success -> {
                    recyclerViewAdapter.updateList(uiState.chatList)
                    lastMessage()
                }
            }
        }
        binding.sendButton.setOnClickListener {
            val text: String = binding.textInput.text.toString()
            viewModel.writeMessage(text,userName)
            binding.textInput.text.clear()
        }
        return binding.root
    }

    private fun lastMessage(){
        if (recyclerViewAdapter.itemCount > 0) {
            manager.scrollToPosition(recyclerViewAdapter.itemCount - 1)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG)
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val USER = "userId"
        private const val TAG = "MessageFragment"
        @JvmStatic
        fun newInstance(user: String) =
            MessageFragment().apply {
                arguments = Bundle().apply {
                    putString(USER, user)
                }
            }
    }
}