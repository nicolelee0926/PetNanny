package com.nicole.petnanny.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayoutMediator
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentChatBinding
import com.nicole.petnanny.databinding.FragmentOrderBinding
import com.nicole.petnanny.ui.chat.ChatPageAdapter
import com.nicole.petnanny.ui.chat.ChatViewModel

class OrderFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentOrderBinding.inflate(inflater, container, false)
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        val pager = binding.pagerOrder
        val tab_layout = binding.tabLayoutOrder
        pager.adapter = OrderPageAdapter(this)

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            when (position) {
                0 -> tab.text = "我的需求"
                1 -> tab.text = "我的客戶"
            }
            pager.setCurrentItem(tab.position, true)
        }.attach()



        return binding.root
    }
}