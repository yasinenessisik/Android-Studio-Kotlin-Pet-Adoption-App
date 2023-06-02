package com.yasinenessisik.adopt_a_pet.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.databinding.FragmentPetInfoFrafmentBinding
import com.yasinenessisik.adopt_a_pet.model.Post
import com.yasinenessisik.adopt_a_pet.views.ChatActivity


class PetInfoFragment : Fragment() {

    private lateinit var binding: FragmentPetInfoFrafmentBinding

    private lateinit var db : FirebaseFirestore

    private lateinit var postobje: Post

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

                postobje = PetInfoFragmentArgs.fromBundle(it).Post

                Picasso.get().load(postobje.imageurl).into(binding.imageView3)

                binding.petName.text = postobje.petname
                binding.petBreed.text = postobje.petbreed
                binding.petSpecies.text = postobje.petspecies
                binding.petAge.text = postobje.petage
                binding.petCity.text = postobje.petcity
                binding.petDistrict.text = postobje.petdistrict
                binding.petGender.text = postobje.petgender
                binding.petExplanation.text = postobje.petexplanation
                binding.petUrgency.text = postobje.peturgency.toString()



                var postid  = postobje.docId

                val cameInfo = PetInfoFragmentArgs.fromBundle(it).camefromhome

                if(cameInfo.equals("camefromhome")){
                    binding.delete.visibility = View.INVISIBLE
                    if(FirebaseAuth.getInstance().currentUser?.uid != postobje.useruid) {
                        binding.message.visibility = View.VISIBLE
                    }
                }else {
                    binding.delete.visibility = View.VISIBLE
                    binding.message.visibility = View.INVISIBLE
                }
                binding.delete.setOnClickListener {
                    if (postid != null) {
                        db.collection("Post").document(postid)
                            .delete()
                            .addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                                var action = PetInfoFragmentDirections.actionPetInfoFragmentToMyHomeFragment()
                                Navigation.findNavController(view).navigate(action)}
                            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                    }

                }
                binding.message.setOnClickListener {
                    val intent = Intent(context, ChatActivity::class.java)



                    intent.putExtra("email",postobje.usermail)
                    intent.putExtra("uid",postobje.useruid)


                    context?.startActivity(intent)
                }



            }


        }

    }