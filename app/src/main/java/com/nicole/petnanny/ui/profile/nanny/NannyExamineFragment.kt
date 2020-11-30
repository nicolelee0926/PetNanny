package com.nicole.petnanny.ui.profile.nanny

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicole.petnanny.databinding.FragmentProfileNannyCenterBinding
import com.nicole.petnanny.databinding.FragmentProfileNannyExamineBinding

class NannyExamineFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileNannyExamineBinding.inflate(inflater, container, false)



        return binding.root
    }
}