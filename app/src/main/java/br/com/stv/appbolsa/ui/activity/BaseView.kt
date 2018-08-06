package br.com.stv.appbolsa.ui.activity

import android.app.Activity
import android.app.AlertDialog


interface BaseView<T> {
    fun printError(message: String)


    fun showErrorAlert(act: Activity, msg: String) {
        val builder = AlertDialog.Builder(act)
        builder.setMessage(msg)

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("OK"){dialog, which ->

        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    fun showErrorAlert(act: Activity, msg: String,  callback: ()->Unit) {
        val builder = AlertDialog.Builder(act)
        builder.setMessage(msg)

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("OK"){dialog, which ->
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

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("OK"){dialog, which ->
            callback()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    fun showQuestion(act: Activity, msg: String,  positiveCallback: ()->Unit, negativeCallback: () -> Unit) {
        val builder = AlertDialog.Builder(act)
        builder.setMessage(msg)

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("OK"){ _, _ ->
            positiveCallback()
        }

        // Set a positive button and its click listener on alert dialog
        builder.setNegativeButton("Não"){ _, _ ->
            negativeCallback()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

}