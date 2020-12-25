package com.nicole.petnanny

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicole.petnanny.databinding.FragmentSuccessSubmitBinding

class SuccessSubmitDialog: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.custom_dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentSuccessSubmitBinding.inflate(inflater, container, false)


        binding.lottieSuccessSubmit.addAnimatorListener( object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                dismiss()
//                when (page) {
//
//                }
                findNavController().navigate(SuccessSubmitDialogDirections.actionSuccessSubmitDialogToNavigationProfile())
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })



        binding.lottieSuccessSubmit.playAnimation()

        return binding.root
    }

    enum class AddSuccessPage(val value: PageType) {
        ADD_PET(PageType()),
        ADD_SERVICE(PageType()),
        ADD_NANNY_EXAMINE(PageType()),
        ADD_DEMAND(PageType())
    }

    interface IPageType {
        var type: Int
    }

    class PageType : IPageType {
        private var _type = 0
        override var type: Int
            get() = _type
            set(value) { _type = value }
    }

}