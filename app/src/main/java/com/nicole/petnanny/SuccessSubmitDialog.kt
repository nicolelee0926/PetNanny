package com.nicole.petnanny

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentSuccessSubmitBinding
import com.nicole.petnanny.ui.profile.pet.add.AddPetFragmentDirections

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

}