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

//        observe userManager nanny認證狀態
//        UserManager.user.observe(viewLifecycleOwner, Observer {
//            Log.d("impossible", "${UserManager.user.value?.verification} ")
//            //  因為登入後認證欄位還是null 所以要在這邊再存回UserManager一次 認證狀態才會被儲存 這時再執行snapshot時就有認證狀態了
//            //  才不會因為還沒存入狀態前就snapshot了
//            if (it.verification == null) {
//                viewModel.getDemandOrderChatRoomListResult()
//                viewModel.getLiveDemandOrdersResult()
//            }
//        })

//      get live demand order snapshot
        viewModel.liveDemandOrderChatRoomList.observe(viewLifecycleOwner, Observer {
            viewModel.getLiveDemandOrder()
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




}