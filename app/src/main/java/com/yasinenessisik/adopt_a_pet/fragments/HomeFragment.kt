package com.yasinenessisik.adopt_a_pet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.adapters.HomeReyclerAdapter
import com.yasinenessisik.adopt_a_pet.databinding.FragmentHomeBinding
import com.yasinenessisik.adopt_a_pet.model.Post
import java.security.Timestamp

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
        database.collection("Post").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
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
                            println(docId)

                        }
                        recyclerViewAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }

}