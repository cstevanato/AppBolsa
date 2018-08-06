package br.com.stv.appbolsa

import android.app.Application
import android.os.Debug
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration
import com.crashlytics.android.answers.Answers


class AppBolsaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .name("stock.realm")
                .schemaVersion(2)
                .build()
        Realm.setDefaultConfiguration(config)

        if (!BuildConfig.DEBUG) {


            // First, init fabric
            val crashlytics = Fabric.Builder(this)
                    .kits(Crashlytics(), Answers())
                    .debuggable(true)
                    .build()
            Fabric.with(crashlytics)
        }
        // Fabric.with(this, Crashlytics())
    }



    //private fun logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
//        Crashlytics.setUserIdentifier("12345")
//        Crashlytics.setUserEmail("user@fabric.io")
//        Crashlytics.setUserName("Test User")

    //}
}