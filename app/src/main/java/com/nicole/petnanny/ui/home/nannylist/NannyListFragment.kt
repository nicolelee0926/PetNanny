package com.nicole.petnanny.ui.home.nannylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentHomeNannyListBinding
import com.nicole.petnanny.ext.getVmFactory

class NannyListFragment: Fragment() {

    private val viewModel by viewModels<NannyListViewModel> { getVmFactory(NannyListFragmentArgs.fromBundle(requireArguments()).serviceType) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeNannyListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val nannyListAdapter = NannyListAdapter(viewModel)
        binding.rvNannyList.adapter = nannyListAdapter


//      按下後跳出dialog的服務類別
        binding.btnChoiceService.setOnClickListener {
            findNavController().navigate(NannyListFragmentDirections.actionNannyListFragmentToNannyServiceFragment())
        }

//        load出首頁所選擇的類別後的list
        viewModel.nannyList.observe(viewLifecycleOwner, Observer {
            Log.d("pppppppppppppp", "${it.toString()} ")
            nannyListAdapter.submitList(it)
        })

//        navigate到nanny detail頁
        viewModel.navigationToNannyDetail.observe(viewLifecycleOwner, Observer {
            if(null != it) {
                findNavController().navigate(NannyListFragmentDirections.actionNannyListFragmentToNannyDetailFragment(it))
                viewModel.displayNannyDetailsComplete()
            }
        })

        return binding.root
    }
}