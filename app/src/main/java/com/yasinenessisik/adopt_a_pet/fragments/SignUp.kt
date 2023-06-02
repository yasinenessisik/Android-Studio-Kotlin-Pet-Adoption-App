package com.yasinenessisik.adopt_a_pet.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout.Directions
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.yasinenessisik.adopt_a_pet.databinding.FragmentSignUpBinding
import com.yasinenessisik.adopt_a_pet.model.User
import java.util.*
import kotlin.math.sign


class SignUp : Fragment() {
    private lateinit var  auth : FirebaseAuth


    private lateinit var database: DatabaseReference
    private lateinit var storagefb: FirebaseStorage
    var pickedImage: Uri? = null
    var pickedBitmap: Bitmap? = null

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root


        auth = FirebaseAuth.getInstance()

        binding.loginTop.setOnClickListener {

        }
        binding.loginTop.setOnClickListener {

            var action = SignUpDirections.actionSignUp2ToSignIn2()
            Navigation.findNavController(view).navigate(action)

        }
        binding.circleImageView.setOnClickListener {
            pickImage(it)
        }
        binding.signUpButton.setOnClickListener {
            if(
                !(binding.loginPasswordText.length()>0 ||
                        binding.loginEmailText.length()>0 ||
                        binding.loginPasswordTextConfirm.length()>0 ||
                        binding.nickName.length()>0)
            ){
                Toast.makeText(activity,"Please fill all the fields.",Toast.LENGTH_LONG).show()
            }else if(binding.loginPasswordText.length()<=6 || binding.loginPasswordTextConfirm.length()<=6) {
                Toast.makeText(activity,"Enter a stronger password.",Toast.LENGTH_LONG).show()
            }
            else if(!binding.loginPasswordTextConfirm.text.toString().equals(binding.loginPasswordText.text.toString()) ) {
                Toast.makeText(activity,"Passwords do not match.",Toast.LENGTH_LONG).show()
            }else{
                signUp(it)
            }
        }


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun signUp(view: View){

        val uuid = UUID.randomUUID()
        val imageName = "${uuid}.jpg"
        storagefb = FirebaseStorage.getInstance()
        val reference = storagefb.reference

        val imageReference = reference.child("Ppimages").child(imageName)



        if (pickedImage != null) {
            imageReference.putFile(pickedImage!!).addOnSuccessListener { taskSnapshot ->
                val uploadedImageReference =
                    FirebaseStorage.getInstance().reference.child("Ppimages").child(imageName)
                uploadedImageReference.downloadUrl.addOnSuccessListener { uri ->


                    val downloadUrl = uri.toString()
                    var userMail = binding.loginEmailText.text.toString()
                    var userPassword = binding.loginPasswordText.text.toString()
                    var nickname = binding.nickName.text.toString()







                    auth.createUserWithEmailAndPassword(userMail,userPassword).addOnCompleteListener{task->
                        if(task.isSuccessful){
                            auth.currentUser?.sendEmailVerification()?.addOnSuccessListener{
                                addUserToDatabase(userMail,auth.currentUser!!.uid,nickname,downloadUrl)
                                var action = SignUpDirections.actionSignUp2ToSignIn2()
                                Navigation.findNavController(view).navigate(action)
                                Toast.makeText(activity,"Registration is successful, Please verify your mail", Toast.LENGTH_LONG).show()
                            }?.addOnFailureListener {
                                Toast.makeText(activity,it.toString(), Toast.LENGTH_LONG).show()

                            }

                        }

                    }.addOnFailureListener{exception ->
                        Toast.makeText(activity,exception.localizedMessage, Toast.LENGTH_LONG).show()
                    }


                }
            }
        }


    }
    private fun addUserToDatabase(email:String, uid:String,nickname:String,downloadUrl:String){
        database = FirebaseDatabase.getInstance().getReference()
        database.child("user").child(uid).setValue(User(email,uid,nickname,downloadUrl))
    }

    fun pickImage(view: View) {

        if(context?.let { ContextCompat.checkSelfPermission(it,android.Manifest.permission.READ_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
        else{
            val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntent,2)

        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==1){
            if(grantResults.size> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galeriIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeriIntent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==2 && resultCode== Activity.RESULT_OK && data != null){
            pickedImage = data.data
            try {


                context?.let {
                    if(pickedImage != null) {
                        if(Build.VERSION.SDK_INT>=28) {
                            val source = ImageDecoder.createSource(it.contentResolver, pickedImage!!)
                            pickedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.circleImageView.setImageBitmap(resizeBitmap(pickedBitmap!!,300))
                        }
                        else{
                            pickedBitmap=MediaStore.Images.Media.getBitmap(it.contentResolver,pickedImage)
                            binding.circleImageView.setImageBitmap(resizeBitmap(pickedBitmap!!,300))
                        }
                    }
                }


            }catch (e : java.lang.Exception){
                e.printStackTrace()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
    fun resizeBitmap(bitmap: Bitmap, maximumBoyut: Int): Bitmap {
        var width = bitmap.width
        var height = bitmap.height
        val bitmapOrani : Double = width.toDouble() / height.toDouble()
        if(bitmapOrani > 1){
            width = maximumBoyut
            val kisaltilmisHeight = width/bitmapOrani
            height = kisaltilmisHeight.toInt()
        }else{
            height = maximumBoyut
            val kisaltilmisHeight = height/bitmapOrani
            width = kisaltilmisHeight.toInt()
        }
        return Bitmap.createScaledBitmap(bitmap,width,height,true)
    }

}