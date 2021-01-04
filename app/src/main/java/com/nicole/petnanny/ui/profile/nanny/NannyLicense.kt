package com.nicole.petnanny.ui.profile.nanny

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicole.petnanny.databinding.FragmentProfileNannyExamineBinding
import com.nicole.petnanny.databinding.FragmentProfileNannyLicenseBinding

class NannyLicense: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileNannyLicenseBinding.inflate(inflater, container, false)

        return binding.root
    }
}
