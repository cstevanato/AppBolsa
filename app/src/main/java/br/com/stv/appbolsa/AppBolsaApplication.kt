package br.com.stv.appbolsa

import android.app.Application
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


        // First, init fabric
        val crashlytics = Fabric.Builder(this)
                .kits(Crashlytics(), Answers())
                .debuggable(true)
                .build()
        Fabric.with(crashlytics)

       // Fabric.with(this, Crashlytics())
    }
}