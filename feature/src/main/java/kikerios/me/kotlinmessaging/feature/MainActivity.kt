package kikerios.me.kotlinmessaging.feature

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

/**
 * Created by kikerios on 8/25/2018.
 */
class MainActivity: BaseActivity() {

    private var TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.sign_out_menu -> {
                logout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}