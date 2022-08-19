package com.accenture.githubapps.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.content.ContextCompat
import com.accenture.githubapps.R
import com.google.android.material.textfield.TextInputEditText

class HelperNotification {

    interface CallBackNotificationYesNo {
        fun onNotificationYes()
        fun onNotificationNo()
    }

    interface CallBackNotificationYesNoSubmit {
        fun onNotificationYes()
        fun onNotificationSubmit()
        fun onNotificationNo()
    }

    interface CallBackNotificationYesNoWithComment {
        fun onNotificationYes(comment: String = "")
        fun onNotificationNo()
    }

    interface CallBackNotificationYesNoWithRating {
        fun onNotificationYes(comment: String = "",rate: Int = 0)
        fun onNotificationNo()
    }

    interface CallbackRetry{
        fun onRetry()
    }

    interface CallbackDismis{
        fun onDismiss()
    }

    interface CallbackList {
        fun onView()
        fun onEdit()
        fun onSubmit()
        fun onCheck()
        fun onCheckFinal()
        fun onImplementation()
        fun onSubmitLaporan()
        fun onReview()
        fun onReviewFinal()
        fun onDelete()
    }

    fun showErrorDialog(activity: Activity, header :String, content : String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_error)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        (dialog.findViewById<View>(R.id.title_warning) as TextView).text = header
        (dialog.findViewById<View>(R.id.content_warning) as TextView).text = content
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        (dialog.findViewById<View>(R.id.bt_close) as AppCompatButton).setOnClickListener { v ->
            dialog.dismiss()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }

    fun showNotification(activity: Activity, header: String,content: String){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_notification)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        (dialog.findViewById<View>(R.id.txt_1) as TextView).text = header
        (dialog.findViewById<View>(R.id.txt_2) as TextView).text = content
        dialog.setCancelable(true)
        (dialog.findViewById<View>(R.id.btn_dismiss) as TextView).setOnClickListener { v ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showNotificationDismisAction(activity: Activity, header: String,content: String,listener : CallbackDismis){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_notification)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        (dialog.findViewById<View>(R.id.txt_1) as TextView).text = header
        (dialog.findViewById<View>(R.id.txt_2) as TextView).text = content
        dialog.setCancelable(false)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        (dialog.findViewById<View>(R.id.btn_dismiss) as TextView).setOnClickListener { v ->
            dialog.dismiss()
            if (listener != null)listener.onDismiss()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }

    fun displayNoInternet(activity: Activity,listener: CallbackRetry){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.nointernet_screen)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        (dialog.findViewById<View>(R.id.bt_retry)as AppCompatButton).setOnClickListener {
            dialog.dismiss()
            if (listener != null)listener.onRetry()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }

}


