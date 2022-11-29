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
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginBtn.setOnClickListener {
            val userId = binding.userInput.text.toString()
            login(userId)
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