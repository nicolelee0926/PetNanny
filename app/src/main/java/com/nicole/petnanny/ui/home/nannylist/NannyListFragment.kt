package com.nicole.petnanny.ui.home.nannylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentHomeNannyListBinding

class NannyListFragment: Fragment() {

    private lateinit var viewModel: NannyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeNannyListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(NannyListViewModel::class.java)
        binding.viewModel = viewModel

        val nannyListAdapter = NannyListAdapter(viewModel)
        binding.rvNannyList.adapter = nannyListAdapter

        viewModel.getFakeNannyListData.observe(viewLifecycleOwner, Observer {
            nannyListAdapter.submitList(it)
        })

        binding.btnChoiceService.setOnClickListener {
            findNavController().navigate(NannyListFragmentDirections.actionNannyListFragmentToNannyServiceFragment())
        }

        viewModel.navigationToNannyDetail.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                findNavController().navigate(NannyListFragmentDirections.actionNannyListFragmentToNannyDetailFragment())
            }

            if(it != null) {
                viewModel.displayNannyDetailsComplete()
            }
        })

        return binding.root
    }
}