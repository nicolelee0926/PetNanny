package com.nicole.petnanny.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator

class ChatFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = com.nicole.petnanny.databinding.FragmentChatBinding.inflate(inflater, container, false)
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        val pager  = binding.pagerChat
        val tab_layout = binding.tabLayoutChat
        pager.adapter = ChatPageAdapter(this)

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            when(position){
                0-> tab.text = "需求"
                1-> tab.text = "工作"
            }
            pager.setCurrentItem(tab.position, true)
        }.attach()

        return binding.root
    }
}