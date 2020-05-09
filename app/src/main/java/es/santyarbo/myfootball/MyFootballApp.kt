package es.santyarbo.myfootball

import android.app.Application

class MyFootballApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initDI()
    }
}