package com.yasinenessisik.adopt_a_pet.views

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.databinding.ActivityLoginActivityBinding
import com.yasinenessisik.adopt_a_pet.databinding.ActivitySignUpBinding
import com.yasinenessisik.adopt_a_pet.model.User
import java.util.*

class SignUp : AppCompatActivity() {
    private lateinit var  auth : FirebaseAuth

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var database: DatabaseReference
    private lateinit var storagefb: FirebaseStorage
    var pickedImage: Uri? = null
    var pickedBitmap: Bitmap? = null
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

                    // imagein urlsini aliyorsuz

                    val downloadUrl = uri.toString()
                    var userMail = binding.loginEmailText.text.toString()
                    var userPassword = binding.loginPasswordText.text.toString()
                    var nickname = binding.nickName.text.toString()







                    auth.createUserWithEmailAndPassword(userMail,userPassword).addOnCompleteListener{task->
                        if(task.isSuccessful){
                            addUserToDatabase(userMail,auth.currentUser!!.uid,nickname,downloadUrl)
                            val intent = Intent(this, NavigationBarActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }.addOnFailureListener{exception ->
                        Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                    }


                }
            }
        }


    }
    private fun addUserToDatabase(email:String, uid:String,nickname:String,downloadUrl:String){
        database = FirebaseDatabase.getInstance().getReference()
        database.child("user").child(uid).setValue(User(email,uid,nickname,downloadUrl))
    }
    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed();
    }
    fun pickImage(view: View) {

            if(ContextCompat.checkSelfPermission(this.applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
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


                    if(pickedImage != null) {
                        if(Build.VERSION.SDK_INT>=28) {
                            val source = ImageDecoder.createSource(this.contentResolver, pickedImage!!)
                            pickedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.circleImageView.setImageBitmap(resizeBitmap(pickedBitmap!!,300))
                        }
                        else{
                            pickedBitmap=
                                MediaStore.Images.Media.getBitmap(this.contentResolver,pickedImage)
                            binding.circleImageView.setImageBitmap(resizeBitmap(pickedBitmap!!,300))
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