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
import android.provider.MediaStore.Audio.Radio
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.yasinenessisik.adopt_a_pet.databinding.FragmentAddPetBinding
import java.util.*
import androidx.navigation.Navigation
import com.yasinenessisik.adopt_a_pet.R

class AddPetFragment : Fragment() {

    var pickedImage: Uri? = null
    var pickedBitmap: Bitmap? = null

    private lateinit var auth : FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseFirestore

    private lateinit var binding: FragmentAddPetBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_pet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddPetBinding.bind(view)
        val view = binding.root

        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()


        binding.imageView.setOnClickListener{
            pickImage(it)
        }

        binding.petSendButton.setOnClickListener{
            sharePet(it)
        }


    }

    fun pickImage(view: View) {
        activity?.let {
            if(ContextCompat.checkSelfPermission(it.applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
            else{
                val galeriIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent,2)

            }

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
                            binding.imageView.setImageBitmap(resizeBitmap(pickedBitmap!!,300))
                        }
                        else{
                            pickedBitmap=MediaStore.Images.Media.getBitmap(it.contentResolver,pickedImage)
                            binding.imageView.setImageBitmap(resizeBitmap(pickedBitmap!!,300))
                        }
                    }
                }


            }catch (e : java.lang.Exception){
                e.printStackTrace()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    fun sharePet(view: View){


        //depo işlemleri
        //UUID -> universal unique id
        val uuid = UUID.randomUUID()
        val imageName = "${uuid}.jpg"

        val reference = storage.reference

        val imageReference = reference.child("images").child(imageName)

        if (pickedImage != null) {
            imageReference.putFile(pickedImage!!).addOnSuccessListener { taskSnapshot ->
                val uploadedImageReference = FirebaseStorage.getInstance().reference.child("images").child(imageName)
                uploadedImageReference.downloadUrl.addOnSuccessListener { uri ->

                    // imagein urlsini aliyorsuz
                    val downloadUrl = uri.toString()

                    //yukleyen kullanicilarin bilgileri
                    val userMail = auth.currentUser!!.email.toString()
                    val userUid = auth.currentUser!!.uid

                    //siralama icin
                    val Date = com.google.firebase.Timestamp.now()

                    //layout idleri degiskenlere atiyoruz databasede tutmak icin

                    val petName = binding.petName.text.toString()
                    val petSpecies = binding.petSpecies.text.toString()
                    val petBreed = binding.petBreed.text.toString()
                    val petAge = binding.petAge.text.toString()
                    val petCity = binding.petCity.text.toString()
                    val petDistrict = binding.petDistrict.text.toString()
                    val petExplanation = binding.petExplanation.text.toString()


                    var petGender ="Male"
                    if(binding.radioF.isChecked){
                        petGender = "Female"
                    }


                    var petUrgency = 0
                    if (binding.petUrgency.isChecked){
                        petUrgency = 1
                    }






                    //veritabanı işlemlerini


                    val postHashMap = hashMapOf<String, Any>()

                    postHashMap.put("imageurl",downloadUrl)

                    postHashMap.put("usermail",userMail)
                    postHashMap.put("useruid",userUid)

                    postHashMap.put("date",Date)

                    postHashMap.put("petname",petName)
                    postHashMap.put("petspecies",petSpecies)
                    postHashMap.put("petbreed",petBreed)
                    postHashMap.put("petage",petAge)
                    postHashMap.put("petcity",petCity)
                    postHashMap.put("petdistrict",petDistrict)
                    postHashMap.put("petgender",petGender)
                    postHashMap.put("petexplanation",petExplanation)
                    postHashMap.put("peturgency",petUrgency)



                    database.collection("Post").add(postHashMap).addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            val action = AddPetFragmentDirections.actionAddPetToHomeFragment()
                            Navigation.findNavController(view).navigate(action)
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(context,exception.localizedMessage, Toast.LENGTH_LONG).show()
                    }

                }.addOnFailureListener { exception ->
                    Toast.makeText(context,exception.localizedMessage, Toast.LENGTH_LONG).show()

                }
            }


        }

    }

    fun resizeBitmap(bitmap: Bitmap,maximumBoyut: Int): Bitmap{
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