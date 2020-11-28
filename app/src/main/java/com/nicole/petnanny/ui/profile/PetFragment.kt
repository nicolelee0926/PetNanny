package com.nicole.petnanny.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicole.petnanny.databinding.FragmentPetBinding

class PetFragment() : Fragment()  {
    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPetBinding.inflate(inflater, container, false)

        return binding.root
    }
}