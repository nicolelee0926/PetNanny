package com.nicole.petnanny

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicole.petnanny.databinding.FragmentSuccessSubmitBinding
import com.nicole.petnanny.ui.main.MainViewModel

class SuccessSubmitDialog: DialogFragment() {

    var type: Int? = null

    private val successPageType by lazy {
        SuccessSubmitDialogArgs.fromBundle(requireArguments()).pageTypeKey
    }

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
                when (successPageType) {
                    AddSuccessPage.ADD_PET -> {
                        findNavController().navigate(SuccessSubmitDialogDirections.actionSuccessSubmitDialogToNavigationProfile())
                        type = 1
                    }
                    AddSuccessPage.ADD_SERVICE -> {
                        findNavController().navigate(SuccessSubmitDialogDirections.actionSuccessSubmitDialogToNavigationProfile())
                        type = 2
                    }
                    AddSuccessPage.ADD_NANNY_EXAMINE -> {
                        findNavController().navigate(SuccessSubmitDialogDirections.actionSuccessSubmitDialogToNavigationProfile())
                        type = 3
                    }
                    AddSuccessPage.ADD_DEMAND -> {
                        findNavController().navigate(SuccessSubmitDialogDirections.actionGlobalNavigationOrder())
                        type = 4
                    }
                }

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