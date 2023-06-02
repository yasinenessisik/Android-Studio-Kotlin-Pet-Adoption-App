package com.yasinenessisik.adopt_a_pet.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.yasinenessisik.adopt_a_pet.databinding.FragmentSignInBinding
import com.yasinenessisik.adopt_a_pet.views.NavigationBarActivity

class SignIn : Fragment() {

    private lateinit var  auth : FirebaseAuth

    private lateinit var database: DatabaseReference
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser

        if( auth.currentUser?.isEmailVerified == true){
            if(currentUser != null){
                var intent = Intent(activity, NavigationBarActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        binding.signUpBottom.setOnClickListener {
            var action = SignInDirections.actionSignIn2ToSignUp2()
            Navigation.findNavController(view).navigate(action)
        }
        binding.signUpTop.setOnClickListener {
            var action = SignInDirections.actionSignIn2ToSignUp2()
            Navigation.findNavController(view).navigate(action)
        }
        binding.signIn.setOnClickListener {
            signIn(it)
        }
        binding.forgotPassword.setOnClickListener{
            resetPassword(it)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun signIn(view: View) {

        var userMail = binding.loginEmailText.text.toString()
        var userPassword = binding.loginPasswordText.text.toString()


        activity?.let {
            auth.signInWithEmailAndPassword(userMail, userPassword)
                .addOnCompleteListener(it) { task ->

                    if (task.isSuccessful) {
                        val verification = auth.currentUser?.isEmailVerified
                        if (verification==true){
                            val user = auth.currentUser
                            Toast.makeText(activity,"Hosgeldin ${user?.email.toString()}", Toast.LENGTH_LONG).show()
                            var intent = Intent(activity, NavigationBarActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        }else{
                            Toast.makeText(context,"Please verify your email.",Toast.LENGTH_LONG).show()
                        }


                    } else {

                        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()

                    }
                }.addOnFailureListener { exception->
                    Toast.makeText(activity,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }


    }
    fun resetPassword(view: View){
        val emailAddress = binding.loginEmailText.text.toString()
        if (emailAddress.length>0) {

            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, "Message sent", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(activity, "Please enter a valid email", Toast.LENGTH_LONG).show()
                }



        }else{
            Toast.makeText(activity, "Please enter a email", Toast.LENGTH_LONG).show()
        }

    }

}
