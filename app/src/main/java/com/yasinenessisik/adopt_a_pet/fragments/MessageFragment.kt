package com.yasinenessisik.adopt_a_pet.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.adapters.MessageAdapter
import com.yasinenessisik.adopt_a_pet.databinding.FragmentMessageBinding
import com.yasinenessisik.adopt_a_pet.model.User

class MessageFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference

    private lateinit var binding:FragmentMessageBinding
    private lateinit var recyclerViewAdapter: MessageAdapter

    var userlist = ArrayList<User>()
    var connectedUserList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference()


        binding = FragmentMessageBinding.bind(view)
        recyclerViewAdapter = getActivity()?.let { MessageAdapter(it, userlist) }!!
        binding.recyclerView.layoutManager= LinearLayoutManager(context)
        binding.recyclerView.adapter = recyclerViewAdapter


        database.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(auth.currentUser?.uid != currentUser?.uid){




                            database.child("chats").addValueEventListener(object :ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    var room =auth.currentUser?.uid+""+currentUser?.uid
                                        var room1=currentUser?.uid+""+auth.currentUser?.uid
                                    for (postSnapshot in snapshot.children) {
                                        if (!userlist.contains(currentUser)) {
                                            if (room.equals(postSnapshot.key)) {
                                                userlist.add(currentUser!!)
                                            } else if (room1.equals(postSnapshot.key)) {
                                                userlist.add(currentUser!!)
                                            }
                                        }
                                    }
                                    recyclerViewAdapter.notifyDataSetChanged()
                                }

                                override fun onCancelled(error: DatabaseError) {


                                }


                            })





                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }

}
