package com.example.code

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import com.example.code.di.ComponentManager

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        setUpRealm()
        setUpDagger()
    }

    private fun setUpRealm() {
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)

    }

    private fun setUpDagger() {
        ComponentManager.initAppComponent(this)
    }
}