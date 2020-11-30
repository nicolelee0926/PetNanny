package com.nicole.petnanny.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentHomeBinding
import com.nicole.petnanny.databinding.FragmentPetBinding
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.btnNannyCenter.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNannyListFragment())
        }

        binding.btnHomeVisit.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNannyListFragment())
        }

        return binding.root
    }
}