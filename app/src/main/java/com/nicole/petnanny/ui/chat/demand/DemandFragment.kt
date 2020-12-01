package com.nicole.petnanny.ui.chat.demand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.databinding.FragmentChatDemandBinding

class DemandFragment() : Fragment() {

    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }

    private lateinit var viewModel: DemandViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatDemandBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(DemandViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }
}