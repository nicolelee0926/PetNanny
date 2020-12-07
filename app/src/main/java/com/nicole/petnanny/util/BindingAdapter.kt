package com.nicole.petnanny.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nicole.petnanny.R
import org.w3c.dom.Text
import java.text.SimpleDateFormat

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

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?) {
    url?.let {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_chat_nanny_status)
                    .error(R.drawable.ic_launcher_background))
            .into(imageView)
    }
}

@BindingAdapter("getServicePrice")
fun TextView.bindGetServicePrice(price: String) {
    price?.let {
        text = "NT$$price 元/次"
    }
}



