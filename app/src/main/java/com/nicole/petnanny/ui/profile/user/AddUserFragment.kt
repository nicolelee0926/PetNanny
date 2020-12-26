package com.nicole.petnanny.ui.profile.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.MobileNavigationDirections
import com.nicole.petnanny.databinding.FragmentProfileAddUserBinding
import com.nicole.petnanny.dialog.SuccessEditDialog
import com.nicole.petnanny.dialog.SuccessSubmitDialog
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.login.UserManager
import com.nicole.petnanny.ui.main.MainViewModel
import com.nicole.petnanny.ui.profile.pet.add.AddPetFragmentDirections

class AddUserFragment: Fragment() {

    private val viewModel by viewModels<AddUserViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileAddUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.addUserFlag.observe(viewLifecycleOwner, Observer {
            if(it == true){
                viewModel.setUser()
                mainViewModel.changeUserStatusFalse()
            }
        })

//        update modified profile data
        viewModel.setUserData.observe(viewLifecycleOwner, Observer {
            Log.d("userEditText", "$it ")
            viewModel.updateUser(it)
        })

        //        新增成功回到profile頁
        viewModel.submitDataFinished.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                if(viewModel.userIntroduction.value != "" && viewModel.userName.value != "") {
                }
                findNavController().navigate(MobileNavigationDirections.actionGlobalSuccessEditDialog(SuccessEditDialog.EditSuccessPage.EDIT_PROFILE))
                viewModel.submitToFireStoreFinished()
            }
        })

        // Preload info if user has already filled in advance
        viewModel.personalInfo.observe(viewLifecycleOwner, Observer {
            binding.etUserName.setText(it.userName)
            binding.etSelfIntroduction.setText(it.selfIntroduction)
        })

        return binding.root
    }

}