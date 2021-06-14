package com.smis935820.examen_parcial3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.smis935820.examen_parcial3.FirebaseUtils.firebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

enum class ProviderType{
    BASIC,
    GOOGLE
}
@Suppress("DEPRECATION")
class loginActivity : AppCompatActivity() {
    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>
    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
            signInInputsArray = arrayOf(etSignInEmail, etSignInPassword)
            btnSignIn.setOnClickListener {
                signInUser()
            }

        googleButton.setOnClickListener {
            val googleconf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleconf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    private fun  showHome(email:String, provide: ProviderType ){
        val homeIntent = Intent(this, inicio::class.java)
        startActivity(homeIntent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            try {
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            showHome(account.email ?:"", ProviderType.GOOGLE)
                        }else{
                            Toast.makeText(applicationContext, "Ha habido un error, intentelo mas tarde", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: ApiException){
                Toast.makeText(applicationContext, "Ha habido un error, intentelo mas tarde", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun notEmpty():Boolean= signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    private fun signInUser(){
            signInEmail = etSignInEmail.text.toString().trim()
            signInPassword = etSignInPassword.text.toString().trim()

            if (notEmpty()){
                firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword).addOnCompleteListener { signIn->
                    if (signIn.isSuccessful){
                        startActivity(Intent(this, inicio::class.java))
                        Toast.makeText(applicationContext, "Ha iniciado sesión correctamente", Toast.LENGTH_LONG).show()
                        finish()
                    } else{
                        Toast.makeText(applicationContext, "No se ha podido iniciar sesión", Toast.LENGTH_LONG).show()
                    }
                }

            } else{
                signInInputsArray.forEach { input ->
                    if (input.text.toString().trim().isEmpty()){
                        input.error = "${input.hint} no debe quedar vacio"
                    }
                }
            }
    }


    fun createAccount(view: View){
        startActivity(Intent(this, createAccountActivity::class.java))
    }
}