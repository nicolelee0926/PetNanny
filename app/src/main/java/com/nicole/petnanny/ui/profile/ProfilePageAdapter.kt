package com.nicole.petnanny.ui.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfilePageAdapter(fm: Fragment) : FragmentStateAdapter(fm){

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PetFragment(0)
            1 -> NannyFragment(1)
            else -> CommentFragment(2)
        }
    }
}