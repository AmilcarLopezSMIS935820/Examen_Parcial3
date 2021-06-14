package com.smis935820.examen_parcial3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.smis935820.examen_parcial3.FirebaseUtils.firebaseAuth
import com.smis935820.examen_parcial3.FirebaseUtils.firebaseUser
import kotlinx.android.synthetic.main.activity_create_account.*

class createAccountActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        createAccountInputsArray = arrayOf(etEmail, etPassword, etConfirmPassword)
        btnCreateAccount.setOnClickListener {
            createAccount()
        }

        btnregresar.setOnClickListener {
            startActivity(Intent(this, loginActivity::class.java))
            finish()
        }
    }

    override fun onStart(){
        super.onStart()
        val user: FirebaseUser? = firebaseAuth?.currentUser
        user?.let {
            startActivity(Intent(this, inicio::class.java))
            Toast.makeText(applicationContext, "Ha iniciado sesión", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun notEmpty(): Boolean= etEmail.text.toString().trim().isNotEmpty()
                && etPassword.text.toString().trim().isNotEmpty()
                    && etConfirmPassword.text.toString().trim().isNotEmpty()

    private fun identicalPassword():Boolean{
        var identical = false
        if(notEmpty() && etPassword.text.toString().trim() == etConfirmPassword.text.toString().trim()){
            identical = true
        } else if(!notEmpty()){
            createAccountInputsArray.forEach{ input->
                if (input.text.toString().trim().isEmpty()){
                    input.error = "${input.hint} el campo es requerido"
                }
            }
        } else{
            Toast.makeText(applicationContext, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
        }
        return identical
    }

    private fun createAccount(){
        if(identicalPassword()){
            userEmail = etEmail.text.toString().trim()
            userPassword = etPassword.text.toString().trim()

            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener{ task->
                if (task.isSuccessful){
                    Toast.makeText(applicationContext, "La cuenta ha sido creada", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, inicio::class.java))
                    finish()
                } else{
                    Toast.makeText(applicationContext, "¡¡Ups!!... Su cuenta no ha sido creada, intentelo mas tarde", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}