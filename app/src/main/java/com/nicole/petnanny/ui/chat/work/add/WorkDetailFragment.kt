package com.nicole.petnanny.ui.chat.work.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nicole.petnanny.data.Message
import com.nicole.petnanny.data.User
import com.nicole.petnanny.databinding.FragmentWorkChatroomDetailBinding
import com.nicole.petnanny.ext.getVmFactory

class WorkDetailFragment : Fragment() {

    private val viewModel by viewModels<WorkDetailViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkChatroomDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
//
//        val chatRoomDetailAdapter = ChatRoomDetailAdapter()
//        binding.rvWorkChatContent.adapter = chatRoomDetailAdapter

//        //一進來監聽之前聊天內容
//        val userTextList = userTextList()
//        val userText = mutableListOf<Message>()
//
//        val aaa = createMock().toUserTextItem()
//
//        chatRoomDetailAdapter.submitList(aaa)


        return binding.root
    }

//    fun createMock() : userTextList{
//        var user1 = Message(
//            content = "明天下午可以過去嗎?",
//            sender = User(
//                userEmail = "0"
//            )
//        )
//
//        var user2 = Message(
//            content = "不要?",
//            sender = User(
//                userEmail = "1"
//            )
//        )
//        var listText =userTextList()
//        var list = mutableListOf<Message>()
//        list.add(user1)
//        list.add(user2)
//
//
//
//        listText.userText = list
//
//        return listText
//    }
}