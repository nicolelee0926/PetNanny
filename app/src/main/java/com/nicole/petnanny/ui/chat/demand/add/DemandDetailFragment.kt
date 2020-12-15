package com.nicole.petnanny.ui.chat.demand.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.petnanny.data.Message
import com.nicole.petnanny.data.User
import com.nicole.petnanny.databinding.FragmentDemandChatroomDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.chat.ChatRoomDetailAdapter
import com.nicole.petnanny.ui.order.nannyorder.detail.MyClientDetailFragmentArgs
import com.nicole.petnanny.ui.order.nannyorder.detail.MyClientDetailViewModel

class DemandDetailFragment: Fragment() {

    private val viewModel by viewModels<DemandDetailViewModel> { getVmFactory(DemandDetailFragmentArgs.fromBundle(requireArguments()).order) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDemandChatroomDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val chatRoomDetailAdapter = ChatRoomDetailAdapter()
        binding.rvDemandChatContent.adapter = chatRoomDetailAdapter

        binding.btnSendMessage.setOnClickListener {
//            if (isEmpty()) {
//                Toast.makeText(requireContext(),"請輸入訊息", Toast.LENGTH_SHORT).show()
//            } else {
//                viewModel.setMessage()
//            }
        }

        viewModel.setMessage.observe(viewLifecycleOwner, Observer {
//            viewModel.addMessage()
        })

        viewModel.allLiveMessage.observe(viewLifecycleOwner, Observer {
            Log.d("getMessageList", "$it ")
//            chatRoomDetailAdapter.submitList()
        })



        return binding.root
    }

//    fun isEmpty(): Boolean {
//        return when (viewModel.enterMessage.value) {
//            null -> true
//            else -> false
//        }
//    }


}


