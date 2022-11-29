package com.daniel.stbchatapp.ui.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daniel.stbchatapp.R
import com.daniel.stbchatapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginBtn.setOnClickListener {
            val userId = binding.userInput.text.toString()
            login(userId)
            Log.d("TAG", "onCreateView: $userId")
        }
        return binding.root
    }

    private fun login(user: String) {
        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", user)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, MessageFragment.newInstance(user))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}