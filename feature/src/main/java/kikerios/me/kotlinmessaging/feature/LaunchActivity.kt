package kikerios.me.kotlinmessaging.feature

import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import kikerios.me.kotlinmessaging.feature.utils.ActivityController
import kikerios.me.kotlinmessaging.feature.utils.Run

class LaunchActivity : BaseActivity() {

    private var TAG: String = "LaunchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show()

        Run.after(2500, { // temp delay
            if (getUser() == null) {
                ActivityController.goToLogin(this)
            } else {
                ActivityController.goToMain(this)
            }
        })
    }
}
