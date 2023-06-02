package com.yasinenessisik.adopt_a_pet.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.yasinenessisik.adopt_a_pet.databinding.ActivityLoginActivityBinding

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



        }

    }


