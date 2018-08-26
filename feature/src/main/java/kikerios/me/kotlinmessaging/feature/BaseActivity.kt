package kikerios.me.kotlinmessaging.feature

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Created by kikerios on 8/25/2018.
 */
open class BaseActivity: AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private var TAG: String = "BaseActivity"
    private var mGoogleApiClient: GoogleApiClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildGoogleApiClient()
    }

    fun buildGoogleApiClient() {
        // Configure Google Sign In
        val mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        // Creating and Configuring Google Api Client.
        mGoogleApiClient = GoogleApiClient.Builder(this@BaseActivity)
            .enableAutoManage(this@BaseActivity, this@BaseActivity)
            .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
            .build()
    }

    fun getAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    fun getUser(): FirebaseUser? {
        return getAuth().currentUser
    }

    fun getGoogleApiClient(): GoogleApiClient? {
        if(mGoogleApiClient != null) {
            return mGoogleApiClient
        }

        // rebuild if is necesary
        buildGoogleApiClient()
        return mGoogleApiClient
    }

    fun logout() {
        getAuth().signOut()
        Auth.GoogleSignInApi.signOut(getGoogleApiClient())
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult)
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }
}