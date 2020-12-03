package com.nicole.petnanny.util

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("petAge")
fun bindPetAge(textView: TextView, string: String) {
    string.let {
        textView.text = "$string"
    }
}

@BindingAdapter("servicePrice")
fun bindServicePrice(textView: TextView, int: Int) {
    int.let {
        textView.text = "$int"
    }
}

@BindingAdapter("intToString")
fun bindIntToSting(textView: TextView, int: Int) {
    int?.let{
        textView.text = int.toString()
    }
}

