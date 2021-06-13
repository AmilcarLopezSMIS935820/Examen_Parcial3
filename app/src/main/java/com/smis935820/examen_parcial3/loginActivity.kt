package com.smis935820.examen_parcial3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.smis935820.examen_parcial3.FirebaseUtils.firebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class loginActivity : AppCompatActivity() {
    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
            signInInputsArray = arrayOf(etSignInEmail, etSignInPassword)
            btnSignIn.setOnClickListener {
                signInUser()
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

     fun create(view: View){
        startActivity(Intent(this, createAccountActivity::class.java))
        finish()
    }

    fun createAccount(view: View){
        startActivity(Intent(this, createAccountActivity::class.java))
    }
}