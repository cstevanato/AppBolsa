package br.com.stv.appbolsa

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class AppBolsaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .name("stock.realm")
                .schemaVersion(1)
                .build()
        Realm.setDefaultConfiguration(config)
    }
}