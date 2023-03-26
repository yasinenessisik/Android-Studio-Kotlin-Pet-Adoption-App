package com.yasinenessisik.adopt_a_pet.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.adapters.HomeReyclerAdapter
import com.yasinenessisik.adopt_a_pet.adapters.MyHomeRecyclerAdapter
import com.yasinenessisik.adopt_a_pet.databinding.FragmentAddPetBinding
import com.yasinenessisik.adopt_a_pet.databinding.FragmentHomeBinding
import com.yasinenessisik.adopt_a_pet.databinding.FragmentMyHomeBinding
import com.yasinenessisik.adopt_a_pet.model.Post
import com.yasinenessisik.adopt_a_pet.views.LoginActivity

class MyHomeFragment : Fragment() {
    private lateinit var recyclerViewAdapter: MyHomeRecyclerAdapter

    private lateinit var binding: FragmentMyHomeBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

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
                            val userMail = document.get("usermail") as String
                            val pet = document.get("pet") as String
                            val petRace = document.get("petRace") as String
                            val docid = document.id
                            val indirilenPost =
                                Post(
                                    imageUrl,
                                    userMail,
                                    pet,
                                    petRace,
                                    docid

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