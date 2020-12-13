package com.nicole.petnanny.ui.order.nannyorder.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.petnanny.databinding.FragmentMyClientDetailBinding
import com.nicole.petnanny.ext.getVmFactory

class MyClientDetailFragment : Fragment() {
    lateinit var binding:FragmentMyClientDetailBinding

    private val viewModel by viewModels<MyClientDetailViewModel> {
        getVmFactory(
            MyClientDetailFragmentArgs.fromBundle(requireArguments()).order
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyClientDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        setAcceptStatusView(viewModel.myClientDetail.value?.nannyAcceptStatus)
        setCheckoutCompleteStatusParent(viewModel.myClientDetail.value?.userCheckoutStatus)
        setNannyCompleteServiceView(viewModel.myClientDetail.value?.nannyCompletedStatus)


//    updateNannyAcceptStatus
        binding.btnAcceptOrder.setOnClickListener {
            viewModel.updateNannyAcceptStatus()
        }

//    observe acceptStatus後 元件做變化
        viewModel.liveAcceptStatusNanny.observe(viewLifecycleOwner, Observer {
            setAcceptStatusView(it)
        })

//     家長付款完成 服務即將開始
        viewModel.liveCheckoutCompleteStatusParent.observe(viewLifecycleOwner, Observer {
            setCheckoutCompleteStatusParent(it)
        })

//    updateNannyCompleteServiceStatus
        binding.btnNannyCheckoutServiceCompleted.setOnClickListener {
            viewModel.updateNannyCompleteServiceStatus()
        }
//    observe NannyCompleteServiceStatus後 元件做變化
        viewModel.liveNannyCompleteServiceStatus.observe(viewLifecycleOwner, Observer {
            setNannyCompleteServiceView(it)
        })

//    observe parentCheckServiceCompleteStatus後 元件做變化
        viewModel.liveCheckServiceCompleteStatus.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.layoutServiceCompletedFinallyNanny.visibility = View.VISIBLE
            }
        })


        return binding.root
    }

    private fun setNannyCompleteServiceView(it: Boolean?) {
        if (it == true) {
            binding.btnNannyCheckoutServiceCompleted.visibility = View.GONE
            binding.layoutServiceCompletedWaitParent.visibility = View.VISIBLE
        }
    }

    private fun setCheckoutCompleteStatusParent(it: Boolean?) {
        if (it == true) {
            binding.layoutServiceStart.visibility = View.VISIBLE
            binding.btnNannyCheckoutServiceCompleted.visibility = View.VISIBLE
            binding.layoutWaitParentCheckout.visibility = View.VISIBLE
        }
    }

    private fun setAcceptStatusView(it: Boolean?) {
        if (it == true) {
            binding.btnRejectOrder.visibility = View.GONE
            binding.btnAcceptOrder.visibility = View.GONE
            binding.layoutWaitParentCheckout.visibility = View.VISIBLE
        }
    }

}