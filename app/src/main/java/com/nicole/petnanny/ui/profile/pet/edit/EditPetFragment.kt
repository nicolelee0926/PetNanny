package com.nicole.petnanny.ui.profile.pet.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentProfileEditPetDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.main.MainViewModel

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
//                else -> binding.radioGender.clearCheck()
            }

            // Preload petLigation
            when (it.petLigation.toString()) {
                "是" -> binding.radioLigation.check(R.id.radio_yes)
                "否" -> binding.radioLigation.check(R.id.radio_no)
//                else -> binding.radioLigation.clearCheck()
            }

            // Preload figure
            when (binding.spinnerType.selectedItemPosition) {

            }

            // Preload name
            binding.edPetName.setText(it.petName)

            // Preload variety
            binding.edPetVariety.setText(it.petVariety)

            // Preload health
            binding.edPetHealth.setText(it.petIntroduction)

            // Preload chip number
            binding.etPetChipNumber.setText(it.chipNumber)

        })

//        send to firebase update data
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.editPetFlag.observe(viewLifecycleOwner, Observer {
            if(it == true){
                viewModel.setEditPet()
                Toast.makeText(requireContext(), "修改寵物資料成功", Toast.LENGTH_SHORT).show()
                mainViewModel.changeEditPetStatusFalse()
            }
        })

//        update modified edit pet data
        viewModel.setEditPetData.observe(viewLifecycleOwner, Observer {
            Log.d("setEditPetDatauuuuu", "$it ")
            viewModel.updatePet(it)
        })


        binding.radioGender.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_female -> {
                        viewModel.setEditGender("母")
//                        Toast.makeText(requireContext(),"你選了母", Toast.LENGTH_SHORT).show()
                    }
                    R.id.radio_male -> {
                        viewModel.setEditGender("公")
//                        Toast.makeText(requireContext(),"你選了公", Toast.LENGTH_SHORT).show()
                    }
                }
            })

        binding.radioLigation.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_yes -> {
                        viewModel.setEditLigation("是")
//                        Toast.makeText(requireContext(),"你選了是", Toast.LENGTH_SHORT).show()
                    }
                    R.id.radio_no -> {
                        viewModel.setEditLigation("否")
//                        Toast.makeText(requireContext(),"你選了否", Toast.LENGTH_SHORT).show()
                    }
                }
            })


        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.editSelectedType.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        binding.spinnerAge.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.editSelectedAge.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }



        return binding.root
    }

//    fun setSpinnerType(pos: Int, binding: FragmentProfileEditPetDetailBinding) {
//        binding.spinnerType.adapter = when (pos) {
//            1 -> R.array.pet_array
//            else ->
//        }
//    }
}