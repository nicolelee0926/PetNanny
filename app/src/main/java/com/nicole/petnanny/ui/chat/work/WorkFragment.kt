package com.nicole.petnanny.ui.chat.work

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentChatWorkBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.chat.ChatFragmentDirections

class WorkFragment(): Fragment() {

    private val viewModel by viewModels<WorkViewModel> { getVmFactory() }

    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatWorkBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val workAdapter = WorkAdapter(viewModel)
        binding.rvChatWork.adapter = workAdapter

        viewModel.workOrderChatRoomList.observe(viewLifecycleOwner, Observer {
                workAdapter.submitList(it)
        })

        //  get live work order snapshot
        viewModel.liveWorkOrderChatRoomList.observe(viewLifecycleOwner, Observer {
                viewModel.getLiveWorkOrder()
        })

        viewModel.navigationToWorkChatRoomDetail.observe(viewLifecycleOwner, Observer {
            if(null != it) {
                findNavController().navigate(ChatFragmentDirections.actionNavigationChatToWorkDetailFragment(it))
                viewModel.displayChatRoomDetailComplete()
            }
        })


        //  loading
        binding.lottieLoading.addAnimatorListener( object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                binding.lottieLoading.visibility = View.GONE

            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })

        return binding.root
    }
}