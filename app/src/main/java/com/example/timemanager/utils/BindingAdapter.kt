package com.example.timemanager.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shared.utils.parseTimeFromMinutes

@BindingAdapter("mutableLiveData", "mutableLiveData2")
fun loadWeight2(
    view: View,
    mutableLiveData: MutableLiveData<Int>,
    mutableLiveData2: MutableLiveData<Int>
) {
    val temp = mutableLiveData2.value?.let { mutableLiveData.value?.minus(it) }
    if (temp != null) {
        if (temp <= 0) {
            view.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                0.0f
            )
        } else {
            view.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                temp.toFloat()
            )
        }
    }
}

@BindingAdapter("app:layout_weight_binding")
fun loadWeight(view: View, mutableLiveData: MutableLiveData<Int>) {
    if (mutableLiveData.value != null && mutableLiveData.value != 0) {
        view.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            mutableLiveData.value!!.toFloat()
        )
    } else {
        view.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            0.0f
        )
    }
}

@BindingAdapter("app:id_binding")
fun setId(view: View, int: Int) {
    view.id = int
}

@SuppressLint("SetTextI18n", "ResourceAsColor")
@BindingAdapter("app:text_time_binding")
fun setTextTime(view: TextView, mutableLiveData: MutableLiveData<Int?>) {
    view.text = mutableLiveData.value?.parseTimeFromMinutes()
//    if (mutableLiveData.value == 0) {
//        view.text = ""
//    } else {
//        view.text = mutableLiveData.value?.parseTimeFromMinutes()
//    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("app:text_time_binding_LiveData")
fun setTextTime2(view: TextView, liveData: LiveData<Int>) {
    view.text = liveData.value?.parseTimeFromMinutes()
}