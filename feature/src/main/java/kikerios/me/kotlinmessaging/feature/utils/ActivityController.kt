package kikerios.me.kotlinmessaging.feature.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import kikerios.me.kotlinmessaging.feature.LoginActivity

/**
 * Created by kikerios on 8/24/2018.
 */
class ActivityController {
    companion object {
        fun goToLogin(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            if (context is Activity) {
                context.finish()
            }
        }
    }
}