package com.nicole.petnanny.ui.profile.pet.edit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.nicole.petnanny.MobileNavigationDirections
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentProfileEditPetDetailBinding
import com.nicole.petnanny.dialog.SuccessEditDialog
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.main.MainViewModel
import com.nicole.petnanny.ui.profile.pet.add.AddPetFragmentDirections

class EditPetFragment: Fragment() {

    private val viewModel by viewModels<EditPetViewModel> { getVmFactory(EditPetFragmentArgs.fromBundle(requireArguments()).pet) }

    private val EDIT_PET_REQUEST_EXTERNAL_STORAGE = 100

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

            viewModel.preloadPic()

        })

//        send to firebase update data
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.editPetFlag.observe(viewLifecycleOwner, Observer {
            if(it == true){
                viewModel.setEditPet()
//                Toast.makeText(requireContext(), "修改寵物資料成功", Toast.LENGTH_SHORT).show()
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

        //        修改成功回到profile頁
        viewModel.modifyDataFinished.observe(viewLifecycleOwner, Observer {
            if (it == true) {
//                Toast.makeText(requireContext(), "修改資料成功", Toast.LENGTH_SHORT).show()
                findNavController().navigate(MobileNavigationDirections.actionGlobalSuccessEditDialog(SuccessEditDialog.EditSuccessPage.EDIT_PET))
                viewModel.modifyDataFinished()
            }
        })

        //        checkPhotoPermission
        binding.btnAddPetPhoto.setOnClickListener {
            checkPermission()
        }

//        observe real URL Path
        viewModel.editPetPhotoRealPath.observe(viewLifecycleOwner, Observer {
//            viewModel.setEditPet()
            Log.d("editPetPhotoRealPath", " $it ")
        })


        return binding.root
    }

    //    開啟相機權限詢問
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            EDIT_PET_REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocalImg()
                } else {
                    Toast.makeText(requireContext(), "請重新嘗試", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermission() {
//        val permission = activity?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) }
        val permission = requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            requireActivity().requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                EDIT_PET_REQUEST_EXTERNAL_STORAGE)
        } else {
            getLocalImg()
        }
    }

    private fun getLocalImg() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    //    拍完拿到file path
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val filePath: String = ImagePicker.getFilePath(data) ?: ""
                if (filePath.isNotEmpty()) {
                    val imgPath = filePath

//                   拿到本地端URL後去call拿到新的URL function 把舊的傳進去換新的
                    viewModel.uploadEditPetPhoto(imgPath)
                    Log.d("imgPath", "$imgPath ")
                } else {
                    Toast.makeText(requireContext(), "讀取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}