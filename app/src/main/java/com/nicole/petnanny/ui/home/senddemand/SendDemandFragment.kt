package com.nicole.petnanny.ui.home.senddemand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicole.petnanny.databinding.FragmentSendDemandBinding

class SendDemandFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSendDemandBinding.inflate(inflater, container, false)

        return binding.root
    }
}