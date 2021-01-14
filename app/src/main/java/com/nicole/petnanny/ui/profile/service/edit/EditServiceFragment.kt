package com.nicole.petnanny.ui.profile.service.edit

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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.nicole.petnanny.MobileNavigationDirections
import com.nicole.petnanny.databinding.FragmentProfileEditServiceDetailBinding
import com.nicole.petnanny.dialog.SuccessEditDialog
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.main.MainViewModel
import com.nicole.petnanny.ui.profile.pet.edit.EditPetFragmentDirections

class EditServiceFragment : Fragment() {

    private val viewModel by viewModels<EditServiceViewModel> { getVmFactory(EditServiceFragmentArgs.fromBundle(requireArguments()).nanny) }

    private val EDIT_SERVICE_REQUEST_EXTERNAL_STORAGE = 2000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileEditServiceDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        //  preload gender and petLigation
        viewModel.serviceDetail.observe(viewLifecycleOwner, Observer {

            // Preload name
            binding.edServiceName.setText(it.serviceName)

            // Preload introduction
            binding.edServiceIntroduction.setText(it.nannyIntroduction)

            // Preload price
            binding.editServicePrice.setText(it.price)

            viewModel.preloadPic()
        })

        //  send to firebase update data
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.editServiceFlag.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.setEditService()
                mainViewModel.changeEditServiceStatusFalse()
            }
        })

        //  update modified edit pet data
        viewModel.setEditServiceData.observe(viewLifecycleOwner, Observer {
            Log.d("setEditPetDatauuuuu", "$it ")
            viewModel.updateService(it)
        })

        binding.spinnerServiceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.editSelectedServiceType.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spinnerServiceArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.editSelectedServiceLocation.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spinnerAcceptType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.editSelectedAcceptPet.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //  修改成功回到profile頁
        viewModel.modifyDataFinished.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(MobileNavigationDirections.actionGlobalSuccessEditDialog(SuccessEditDialog.EditSuccessPage.EDIT_SERVICE))
                viewModel.modifyDataFinished()
            }
        })

        //  checkPhotoPermission
        binding.btnServiceTakePhoto.setOnClickListener {
            checkPermission()
        }

        //  observe real URL Path
        viewModel.editServicePhotoRealPath.observe(viewLifecycleOwner, Observer {
            Log.d("editPetPhotoRealPath", " $it ")
        })

        return binding.root
    }

    //    開啟相機權限詢問
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            EDIT_SERVICE_REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocalImg()
                } else {
                    Toast.makeText(requireContext(), "請重新嘗試", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermission() {
        val permission = requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            requireActivity().requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                EDIT_SERVICE_REQUEST_EXTERNAL_STORAGE)
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

                    //  拿到本地端URL後去call拿到新的URL function 把舊的傳進去換新的
                    viewModel.uploadEditServicePhoto(imgPath)
                    Log.d("imgPath", "$imgPath ")
                } else {
                    Toast.makeText(requireContext(), "讀取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}