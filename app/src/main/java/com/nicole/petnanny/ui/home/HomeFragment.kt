package com.nicole.petnanny.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentHomeBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.home.nannylist.NannyListFragmentDirections

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val homeAdapter = HomeAdapter(viewModel)
        binding.rvHomeNanny.adapter = homeAdapter

        //  拿到首頁 nanny list隨機保姆服務
        viewModel.nannyList.observe(viewLifecycleOwner, Observer {
            Log.d("nannyList", "$it ")
            homeAdapter.submitList(it)
        })

        binding.btnNannyCenter.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToNannyListFragment("到府照顧")
            )
        }

        binding.btnHomeVisit.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToNannyListFragment("到府照顧")
            )
        }

        binding.btnHomeBeauty.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToNannyListFragment("到府美容")
            )
        }

        binding.btnHomeShower.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToNannyListFragment("到府洗澡")
            )
        }

        binding.btnHomeTrain.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToNannyListFragment("到府訓練")
            )
        }

        binding.btnStay.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToNannyListFragment("住宿託管")
            )
        }

        binding.btnWalk.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToNannyListFragment("遛狗散步")
            )
        }

        viewModel.navigationToNannyDetail.observe(viewLifecycleOwner, Observer {
            if(null != it) {
                findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNannyDetailFragment(it))
                viewModel.displayNannyDetailsComplete()
            }
        })

        return binding.root
    }
}