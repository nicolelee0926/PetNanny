package com.nicole.petnanny.ui.home.nannylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicole.petnanny.databinding.FragmentHomeNannyListBinding

class NannyListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeNannyListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this




        return binding.root
    }
}