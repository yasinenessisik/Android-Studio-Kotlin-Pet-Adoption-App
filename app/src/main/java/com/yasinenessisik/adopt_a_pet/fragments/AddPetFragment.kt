package com.yasinenessisik.adopt_a_pet.fragments

import SearchFragmentAdapter
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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.databinding.FragmentMyHomeBinding

class AddPetFragment : Fragment() {

    lateinit var img_dog: ImageView;
    lateinit var img_cat: ImageView;
    lateinit var img_rabbit: ImageView;
    lateinit var img_fish: ImageView;
    lateinit var img_bird: ImageView;
    lateinit var img_other: ImageView;
    lateinit var recyclerView: RecyclerView
    var dog_selected =false
    var cat_selected =false
    var fish_selected=false
    var rabbit_selected=false
    var bird_selected=false
    var other_selected=false
    var anything_selected=false
    lateinit var img_down_arrow: ImageButton
    var is_recyclerVisible=false
    private lateinit var recyclerViewAdapter: SearchFragmentAdapter


    var pickedImage: Uri? = null
    var pickedBitmap: Bitmap? = null

    private lateinit var auth : FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseFirestore

    private lateinit var binding: FragmentAddPetBinding

    private lateinit var CurrentPetType:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View
        rootView=inflater.inflate(R.layout.fragment_add_pet, container, false);
        binding = FragmentAddPetBinding.bind(rootView)


        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val items = listOf("")
        recyclerViewAdapter = SearchFragmentAdapter(items)
        recyclerView=rootView.findViewById(R.id.sf_recyclerview)
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        img_down_arrow=rootView.findViewById(R.id.sf_down_arrow)
        recyclerView.visibility=View.GONE


        recyclerView.adapter=recyclerViewAdapter

        img_down_arrow.setOnClickListener(View.OnClickListener {

            setRecyclerState()

        })




        img_dog=rootView.findViewById<ImageView>(R.id.img_dog)

        img_dog.setOnClickListener(View.OnClickListener {
            changeVisibility(0)

        })
        img_cat=rootView.findViewById<ImageView>(R.id.img_cat)
        img_cat.setOnClickListener(View.OnClickListener {
            changeVisibility(1)


        })
        img_rabbit=rootView.findViewById<ImageView>(R.id.img_rabbit)
        img_rabbit.setOnClickListener(View.OnClickListener {

            changeVisibility(2)

        })
        img_fish=rootView.findViewById<ImageView>(R.id.img_fish)
        img_fish.setOnClickListener(View.OnClickListener {
            changeVisibility(3)

        })

        img_bird=rootView.findViewById<ImageView>(R.id.img_bird)
        img_bird.setOnClickListener(View.OnClickListener {
            changeVisibility(4)


        })
        img_other=rootView.findViewById<ImageView>(R.id.img_other)
        img_other.setOnClickListener(View.OnClickListener {

            changeVisibility(5)

        })
        var imageView = rootView.findViewById<ImageView>(R.id.imageView)
        imageView.setOnClickListener {
            pickImage(it)
        }

        var sendbutton = rootView.findViewById<Button>(R.id.sf_btn_submit)
        sendbutton.setOnClickListener {
            if(checkFields() && checkAnimal()){
                sharePet(it)
            }
        }



        return rootView;
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

                    var petGender =""
                    if(binding.radioButton.isChecked){
                        petGender = "Male"
                    }else if(binding.radioButton2.isChecked){
                        petGender = "Female"
                    }else if(binding.radioButton3.isChecked){
                        petGender = "Does not matter"
                    }
                    var petAge = ""
                    if(binding.sfEtMinAge.isChecked){
                        petAge = "Bayb"
                    }else if(binding.sfEtMaxAge.isChecked){
                        petAge = "Adult"
                    }
                    val petCity = binding.sfEtCity.text.toString()
                    val petDistrict = binding.sfEtDistrict.text.toString()
                    val petName = binding.sfEtName.text.toString()
                    val petExplanation = binding.sfEtExplanation.text.toString()


                    val petSpecies = getSelectedSpecies()
                    val petBreed = getSelectedBreed()



                    var petUrgency = 0






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
    fun getSelectedBreed(): String{
        var booleanArray = recyclerViewAdapter.getCheckedItems()
        Log.d("mymessage", "setRecyclerData: index is $id")
        if(dog_selected){
            var data= listOf("Kangal","Anadolu Çoban Köpeği","Pitbull","Rotweiller","Dogo argentino","Husskel")
            for (i in booleanArray.indices) {
                if (booleanArray[i]) {
                    Log.d("Animal Breed","Selected option: ${data[i]}, Index: $i")
                    println()
                    return data[i];
                }
            }

        }
        else if(cat_selected){
            var data= listOf("Sokak Kedisi","Ragdoll","Exotic","Persian","British Shorthair","Devon Rex")
            for (i in booleanArray.indices) {
                if (booleanArray[i]) {
                    Log.d("Animal Breed","Selected option: ${data[i]}, Index: $i")
                    return data[i];
                }
            }
        }
        else if(rabbit_selected){
            var data= listOf("Cüce","Angora","Beveren","Havana","Himalaya","Argente")
            for (i in booleanArray.indices) {
                if (booleanArray[i]) {
                    Log.d("Animal Breed","Selected option: ${data[i]}, Index: $i")
                    return data[i];
                }
            }
        }
        else if(fish_selected){
            var data= listOf("Beta","Barbus","Zebra Balığı","Ancistrus","Siyah Tetra","Moli","Lepistesler")
            for (i in booleanArray.indices) {
                if (booleanArray[i]) {
                    Log.d("Animal Breed","Selected option: ${data[i]}, Index: $i")
                    return data[i];
                }
            }
        }
        else if(bird_selected){
            var data= listOf("Muhabbet Kuşu","Kanarya","Saka","Paraket","Papağan","Bülbül")
            for (i in booleanArray.indices) {
                if (booleanArray[i]) {
                    Log.d("Animal Breed","Selected option: ${data[i]}, Index: $i")
                    return data[i];
                }
            }
        }

        else {
            var data= listOf("All")
            return "All"
        }

        return ""
    }
    fun getSelectedSpecies(): String {

        if (dog_selected) {
            return "Dog"
        } else if (cat_selected) {
            return "Cat"
        } else if (fish_selected) {
            return "Fish"
        } else if (rabbit_selected) {
            return "Rabbit"
        } else if (bird_selected) {
            return "Bird"
        } else if (other_selected) {
            return "Other"
        }

        return "No option selected"
    }


    private fun checkAnimal(): Boolean {
        val allFalse = !dog_selected && !cat_selected && !fish_selected && !rabbit_selected && !bird_selected && !other_selected && !anything_selected

        if (dog_selected) {
            Log.d("AnimalSelected", "Dog is selected")
        } else if (cat_selected) {
            Log.d("AnimalSelected", "Cat is selected")
        } else if (fish_selected) {
            Log.d("AnimalSelected", "Fish is selected")
        } else if (rabbit_selected) {
            Log.d("AnimalSelected", "Rabbit is selected")
        } else if (bird_selected) {
            Log.d("AnimalSelected", "Bird is selected")
        } else if (other_selected) {
            Log.d("AnimalSelected", "Other animal is selected")
        } else {
            Log.d("AnimalSelected", "No animal is selected")
        }

        if (allFalse) {
            Log.d("VariableCheck", "All variables are false")
            Toast.makeText(context,"You need to check one animal type",Toast.LENGTH_SHORT).show()
            return false
        } else {
            Log.d("VariableCheck", "At least one variable is true")
            return true
        }
    }
    private fun checkFields(): Boolean {
        var isEmpty = binding.sfEtCity.text.isNullOrEmpty() || binding.sfEtDistrict.text.isNullOrEmpty() ||  binding.sfEtExplanation.text.isNullOrEmpty() || binding.sfEtName.text.isNullOrEmpty()
        if (isEmpty) {
            Log.d("EmptyField", "At least one field is empty")
            Toast.makeText(context,"Please fill all the fields.",Toast.LENGTH_SHORT).show()
            if (binding.sfEtCity.text.isNullOrEmpty()) {
                Log.d("EmptyField", "City field is empty")
            }
            if ( binding.sfEtDistrict.text.isNullOrEmpty()) {
                Log.d("EmptyField", "District field is empty")
            }
            if (binding.sfEtExplanation.text.isNullOrEmpty()) {
                Log.d("EmptyField", "Explanation field is empty")
            }
            if (binding.sfEtName.text.isNullOrEmpty()) {
                Log.d("EmptyField", "Pet Name field is empty")
            }
            return isEmpty
        }
        return true

    }
    private fun changeVisibility(id:Int) {
        if(id==0){
            //for dog
            if(dog_selected){
                img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_dog.setImageResource(R.drawable.dog)
                dog_selected=false
                anything_selected=false


            }
            else{
                img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_dog.setImageResource(R.drawable.white_dog)
                if (cat_selected){
                    img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_cat.setImageResource(R.drawable.cat)
                    cat_selected=false
                }
                if (rabbit_selected){
                    img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_rabbit.setImageResource(R.drawable.rabbit)
                    rabbit_selected=false
                }
                if (fish_selected){
                    img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_fish.setImageResource(R.drawable.fish)
                    fish_selected=false
                }
                if (bird_selected){
                    img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_bird.setImageResource(R.drawable.bird)
                    bird_selected=false
                }
                if (other_selected){
                    img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_other.setImageResource(R.drawable.other)
                    other_selected=false
                }
                dog_selected=true
                anything_selected=true
            }

        }
        else if(id==1){
            //for cat
            if(cat_selected){
                img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_cat.setImageResource(R.drawable.cat)
                cat_selected=false
                anything_selected=false


            }
            else{
                img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_cat.setImageResource(R.drawable.white_cat)
                if (dog_selected){
                    img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_dog.setImageResource(R.drawable.dog)
                    dog_selected=false
                }
                if (rabbit_selected){
                    img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_rabbit.setImageResource(R.drawable.rabbit)
                    rabbit_selected=false
                }
                if (fish_selected){
                    img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_fish.setImageResource(R.drawable.fish)
                    fish_selected=false
                }
                if (bird_selected){
                    img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_bird.setImageResource(R.drawable.bird)
                    bird_selected=false
                }
                if (other_selected){
                    img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_other.setImageResource(R.drawable.other)
                    other_selected=false
                }
                cat_selected=true
                anything_selected=true
            }

        }
        else if(id==2){//for rabbit
            //for rabbit
            if(rabbit_selected){
                img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_rabbit.setImageResource(R.drawable.rabbit)
                rabbit_selected=false
                anything_selected=false

            }
            else{
                img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_rabbit.setImageResource(R.drawable.white_rabbit)
                if (cat_selected){
                    img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_cat.setImageResource(R.drawable.cat)
                    cat_selected=false
                }
                if (dog_selected){
                    img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_dog.setImageResource(R.drawable.dog)
                    dog_selected=false
                }
                if (fish_selected){
                    img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_fish.setImageResource(R.drawable.fish)
                    fish_selected=false
                }
                if (bird_selected){
                    img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_bird.setImageResource(R.drawable.bird)
                    bird_selected=false
                }
                if (other_selected){
                    img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_other.setImageResource(R.drawable.other)
                    other_selected=false
                }

                rabbit_selected=true
                anything_selected=true
            }

        }
        else if (id==3){
            //for fish
            if(fish_selected){
                img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_fish.setImageResource(R.drawable.fish)
                fish_selected=false
                anything_selected=false

            }
            else{
                img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_fish.setImageResource(R.drawable.white_fish)
                if (cat_selected){
                    img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_cat.setImageResource(R.drawable.cat)
                    cat_selected=false
                }
                if (rabbit_selected){
                    img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_rabbit.setImageResource(R.drawable.rabbit)
                    rabbit_selected=false
                }
                if (dog_selected){
                    img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_dog.setImageResource(R.drawable.dog)
                    dog_selected=false
                }
                if (bird_selected){
                    img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_bird.setImageResource(R.drawable.bird)
                    bird_selected=false
                }
                if (other_selected){
                    img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_other.setImageResource(R.drawable.other)
                    other_selected=false
                }

                fish_selected=true
                anything_selected=true
            }

        }
        else if (id==4){
            //for bird
            if(bird_selected){
                img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_bird.setImageResource(R.drawable.bird)
                bird_selected=false
                anything_selected=false

            }
            else{
                img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_bird.setImageResource(R.drawable.white_bird)
                if (cat_selected){
                    img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_cat.setImageResource(R.drawable.cat)
                    cat_selected=false
                }
                if (rabbit_selected){
                    img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_rabbit.setImageResource(R.drawable.rabbit)
                    rabbit_selected=false
                }
                if (fish_selected){
                    img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_fish.setImageResource(R.drawable.fish)
                    fish_selected=false
                }
                if (dog_selected){
                    img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_dog.setImageResource(R.drawable.dog)
                    bird_selected=false
                }
                if (other_selected){
                    img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_other.setImageResource(R.drawable.other)
                    other_selected=false
                }

                bird_selected=true
                anything_selected=true
            }

        }
        else {
            //for other
            if(other_selected){
                img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_other.setImageResource(R.drawable.other)
                other_selected=false
                anything_selected=false

            }
            else{
                img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_other.setImageResource(R.drawable.white_other)
                if (cat_selected){
                    img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_cat.setImageResource(R.drawable.cat)
                    cat_selected=false
                }
                if (rabbit_selected){
                    img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_rabbit.setImageResource(R.drawable.rabbit)
                    rabbit_selected=false
                }
                if (fish_selected){
                    img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_fish.setImageResource(R.drawable.fish)
                    fish_selected=false
                }
                if (bird_selected){
                    img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_bird.setImageResource(R.drawable.bird)
                    bird_selected=false
                }
                if (dog_selected){
                    img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_dog.setImageResource(R.drawable.dog)
                    dog_selected=false
                }

                other_selected=true
                anything_selected=true
            }

        }
        resetRecyclerState()
        setRecyclerData(id)

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

    fun pickImage(view: View) {
        activity?.let {
            if(ContextCompat.checkSelfPermission(it.applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
            else{
                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
                            pickedBitmap=
                                MediaStore.Images.Media.getBitmap(it.contentResolver,pickedImage)
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


    private fun setRecyclerData(id:Int) {
        Log.d("mymessage", "setRecyclerData: index is $id")
        if(id==0){
            var data= listOf("Kangal","Anadolu Çoban Köpeği","Pitbull","Rotweiller","Dogo argentino","Husskel")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()


        }
        else if(id==1){
            var data= listOf("Sokak Kedisi","Ragdoll","Exotic","Persian","British Shorthair","Devon Rex")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
        }
        else if(id==2){
            var data= listOf("Cüce","Angora","Beveren","Havana","Himalaya","Argente")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
        }
        else if(id==3){
            var data= listOf("Beta","Barbus","Zebra Balığı","Ancistrus","Siyah Tetra","Moli","Lepistesler")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
        }
        else if(id==4){

            var data= listOf("Muhabbet Kuşu","Kanarya","Saka","Paraket","Papağan","Bülbül")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter


            recyclerViewAdapter.notifyDataSetChanged()
        }

        else {
            var data= listOf("All")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
        }

    }


    private  fun setRecyclerState(){
        if(anything_selected){
            if(is_recyclerVisible){
                recyclerView.visibility=View.GONE
                img_down_arrow.setImageResource(R.drawable.down_arrow)
                img_down_arrow.setBackgroundResource(R.drawable.down_arrow)
                is_recyclerVisible=false



            }
            else{
                recyclerView.visibility=View.VISIBLE
                img_down_arrow.setImageResource(R.drawable.up_arrow)
                img_down_arrow.setBackgroundResource(R.drawable.up_arrow)
                is_recyclerVisible=true
            }
        }
        else{

            recyclerView.visibility=View.GONE
            img_down_arrow.setImageResource(R.drawable.down_arrow)
            img_down_arrow.setBackgroundResource(R.drawable.down_arrow)
            is_recyclerVisible=false

        }
    }
    private  fun resetRecyclerState(){
        recyclerView.visibility=View.GONE
        img_down_arrow.setImageResource(R.drawable.down_arrow)
        img_down_arrow.setBackgroundResource(R.drawable.down_arrow)
        is_recyclerVisible=false
    }
    fun resizeBitmapT(width : Int, height : Int): Boolean {
        val maximumBoyut = 300
        var width = width
        var height = height
        val bitmapOrani : Double = width.toDouble() / height.toDouble()
        return bitmapOrani > 1

    }



}