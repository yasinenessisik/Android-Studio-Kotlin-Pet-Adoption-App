package com.yasinenessisik.adopt_a_pet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.adapters.HomeReyclerAdapter
import com.yasinenessisik.adopt_a_pet.databinding.FragmentHomeBinding
import com.yasinenessisik.adopt_a_pet.model.Post

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerViewAdapter: HomeReyclerAdapter

    var postList = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding = FragmentHomeBinding.bind(view)
        recyclerViewAdapter = HomeReyclerAdapter(postList)
        binding.recyclerView.layoutManager=LinearLayoutManager(context)
        binding.recyclerView.adapter = recyclerViewAdapter

        getData()

    }
    fun getData(){
        database.collection("Post").orderBy("tarih", Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
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
                            val docId = document.id
                            val indirilenPost =
                                Post(
                                    imageUrl,
                                    userMail,
                                    pet,
                                    petRace,
                                    docId

                                )
                            postList.add(indirilenPost)
                            println(docId)

                        }
                        recyclerViewAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }

}