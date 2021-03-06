package com.nicole.petnanny.ui.profile.service.add

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.MobileNavigationDirections
import com.nicole.petnanny.dialog.SuccessSubmitDialog.AddSuccessPage
import com.nicole.petnanny.databinding.FragmentProfileAddServiceBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.main.MainViewModel
import com.github.dhaval2404.imagepicker.ImagePicker

class AddServiceFragment: Fragment() {

    private val viewModel by viewModels<AddServiceViewModel> { getVmFactory() }

    private val SERVICE_REQUEST_EXTERNAL_STORAGE = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileAddServiceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.addServiceFlag.observe(viewLifecycleOwner, Observer {
            if(it == true){
                if (viewModel.checkInfoComplete()) {
                    if (viewModel.selectedLocation.value == "全部") {
                        Toast.makeText(requireContext(), "請選擇服務區域", Toast.LENGTH_SHORT).show()
                    } else if (viewModel.selectedAcceptPet.value == "全部") {
                        Toast.makeText(requireContext(), "請選擇可接受的寵物類型", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.setService()
                        mainViewModel.changeServiceStatusFalse()
                    }
                } else {
                        Toast.makeText(requireContext(), "您的資料還沒填完唷", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.setServiceData.observe(viewLifecycleOwner, Observer {
            Log.d("serviceEditText", "$it ")
            viewModel.addService(it)
        })

        //  新增成功回到profile頁
        viewModel.submitDataFinished.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                //  Toast.makeText(requireContext(), "新增資料成功", Toast.LENGTH_SHORT).show()
                findNavController().navigate(MobileNavigationDirections.actionGlobalSuccessSubmitDialog(AddSuccessPage.ADD_SERVICE))
                viewModel.submitToFireStoreFinished()
            }
        })

        binding.spinnerAcceptType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.selectedAcceptPet.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.spinnerServiceArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.selectedLocation.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        binding.spinnerServiceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.selectedServiceType.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        //  checkPhotoPermission
        binding.btnServiceTakePhoto.setOnClickListener {
            checkPermission()
        }

        //  observe real URL Path
        viewModel.servicePhotoRealPath.observe(viewLifecycleOwner, Observer {
            Log.d("petPhotoRealPath", " $it ")
        })

        return binding.root
    }

    //  開啟相機權限詢問
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            SERVICE_REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocalImg()
                } else {
                    Toast.makeText(requireContext(),"請重新嘗試", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermission() {
        val permission = activity?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), SERVICE_REQUEST_EXTERNAL_STORAGE)
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

    //  拍完拿到file path
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val filePath: String = ImagePicker.getFilePath(data) ?: ""
                if (filePath.isNotEmpty()) {
                    val imgPath = filePath

                    //  拿到本地端URL後去call拿到新的URL function 把舊的傳進去換新的
                    viewModel.uploadServicePhoto(imgPath)
                    Log.d("imgPath", "$imgPath ")
                } else {
                    Toast.makeText(requireContext(), "讀取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}