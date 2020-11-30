package com.nicole.petnanny.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentHomeBinding
import com.nicole.petnanny.databinding.FragmentPetBinding
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections
import com.nicole.petnanny.ui.profile.pet.PetAdapter
import com.nicole.petnanny.ui.profile.pet.PetViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        val homeAdapter = HomeAdapter()
        binding.rvHomeNanny.adapter = homeAdapter

        viewModel.getFakeNannyData.observe(viewLifecycleOwner, Observer {
            homeAdapter.submitList(it)
        })

        binding.btnNannyCenter.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNannyListFragment())
        }

        binding.btnHomeVisit.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNannyListFragment())
        }

        return binding.root
    }
}