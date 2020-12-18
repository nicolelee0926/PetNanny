package com.nicole.petnanny.ui.home.senddemand

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.nicole.petnanny.data.Pet


class SelectUserPetDialog(val userPetList: List<Pet>, val viewModel: SendDemandViewModel): DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            // setup the alert builder
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("請選擇您的寵物")

            // add a radio button list
            val arrayList = userPetList.map { it?.petName }.toTypedArray()
            val selectedItems = ArrayList<Int>() //we selected item position
            viewModel.selectedPet.value = userPetList[0]

            builder.setSingleChoiceItems(arrayList, 0) { dialog, which ->
                    // user checked an item
                Log.d("which2", "$which ")
                viewModel.selectedPet.value = userPetList[which]
                }

            // add OK and Cancel buttons
            builder.setPositiveButton("確定", null)
//            { dialog, which ->
//                // user clicked OK
//                Log.d("which", "$which ")
//                Log.d("userPetListvvvvv", "$userPetList ")
//
//            }
            builder.setNegativeButton("取消", null)

            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}