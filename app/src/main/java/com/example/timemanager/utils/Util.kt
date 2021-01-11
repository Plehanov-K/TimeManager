package com.example.timemanager.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.timemanager.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun launchIo(task: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        task()
    }
}

fun launchUi(task: suspend () -> Unit) {

    CoroutineScope(Dispatchers.Main).launch {
        task()
    }
}

suspend fun <R> launchForResult(task: suspend () -> R): R? {

    return CoroutineScope(Dispatchers.IO).async {
        task()
    }.await()
}


fun Fragment.showAlert(
    title: String? = null,
    message: String? = null,
    positiveButtonResId: Int = R.string.buttonOk,
    neutralButtonResId: Int = R.string.buttonCancel,
    positiveButtonFun: (() -> Unit)? = null,
    neutralButtonFun: (() -> Unit)? = null,
    view: View? = null,
    theme: Int
) {

    MaterialAlertDialogBuilder(requireContext(), theme)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonResId, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                positiveButtonFun?.let { it() }
                dialog?.dismiss()
            }

        })
        .setNeutralButton(neutralButtonResId, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                neutralButtonFun?.let { it() }
                dialog?.dismiss()
            }

        })
        .setView(view)
        .show()
}