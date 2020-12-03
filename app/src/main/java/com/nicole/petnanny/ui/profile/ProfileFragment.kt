package com.nicole.petnanny.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.nicole.petnanny.databinding.FragmentProfileBinding
import com.nicole.petnanny.ext.getVmFactory

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val pager  = binding.pagerProfile
        val tab_layout = binding.tabLayoutProfile
        pager.adapter = ProfilePageAdapter(this)

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            when(position){
                0-> tab.text = "寵物"
                1-> tab.text = "服務"
                2-> tab.text = "評價"
            }
            pager.setCurrentItem(tab.position, true)
        }.attach()


        binding.btnEditUser.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToAddUserFragment())
        }

        binding.btnNannyCenter.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToNannyCenterFragment())
        }

        return binding.root
    }
}