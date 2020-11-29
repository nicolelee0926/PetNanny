package com.nicole.petnanny.ui.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nicole.petnanny.ui.profile.comment.CommentFragment
import com.nicole.petnanny.ui.profile.pet.PetFragment
import com.nicole.petnanny.ui.profile.service.ServiceFragment

class ProfilePageAdapter(fm: Fragment) : FragmentStateAdapter(fm){

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PetFragment(0)
            1 -> ServiceFragment(1)
            else -> CommentFragment(2)
        }
    }
}