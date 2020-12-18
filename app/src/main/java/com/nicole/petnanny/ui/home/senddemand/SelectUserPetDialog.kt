package com.nicole.petnanny.ui.home.senddemand

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
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

            builder.setSingleChoiceItems(arrayList, 0,
                DialogInterface.OnClickListener { dialog, which ->
                    // user checked an item
                    selectedItems.add(which)
                })

            // add OK and Cancel buttons
            builder.setPositiveButton("確定") { dialog, which ->
                // user clicked OK
                val selectPets = mutableListOf<Pet>()
                for (item in selectedItems) {
                    selectPets.add(userPetList[item])
                }
                viewModel._userPetList.value = selectPets
            }
            builder.setNegativeButton("取消", null)

            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}