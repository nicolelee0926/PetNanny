package com.nicole.petnanny.dialog

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentSuccessEditBinding

class SuccessEditDialog: DialogFragment() {

    var type: Int? = null

    private val successEditPageType by lazy {
        SuccessEditDialogArgs.fromBundle(requireArguments()).editPageTypeKey
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.custom_dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentSuccessEditBinding.inflate(inflater, container, false)

        binding.lottieSuccessEdit.addAnimatorListener( object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                dismiss()
                when (successEditPageType) {
                    EditSuccessPage.EDIT_PET -> {
                        findNavController().navigate(SuccessEditDialogDirections.actionSuccessEditDialogToNavigationProfile())
                        type = 1
                    }
                    EditSuccessPage.EDIT_PROFILE -> {
                        findNavController().navigate(SuccessEditDialogDirections.actionSuccessEditDialogToNavigationProfile())
                        type = 2
                    }
                    EditSuccessPage.EDIT_SERVICE -> {
                        findNavController().navigate(SuccessEditDialogDirections.actionSuccessEditDialogToNavigationProfile())
                        type = 3
                    }
                }

            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })

        binding.lottieSuccessEdit.playAnimation()

        return binding.root
    }

    enum class EditSuccessPage(val value: EditPageType) {
        EDIT_PET(EditPageType()),
        EDIT_SERVICE(EditPageType()),
        EDIT_PROFILE(EditPageType())
    }

    interface IEditPageType {
        var type: Int
    }

    class EditPageType : IEditPageType {
        private var _type = 0
        override var type: Int
            get() = _type
            set(value) { _type = value }
    }
}