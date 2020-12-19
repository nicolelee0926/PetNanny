package com.nicole.petnanny.ui.chat.work.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.petnanny.databinding.FragmentWorkChatroomDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.chat.ChatRoomDetailAdapter

class WorkDetailFragment : Fragment() {

    private val viewModel by viewModels<WorkDetailViewModel> { getVmFactory(WorkDetailFragmentArgs.fromBundle(requireArguments()).order) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkChatroomDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val chatRoomDetailAdapter = ChatRoomDetailAdapter()
        binding.rvWorkChatContent.adapter = chatRoomDetailAdapter


        binding.btnSendMessage.setOnClickListener {
            viewModel.setWorkMessage()
            binding.etMessageContent.setText("")
        }

        viewModel.setWorkMessage.observe(viewLifecycleOwner, Observer {
            Log.d("setMessage", "$it ")
            viewModel.addWorkMessage(it)
        })

        viewModel.workMessages.observe(viewLifecycleOwner, Observer {
            Log.d("getMessageList", "$it ")
            chatRoomDetailAdapter.submitList(it)
            binding.rvWorkChatContent.post { binding.rvWorkChatContent.scrollToPosition(it.size - 1) }
        })

//        snapshot
        viewModel.liveWorkMessages.observe(viewLifecycleOwner, Observer {
            Log.d("uuuuu", "$it ")
            viewModel.getLiveMessage()
        })

//        控制status View
        binding.btnChatNannyStatus.setOnClickListener {
            if (binding.viewChatNannyStatus.visibility == View.GONE) {
                binding.viewChatNannyStatus.visibility = View.VISIBLE
                binding.viewDeco.visibility = View.VISIBLE
            } else {
                binding.viewChatNannyStatus.visibility = View.GONE
                binding.viewDeco.visibility = View.GONE
            }
        }

        return binding.root
    }


}