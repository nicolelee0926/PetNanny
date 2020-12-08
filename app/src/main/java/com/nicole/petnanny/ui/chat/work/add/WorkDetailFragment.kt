package com.nicole.petnanny.ui.chat.work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicole.petnanny.databinding.FragmentWorkChatroomDetailBinding

class WorkDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkChatroomDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }
}