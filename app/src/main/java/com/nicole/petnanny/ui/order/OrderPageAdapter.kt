package com.nicole.petnanny.ui.order

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nicole.petnanny.ui.order.nannyorder.MyClientFragment
import com.nicole.petnanny.ui.order.parentorder.MyOrderFragment

class OrderPageAdapter(fm: Fragment): FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MyOrderFragment(0)
            else -> MyClientFragment(1)
        }
    }
}