package com.yasinenessisik.adopt_a_pet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.databinding.RecyclerRowBinding
import com.yasinenessisik.adopt_a_pet.fragments.HomeFragmentDirections
import com.yasinenessisik.adopt_a_pet.fragments.MyHomeFragmentDirections
import com.yasinenessisik.adopt_a_pet.model.Post

class MyHomeRecyclerAdapter(val postList : ArrayList<Post>) : RecyclerView.Adapter<HomeReyclerAdapter.PostHolder>() {
    private lateinit var binding: RecyclerRowBinding

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeReyclerAdapter.PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        binding = RecyclerRowBinding.bind(view)
        return HomeReyclerAdapter.PostHolder(view)
    }

    override fun onBindViewHolder(holder: HomeReyclerAdapter.PostHolder, position: Int) {
        binding.petName.text = postList[position].pet
        binding.petBreed.text = postList[position].petRace
        binding.userMail.text = postList[position].usermail
        Picasso.get().load(postList[position].imageurl).into(binding.petImage)
        binding.cardView.setOnClickListener{
            var action = MyHomeFragmentDirections.actionMyHomeFragmentToPetInfoFragment("camefrommyhome",postList[position].docId,postList[position].imageurl,postList[position].petRace,postList[position].pet,postList[position].usermail)
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }


}