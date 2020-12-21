package com.nicole.petnanny.ui.profile.pet.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentProfileEditPetDetailBinding
import com.nicole.petnanny.ext.getVmFactory

class EditPetFragment: Fragment() {

    private val viewModel by viewModels<EditPetViewModel> { getVmFactory(EditPetFragmentArgs.fromBundle(requireArguments()).pet) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileEditPetDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

//  preload gender and petLigation
        viewModel.petDetail.observe(viewLifecycleOwner, Observer {
            // Preload Gender
            when (it.gender.toString()) {
                "公" -> binding.radioGender.check(R.id.radio_male)
                "母" -> binding.radioGender.check(R.id.radio_female)
                else -> binding.radioGender.clearCheck()
            }

            // Preload petLigation
            when (it.petLigation.toString()) {
                "是" -> binding.radioLigation.check(R.id.radio_yes)
                "否" -> binding.radioLigation.check(R.id.radio_no)
                else -> binding.radioLigation.clearCheck()
            }

            // Preload figure
            when (binding.spinnerType.selectedItemPosition) {

            }
        })

        return binding.root
    }

//    fun setSpinnerType(pos: Int, binding: FragmentProfileEditPetDetailBinding) {
//        binding.spinnerType.adapter = when (pos) {
//            1 -> R.array.pet_array
//            else ->
//        }
//    }
}