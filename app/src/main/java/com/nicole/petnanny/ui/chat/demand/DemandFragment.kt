package com.nicole.petnanny.ui.chat.demand

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.databinding.FragmentChatDemandBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.chat.ChatFragmentDirections
import com.nicole.petnanny.ui.login.UserManager

class DemandFragment() : Fragment() {

    private val viewModel by viewModels<DemandViewModel> { getVmFactory() }

    var type: Int = 0

    constructor(int: Int) : this() {
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


        viewModel.demandOrderChatRoomList.observe(viewLifecycleOwner, Observer {
            Log.d("testDemandMessage", "$it ")
            demandAdapter.submitList(it)
        })

//        navigate到demand chat room detail頁
        viewModel.navigationToDemandChatRoomDetail.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                findNavController().navigate(ChatFragmentDirections.actionNavigationChatToDemandDetailFragment(it))
                viewModel.displayChatRoomDetailComplete()
            }
        })


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("!!!!", "!!! ");
        UserManager.user.value?.userEmail?.let {
            Log.d("!!!", "$it ")
//            viewModel.getDemandOrderChatRoomListResult()
        }
    }


}