package com.yasinenessisik.adopt_a_pet.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.yasinenessisik.adopt_a_pet.databinding.ActivityLoginActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var  auth : FirebaseAuth

    private lateinit var binding: ActivityLoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityLoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()



        val currentUser = auth.currentUser
        if(currentUser != null){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun signIn(view: View) {

        var userMail = binding.loginEmailText.text.toString()
        var userPassword = binding.loginPasswordText.text.toString()


        auth.signInWithEmailAndPassword(userMail, userPassword)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    val user = auth.currentUser
                    Toast.makeText(applicationContext,"Hosgeldin ${user?.email.toString()}", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {

                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()

                }
            }.addOnFailureListener { exception->
                Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }


    }
    fun signUp(view: View){


        var userMail = binding.loginEmailText.text.toString()
        var userPassword = binding.loginPasswordText.text.toString()

        auth.createUserWithEmailAndPassword(userMail,userPassword).addOnCompleteListener{task->
            if(task.isSuccessful){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }.addOnFailureListener{exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }
}