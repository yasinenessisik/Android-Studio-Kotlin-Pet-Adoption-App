package com.yasinenessisik.adopt_a_pet.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.databinding.RecyclerRowMessageBinding
import com.yasinenessisik.adopt_a_pet.model.User
import com.yasinenessisik.adopt_a_pet.views.ChatActivity


class MessageAdapter(val context: Context, val userlist: ArrayList<User>): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    lateinit var binding: RecyclerRowMessageBinding
    class MessageViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.txtName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row_message,parent,false)
        binding = RecyclerRowMessageBinding.bind(view)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        val currentUser = userlist[position]

        binding.txtName.text = currentUser.mail

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)



            intent.putExtra("email",currentUser.mail)
            intent.putExtra("uid",currentUser.uid)


            context.startActivity(intent)
        }

    }
}