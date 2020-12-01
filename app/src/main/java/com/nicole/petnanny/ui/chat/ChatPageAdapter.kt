package com.nicole.petnanny.ui.chat

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nicole.petnanny.ui.chat.demand.DemandFragment
import com.nicole.petnanny.ui.chat.work.WorkFragment

class ChatPageAdapter(fm: Fragment): FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DemandFragment(0)
            else -> WorkFragment(1)
        }
    }
}