package kikerios.me.kotlinmessaging.feature

import android.app.Application
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.google.firebase.FirebaseApp

/**
 * Created by kikerios on 8/25/2018.
 */
class MyApplication: MultiDexApplication() {

    private var TAG: String = "MyApplication"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Kotlin Messaging Demo")
        FirebaseApp.initializeApp(this)
    }
}