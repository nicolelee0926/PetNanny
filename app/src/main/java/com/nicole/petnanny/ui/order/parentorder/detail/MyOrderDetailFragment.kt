package com.nicole.petnanny.ui.order.parentorder.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentMyClientDetailBinding
import com.nicole.petnanny.databinding.FragmentMyOrderDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.home.nannydetail.NannyDetailFragmentArgs
import com.nicole.petnanny.ui.home.nannydetail.NannyDetailViewModel

class MyOrderDetailFragment : Fragment() {
    lateinit var binding: FragmentMyOrderDetailBinding

    private val viewModel by viewModels<MyOrderDetailViewModel> {
        getVmFactory(
            MyOrderDetailFragmentArgs.fromBundle(requireArguments()).order
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyOrderDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setParentWaitCheckoutView(viewModel.myOrderDetail.value?.nannyAcceptStatus)
        setParentCheckoutCompleteView(viewModel.myOrderDetail.value?.userCheckoutStatus)
        setParentCheckNannyCompleteService(viewModel.myOrderDetail.value?.nannyCompletedStatus)
        setCompleteServiceFinally(viewModel.myOrderDetail.value?.userCheckedStatus)


//        保姆接受 家長要去完成付款
        viewModel.liveAcceptStatusNanny.observe(viewLifecycleOwner, Observer {
            setParentWaitCheckoutView(it)
        })

//    update ParentCheckoutCompleteStatus
        binding.btnCheckoutComplete.setOnClickListener {
            viewModel.updateParentCheckoutCompleteStatus()

        }
//    observe ParentCheckoutCompleteStatus
        viewModel.liveCheckoutStatusParent.observe(viewLifecycleOwner, Observer {
            setParentCheckoutCompleteView(it)
        })

//        保姆完成服務 家長要按確認按鈕
        viewModel.liveServiceCompletedNanny.observe(viewLifecycleOwner, Observer {
            setParentCheckNannyCompleteService(it)
        })

//    update ParentCheckCompleteServiceStatus
        binding.btnCheckServiceCompleted.setOnClickListener {
            viewModel.updateParentCheckCompleteServiceStatus()

        }
//    家長自己觀察到服務完成欄位變true
        viewModel.liveParentCheckCompleteServiceStatus.observe(viewLifecycleOwner, Observer {
            setCompleteServiceFinally(it)
        })

        return binding.root
    }

    private fun setCompleteServiceFinally(it: Boolean?) {
        if (it == true) {
            binding.layoutServiceCompletedFinally.visibility = View.VISIBLE
            binding.btnCheckServiceCompleted.visibility = View.GONE
            binding.ivCheckServiceCompleted.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_status_ok))
            binding.ivServiceCompletedFinally.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_status_ok))
        }
    }

    private fun setParentCheckNannyCompleteService(it: Boolean?) {
        if (it == true) {
            binding.btnCheckServiceCompleted.visibility = View.VISIBLE
            binding.layoutCheckServiceCompleted.visibility = View.VISIBLE
        }
    }

    private fun setParentWaitCheckoutView(it: Boolean?) {
        if (it == true) {
            binding.layoutWaitYourCheckout.visibility = View.VISIBLE
            binding.btnCheckoutComplete.visibility = View.VISIBLE
            binding.ivWaitNannyAccept.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_status_ok))
        }
    }

    private fun setParentCheckoutCompleteView(it: Boolean?) {
        if (it == true) {
            binding.btnCheckoutComplete.visibility = View.GONE
            binding.layoutYourCheckoutCompleted.visibility = View.VISIBLE
            binding.ivWaitYourCheckout.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_status_ok))
            binding.ivYourCheckoutCompleted.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_status_ok))
        }
    }
}