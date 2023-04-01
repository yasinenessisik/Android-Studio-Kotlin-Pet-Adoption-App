package com.yasinenessisik.adopt_a_pet.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.databinding.ActivityLoginActivityBinding
import com.yasinenessisik.adopt_a_pet.databinding.ActivitySignUpBinding
import com.yasinenessisik.adopt_a_pet.model.User

class SignUp : AppCompatActivity() {
    private lateinit var  auth : FirebaseAuth

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.signUp.setOnClickListener{
            signUp(it)

        }



    }
    fun signUp(view: View){


        var userMail = binding.loginEmailText.text.toString()
        var userPassword = binding.loginPasswordText.text.toString()
        var nickname = binding.nickName.text.toString()

        auth.createUserWithEmailAndPassword(userMail,userPassword).addOnCompleteListener{task->
            if(task.isSuccessful){
                addUserToDatabase(userMail,auth.currentUser!!.uid,nickname)
                val intent = Intent(this, NavigationBarActivity::class.java)
                startActivity(intent)
                finish()
            }

        }.addOnFailureListener{exception ->
            Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }
    private fun addUserToDatabase(email:String, uid:String,nickname:String){
        database = FirebaseDatabase.getInstance().getReference()
        database.child("user").child(uid).setValue(User(email,uid,nickname))
    }
    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed();
    }
}