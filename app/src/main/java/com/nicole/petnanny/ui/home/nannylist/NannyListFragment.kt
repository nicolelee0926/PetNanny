package com.nicole.petnanny.ui.home.nannylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentHomeNannyListBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.home.choiceservice.NannyServiceFragment

class NannyListFragment : Fragment() {

//    設flag讓第一次只load出serviceType
    var isAlreadyFetch = false

    private val viewModel by viewModels<NannyListViewModel> { getVmFactory(NannyListFragmentArgs.fromBundle(requireArguments()).serviceType) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeNannyListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val nannyListAdapter = NannyListAdapter(viewModel)
        binding.rvNannyList.adapter = nannyListAdapter


//      按下後跳出dialog的服務類別
        binding.btnChoiceService.setOnClickListener {
//            接dialog點選後call back function傳回來的字串
            NannyServiceFragment {
                Log.d("TEST", "NannyServiceFragment: $it")
                viewModel.serviceType.value = it
            }.show(parentFragmentManager, "NannyServiceFragment")
        }

//        load出首頁所選擇的類別後的list
        viewModel.nannyList.observe(viewLifecycleOwner, Observer {
//            第一次只load出serviceType 其他兩種條件篩選不執行
            isAlreadyFetch = true
            Log.d("pppppppppppppp", "${it.toString()} ")
            nannyListAdapter.submitList(it)
        })

//        navigate到nanny detail頁
        viewModel.navigationToNannyDetail.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                findNavController().navigate(
                    NannyListFragmentDirections.actionNannyListFragmentToNannyDetailFragment(
                        it
                    )
                )
                viewModel.displayNannyDetailsComplete()
            }
        })

//        搜尋用 選擇寵物型態 記得加position==0(全部為空字串)的判斷
        binding.spinnerTypeNannyList.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                    Log.d("spinnerType", "${parent?.selectedItem}")
                    viewModel.selectedPetType.value = if (position == 0) "" else parent?.selectedItem.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

//        搜尋用 選擇地區 記得加position==0(全部為空字串)的判斷
        binding.spinnerLocationNannyList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                    Log.d("spinnerLocation", "${parent?.selectedItem}")
                    viewModel.selectedLocation.value = if (position == 0) "" else parent?.selectedItem.toString()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }


//        選擇服務類型 記得參數要按順序排 不然就要寫serviceType = viewModel.serviceType.value.toString()
        viewModel.serviceType.observe(viewLifecycleOwner, Observer {
            viewModel.getThreeSelectedList(it, viewModel.selectedPetType.value.toString(), viewModel.selectedLocation.value.toString())
        })

//        選擇寵物類型
        viewModel.selectedPetType.observe(viewLifecycleOwner, Observer {
//      被選擇後執行 isAlreadyFetch = false
            if (isAlreadyFetch)
                viewModel.getThreeSelectedList(serviceType = viewModel.serviceType.value.toString(), petType = it, location = viewModel.selectedLocation.value.toString())
        })

//        選擇地區
        viewModel.selectedLocation.observe(viewLifecycleOwner, Observer {
//       被選擇後執行 isAlreadyFetch = false
            if (isAlreadyFetch)
                viewModel.getThreeSelectedList(serviceType = viewModel.serviceType.value.toString(), petType = viewModel.selectedPetType.value.toString(), location = it)
        })

        binding.btnLeave.setOnClickListener {
            viewModel.leaveDetail()
        }

        viewModel.leaveDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) findNavController().popBackStack()
            }
        })


        return binding.root
    }
}