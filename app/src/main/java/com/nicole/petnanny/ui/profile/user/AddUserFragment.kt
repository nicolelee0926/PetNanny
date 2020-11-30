package com.nicole.petnanny.ui.profile.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentPetBinding
import com.nicole.petnanny.databinding.FragmentProfileAddUserBinding
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections

class AddUserFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileAddUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this



        return binding.root
    }

}