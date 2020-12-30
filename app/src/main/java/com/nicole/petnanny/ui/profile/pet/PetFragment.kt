package com.nicole.petnanny.ui.profile.pet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentPetBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.login.UserManager
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections

class PetFragment() : Fragment()  {
    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }

    private val viewModel by viewModels<PetViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val petAdapter = PetAdapter(viewModel, binding.layoutPetListView)
        binding.rvPet.adapter = petAdapter

        viewModel.pet.observe(viewLifecycleOwner, Observer {
                petAdapter.submitList(it)
        })

//        點新增毛寶貝後跳到新增寵物頁面
        binding.btnAddPet.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToAddPetFragmentL())
        }

//        navigate to edit pet detail page
        viewModel.navigationToEditPetDetail.observe(viewLifecycleOwner, Observer {
            if(null != it) {
                findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToEditPetFragment(it))
                viewModel.displayEditPetDetailsComplete()
            }
        })


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("!!!!", "!!! ");
        UserManager.user.value?.userEmail?.let {
            Log.d("!!!", "$it ");
            viewModel.getPetsResult()
        }
    }
}