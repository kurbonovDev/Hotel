package com.example.hotel.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.hotel.databinding.ErrorDialogBinding

object ErrorDialog {
    fun showDialog(context: Context,errorMessage:String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)

        val binding = ErrorDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {

            errorText.text = errorMessage
            close.setOnClickListener {
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

}