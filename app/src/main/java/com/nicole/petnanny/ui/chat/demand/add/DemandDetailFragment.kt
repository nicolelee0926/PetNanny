package com.nicole.petnanny.ui.chat.demand.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nicole.petnanny.data.Message
import com.nicole.petnanny.data.userTextList
import com.nicole.petnanny.databinding.FragmentDemandChatroomDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.chat.ChatRoomDetailAdapter
import com.nicole.petnanny.ui.chat.demand.DemandAdapter

class DemandDetailFragment: Fragment() {

    private val viewModel by viewModels<DemandDetailViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDemandChatroomDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val chatRoomDetailAdapter = ChatRoomDetailAdapter()
        binding.rvDemandChatContent.adapter = chatRoomDetailAdapter

        //一進來監聽之前聊天內容
        val userTextList = userTextList()
        val userText = mutableListOf<Message>()

        return binding.root
    }
}

