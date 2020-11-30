package com.nicole.petnanny

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("petAge")
fun bindPetAge(textView: TextView, int: Int) {
    int.let {
        textView.text = "$int 歲"
    }
}

@BindingAdapter("servicePrice")
fun bindServicePrice(textView: TextView, int: Int) {
    int.let {
        textView.text = "$int"
    }
}

