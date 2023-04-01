package com.yasinenessisik.adopt_a_pet.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yasinenessisik.adopt_a_pet.databinding.ActivityLoginActivityBinding
import com.yasinenessisik.adopt_a_pet.model.User

class LoginActivity : AppCompatActivity() {

    private lateinit var  auth : FirebaseAuth

    private lateinit var binding: ActivityLoginActivityBinding

    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityLoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()



        val currentUser = auth.currentUser
        if(currentUser != null){
            var intent = Intent(this, NavigationBarActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
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
                    var intent = Intent(this, NavigationBarActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {

                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()

                }
            }.addOnFailureListener { exception->
                Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }


    }

}