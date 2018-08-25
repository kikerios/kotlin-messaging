package kikerios.me.kotlinmessaging.feature

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kikerios.me.kotlinmessaging.feature.utils.ActivityController
import kikerios.me.kotlinmessaging.feature.utils.Run
import java.util.*

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        Run.after(2500, { // temp delay
            ActivityController.goToLogin(this)
        })
    }
}
