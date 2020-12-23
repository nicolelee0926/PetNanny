package com.nicole.petnanny.ui.profile.pet.add

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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentProfileAddPetBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.main.MainViewModel


class AddPetFragment : Fragment() {

    private val viewModel by viewModels<AddPetViewModel> { getVmFactory() }

    private val PET_REQUEST_EXTERNAL_STORAGE = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileAddPetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.addPetFlag.observe(viewLifecycleOwner, Observer {
//            if (it == true) {
//                if (viewModel.checkInfoComplete()) {
                    viewModel.setPet()
                    mainViewModel.changePetStatusFalse()
//                } else {
//                    Toast.makeText(requireContext(), "您的資料還沒填完唷", Toast.LENGTH_SHORT).show()
//                }
//            }
        })

        viewModel.setPetData.observe(viewLifecycleOwner, Observer {
            Log.d("petEditText", "$it ")
            viewModel.addPet(it)
        })

//        新增成功回到profile頁
        viewModel.submitDataFinished.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Toast.makeText(requireContext(), "新增資料成功", Toast.LENGTH_SHORT).show()
                findNavController().navigate(AddPetFragmentDirections.actionAddPetFragmentLToNavigationProfile())
                viewModel.submitToFireStoreFinished()
            }
        })

        binding.radioGender.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_female -> {
                        viewModel.setGender("母")
//                        Toast.makeText(requireContext(),"你選了母",Toast.LENGTH_SHORT).show()
                    }
                    R.id.radio_male -> {
                        viewModel.setGender("公")
//                        Toast.makeText(requireContext(),"你選了公",Toast.LENGTH_SHORT).show()
                    }
                }
            })

        binding.radioLigation.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_yes -> {
                        viewModel.setLigation("是")
//                        Toast.makeText(requireContext(),"你選了是",Toast.LENGTH_SHORT).show()
                    }
                    R.id.radio_no -> {
                        viewModel.setLigation("否")
//                        Toast.makeText(requireContext(),"你選了否",Toast.LENGTH_SHORT).show()
                    }
                }
            })


        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
               viewModel.selectedType.value = parent?.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }




        binding.spinnerAge.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.selectedAge.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

//        checkPhotoPermission
        binding.btnAddPetPhoto.setOnClickListener {
            checkPermission()
        }

//        observe real URL Path
        viewModel.petPhotoRealPath.observe(viewLifecycleOwner, Observer {
            Log.d("petPhotoRealPath", " $it ")
        })







        return binding.root
    }

//    開啟相機權限詢問
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PET_REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocalImg()
                } else {
                    Toast.makeText(requireContext(), "請重新嘗試", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermission() {
        val permission = activity?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PET_REQUEST_EXTERNAL_STORAGE
            )
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
                    viewModel.uploadPetPhoto(imgPath)
                    Log.d("imgPath", "$imgPath ")
                } else {
                    Toast.makeText(requireContext(), "讀取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}