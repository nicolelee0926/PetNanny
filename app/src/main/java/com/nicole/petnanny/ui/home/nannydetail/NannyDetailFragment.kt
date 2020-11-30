package com.nicole.petnanny.ui.home.nannydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicole.petnanny.databinding.FragmentHomeNannyDetailBinding

class NannyDetailFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeNannyDetailBinding.inflate(inflater, container, false)




        return binding.root
    }
}