package com.nicole.petnanny.ui.chat.demand.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.CurrentFragmentType
import com.nicole.petnanny.databinding.FragmentDemandChatroomDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.chat.ChatRoomDetailAdapter
import com.nicole.petnanny.ui.main.MainViewModel

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
            binding.rvDemandChatContent.smoothScrollToPosition(chatRoomDetailAdapter.itemCount)
        })

//        snapshot
        viewModel.livemessages.observe(viewLifecycleOwner, Observer {
            Log.d("uuuuu", "$it ")
            viewModel.getLiveMessage()
        })

//        snapshot demandOrder
        viewModel.livaDemandOrderChatRoom.observe(viewLifecycleOwner, Observer {
            if (it.userCheckedStatus == true) {
                binding.tvDemandOrderStatus.setText("此筆訂單完成")
                binding.ivParentChecked.visibility = View.VISIBLE
                binding.ivParentUndone.visibility = View.GONE
            } else if (it.nannyCompletedStatus == true) {
                binding.tvDemandOrderStatus.setText("等待您的確認")
            } else if (it.userCheckoutStatus == true) {
                binding.tvDemandOrderStatus.setText("等待服務開始")
            } else if (it.nannyAcceptStatus == true) {
                binding.tvDemandOrderStatus.setText("等待您的付款")
            }
        })

//          改chat room 型態
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val chatRoomName = CurrentFragmentType.CHAT_ROOM_DEMAND_NANNY_NAME
        chatRoomName.value = viewModel.demandDetail.value?.nannyServiceDetail?.nannyName.toString()
        mainViewModel.currentFragmentType.value = chatRoomName


        return binding.root
    }

}


