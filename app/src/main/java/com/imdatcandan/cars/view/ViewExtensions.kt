package com.imdatcandan.cars.view

import android.app.Activity
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.imdatcandan.cars.R

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showLoading(show: Boolean) {
    if (show) {
        visible()
    } else {
        gone()
    }
}

fun Activity.showErrorDialog(message: String?, onClickListener: DialogInterface.OnClickListener) {
    AlertDialog.Builder(this)
        .setTitle(R.string.dialog_error_title)
        .setMessage(message)
        .setPositiveButton(R.string.dialog_error_button, onClickListener).create()
        .show()
}