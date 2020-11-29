package com.nicole.petnanny.ui.profile

import android.app.Service
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.nicole.petnanny.databinding.FragmentProfileBinding
import com.nicole.petnanny.ui.profile.service.ServiceViewModel

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)


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

        return binding.root
    }
}