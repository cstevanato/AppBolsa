package br.com.stv.appbolsa.controller

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver

class KeyboardController(private var activity: Activity,
                        private val delegate: (isOpen: Boolean) -> Unit) {
    var isOpened = false

    init {
        val density = activity.resources.displayMetrics.density

        val activityRootView = activity.window.decorView.findViewById<View>(android.R.id.content)
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {

            val r = Rect()
            val rootview = activity.window.decorView // this = activity
            rootview.getWindowVisibleDisplayFrame(r)
            val keyboardHeight = rootview.height - r.bottom

            if (keyboardHeight > 100) { // 99% of the time the height diff will be due to a keyboard.

                if (!isOpened) {
                    delegate(true)
                }
                isOpened = true
            } else if (isOpened) {
                delegate(false)
                isOpened = false
            }
        })
    }


}