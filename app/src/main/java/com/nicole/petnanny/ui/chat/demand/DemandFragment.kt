package com.nicole.petnanny.ui.chat.demand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentChatDemandBinding
import com.nicole.petnanny.ext.getVmFactory

class DemandFragment() : Fragment() {

    private val viewModel by viewModels<DemandViewModel> { getVmFactory() }

    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatDemandBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val demandAdapter = DemandAdapter(viewModel)
        binding.rvChatDemand.adapter = demandAdapter

        viewModel.chatList.observe(viewLifecycleOwner, Observer {
            demandAdapter.submitList(it)
        })

        viewModel.navigationToChatRoomDetail.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                findNavController().navigate(DemandFragmentDirections.actionDemandFragmentToDemandDetailFragment())
            }
            if (it != null) {
                viewModel.displayChatRoomDetailComplete()
            }
        })

        return binding.root
    }
}