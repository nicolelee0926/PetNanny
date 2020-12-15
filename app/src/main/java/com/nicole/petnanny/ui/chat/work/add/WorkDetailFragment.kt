package com.nicole.petnanny.ui.chat.work.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nicole.petnanny.databinding.FragmentWorkChatroomDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.chat.ChatRoomDetailAdapter

class WorkDetailFragment : Fragment() {

    private val viewModel by viewModels<WorkDetailViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkChatroomDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val chatRoomDetailAdapter = ChatRoomDetailAdapter()
        binding.rvWorkChatContent.adapter = chatRoomDetailAdapter

//        //一進來監聽之前聊天內容
//        var userTextList = MessageList()
//        val userText = mutableListOf<Message>()
//
//        val aaa = createMock().toMessageListItem()
//
//        chatRoomDetailAdapter.submitList(aaa)


        return binding.root
    }

//    fun createMock() : MessageList{
//        var user1 = Message(
//            content = "明天下午可以過去嗎?",
//
//        )
//
//        var user2 = Message(
//            content = "不要?",
//
//        )
//        var listText =MessageList()
//        var list = mutableListOf<Message>()
//        list.add(user1)
//        list.add(user2)
//
//
//
//        listText.messageText = list

//        return listText
//    }
}