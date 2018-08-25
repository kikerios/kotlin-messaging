package kikerios.me.kotlinmessaging.feature.utils

import android.os.Handler

/**
 * Created by kikerios on 8/24/2018.
 */
class Run {
    companion object {
        fun after(delay: Long, process: () -> Unit) {
            Handler().postDelayed({
                process()
            }, delay)
        }
    }
}