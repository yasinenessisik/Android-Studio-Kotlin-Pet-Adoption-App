package com.yasinenessisik.adopt_a_pet.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.adapters.MyHomeRecyclerAdapter
import com.yasinenessisik.adopt_a_pet.databinding.FragmentMyHomeBinding
import com.yasinenessisik.adopt_a_pet.model.Post
import com.yasinenessisik.adopt_a_pet.views.LoginActivity
import java.security.Timestamp
import java.sql.Time

class MyHomeFragment : Fragment() {
    private lateinit var recyclerViewAdapter: MyHomeRecyclerAdapter

    private lateinit var binding: FragmentMyHomeBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var realtimedb : FirebaseDatabase

    var postList = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentMyHomeBinding.bind(view)
        recyclerViewAdapter = MyHomeRecyclerAdapter(postList)
        binding.recyclerView.layoutManager= LinearLayoutManager(context)
        binding.recyclerView.adapter = recyclerViewAdapter
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        realtimedb= FirebaseDatabase.getInstance()


        auth.currentUser?.let {
            realtimedb.reference.child("user").child(it.uid).child("mail").get()
                .addOnSuccessListener {
                    binding.userMail.setText(it.value.toString())
                }
        }
        auth.currentUser?.let {
            realtimedb.reference.child("user").child(it.uid).child("nickname").get()
                .addOnSuccessListener {
                    binding.userNickname.setText(it.value.toString())
                }
        }

        auth.currentUser?.let { it ->
            realtimedb.reference.child("user").child(it.uid).child("downloadUrl").get()
                .addOnSuccessListener {
                    Picasso.get().load(it.value.toString()).into(binding.profileImage)
                }
        }
        getData()

        binding.logOut.setOnClickListener{
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun getData(){
        database.collection("Post").whereEqualTo("usermail",auth.currentUser?.email).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(activity,exception.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {

                        val documents = snapshot.documents

                        postList.clear()

                        for (document in documents) {

                            val imageUrl = document.get("imageurl") as String
                            val usermail = document.get("usermail") as String
                            val useruid = document.get("useruid") as String
                            val petname = document.get("petname") as String
                            val petspecies = document.get("petspecies") as String
                            val petbreed = document.get("petbreed") as String
                            val petage = document.get("petage") as String
                            val petcity = document.get("petcity") as String
                            val petdistrict = document.get("petdistrict") as String
                            val petgender = document.get("petgender") as String
                            val petexplanation = document.get("petexplanation") as String
                            val peturgency = document.get("peturgency") as Long
                            val docId = document.id






                            val indirilenPost =
                                Post(
                                    imageUrl,
                                    usermail,
                                    useruid,
                                    petname,
                                    petspecies,
                                    petbreed,
                                    petage,
                                    petcity,
                                    petdistrict,
                                    petgender,
                                    petexplanation,
                                    peturgency,
                                    docId

                                )
                            postList.add(indirilenPost)
                            println(postList)

                        }
                        recyclerViewAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }
}