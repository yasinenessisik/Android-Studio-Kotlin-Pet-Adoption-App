package com.yasinenessisik.adopt_a_pet.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.databinding.FragmentPetInfoFrafmentBinding


class PetInfoFragment : Fragment() {

    private lateinit var binding: FragmentPetInfoFrafmentBinding

    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pet_info_frafment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding = FragmentPetInfoFrafmentBinding.bind(view)

            val view = binding.root

            db = FirebaseFirestore.getInstance()

            arguments?.let {

                binding.petName.text = PetInfoFragmentArgs.fromBundle(it).pet
                binding.petBreed.text = PetInfoFragmentArgs.fromBundle(it).petrace
                Picasso.get().load(PetInfoFragmentArgs.fromBundle(it).imageurl).into(binding.imageView3)
                var postid  = PetInfoFragmentArgs.fromBundle(it).postid

                val cameInfo = PetInfoFragmentArgs.fromBundle(it).camefromhome

                if(cameInfo.equals("camefromhome")){
                    binding.delete.visibility = View.INVISIBLE
                }else {
                    binding.delete.visibility = View.VISIBLE
                }
                binding.delete.setOnClickListener {
                    db.collection("Post").document(postid)
                        .delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!")
                            var action = PetInfoFragmentDirections.actionPetInfoFragmentToMyHomeFragment()
                            Navigation.findNavController(view).navigate(action)}
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

                }



            }


        }

    }