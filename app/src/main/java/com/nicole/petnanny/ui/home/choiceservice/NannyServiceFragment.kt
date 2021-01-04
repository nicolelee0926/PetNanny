package com.nicole.petnanny.ui.home.choiceservice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nicole.petnanny.databinding.FragmentHomeNannyServiceBinding
import kotlinx.android.synthetic.main.fragment_home_nanny_service.*

class NannyServiceFragment(val listener:(String)->Unit): DialogFragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeNannyServiceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.btnHomeVisit.setOnClickListener {
            listener("到府照顧")
            dismiss()
        }

        binding.btnHomeShower.setOnClickListener {
            listener("到府洗澡")
            dismiss()
        }

        binding.btnHomeBeauty.setOnClickListener {
            listener("到府美容")
            dismiss()
        }

        binding.btnHomeTrain.setOnClickListener {
            listener("到府訓練")
            dismiss()
        }

        binding.btnWalk.setOnClickListener {
            listener("遛狗散步")
            dismiss()
        }

        binding.btnStay.setOnClickListener {
            listener("住宿託管")
            dismiss()
        }

        return binding.root
    }
}