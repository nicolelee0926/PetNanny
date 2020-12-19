package com.nicole.petnanny.ui.chat.demand.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.petnanny.databinding.FragmentDemandChatroomDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.chat.ChatRoomDetailAdapter

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
                viewModel.setMessage()
//            }
            binding.etMessageContent.setText("")
        }

        viewModel.setMessage.observe(viewLifecycleOwner, Observer {
            Log.d("setMessage", "$it ")
            viewModel.addMessage(it)
        })

        viewModel.messages.observe(viewLifecycleOwner, Observer {
            Log.d("getMessageList", "$it ")
            chatRoomDetailAdapter.submitList(it)
            binding.rvDemandChatContent.post { binding.rvDemandChatContent.scrollToPosition(it.size - 1) }
        })

//        snapshot
        viewModel.livemessages.observe(viewLifecycleOwner, Observer {
            Log.d("uuuuu", "$it ")
            viewModel.getLiveMessage()
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


