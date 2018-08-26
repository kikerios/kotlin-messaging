package kikerios.me.kotlinmessaging.feature

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import kikerios.me.kotlinmessaging.feature.utils.ActivityController

class LoginActivity: BaseActivity() {

    private var mSignInButton: SignInButton? = null
    private var RC_SIGN_IN: Int = 9001
    private var TAG: String = "LoginActivy"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //UI elements
        mSignInButton  = findViewById<View>(R.id.sign_in_button) as SignInButton

        // Creating and Configuring Google Api Client.
        val mGoogleApiClient = getGoogleApiClient()

        // Listeners
        mSignInButton!!.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...)
        if (requestCode == RC_SIGN_IN) {
            val result: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                val account: GoogleSignInAccount? = result.getSignInAccount()
                firebaseAuthWithGoogle(account!!)
            } else {
                // Google Sign In failed
                Log.e(TAG, "Google Sign In failed.")
            }
        }
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId())

        val credential: AuthCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null)
        getAuth().signInWithCredential(credential)
            .addOnCompleteListener(this, object: OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful())

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException())
                        Toast.makeText(this@LoginActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@LoginActivity, "Authentication Successfull.", Toast.LENGTH_SHORT).show()
                        ActivityController.goToMain(this@LoginActivity)
                    }
                }
            })
    }
}