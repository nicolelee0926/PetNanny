package com.nicole.petnanny.ui.chat.work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentChatWorkBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.chat.ChatFragmentDirections

class WorkFragment(): Fragment() {

    private val viewModel by viewModels<WorkViewModel> { getVmFactory() }

    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatWorkBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val workAdapter = WorkAdapter(viewModel)
        binding.rvChatWork.adapter = workAdapter

        viewModel.chatList.observe(viewLifecycleOwner, Observer {
            workAdapter.submitList(it)
        })

        viewModel.navigationToChatRoomDetail.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                findNavController().navigate(ChatFragmentDirections.actionNavigationChatToWorkDetailFragment())
            }
            if (it != null) {
                viewModel.displayChatRoomDetailComplete()
            }
        })
        return binding.root
    }
}