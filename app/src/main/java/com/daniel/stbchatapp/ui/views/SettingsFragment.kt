package com.daniel.stbchatapp.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daniel.stbchatapp.R
import com.daniel.stbchatapp.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.saveChanges.setOnClickListener {
            if (binding.newUserName.text.toString().isNotEmpty()) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainer, MessageFragment.newInstance(
                            binding.newUserName.text.toString()
                        )
                    )
                    .commit()
            }
        }
        return binding.root
    }
}