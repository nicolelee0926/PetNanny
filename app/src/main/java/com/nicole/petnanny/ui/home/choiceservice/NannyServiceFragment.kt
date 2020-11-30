package com.nicole.petnanny.ui.home.choiceservice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nicole.petnanny.databinding.FragmentHomeNannyServiceBinding

class NannyServiceFragment: DialogFragment()  {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeNannyServiceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this



        return binding.root
    }
}