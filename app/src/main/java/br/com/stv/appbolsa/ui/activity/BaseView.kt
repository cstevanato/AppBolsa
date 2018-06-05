package br.com.stv.appbolsa.ui.activity

import android.app.Activity
import android.app.AlertDialog


interface BaseView<T> {
    fun printError(message: String)


    fun showErrorAlert(act: Activity, msg: String) {
        val builder = AlertDialog.Builder(act)
        builder.setMessage(msg)


        builder.setMessage("Are you want to set the app background color to RED?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->

        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    fun showErrorAlert(act: Activity, msg: String,  callback: ()->Unit) {
        val builder = AlertDialog.Builder(act)
        builder.setMessage(msg)


        builder.setMessage("Are you want to set the app background color to RED?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->
            callback()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    fun showSucessAlert(act: Activity, msg: String,  callback: ()->Unit) {
        val builder = AlertDialog.Builder(act)
        builder.setMessage(msg)


        builder.setMessage("Are you want to set the app background color to RED?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->
            callback()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

}