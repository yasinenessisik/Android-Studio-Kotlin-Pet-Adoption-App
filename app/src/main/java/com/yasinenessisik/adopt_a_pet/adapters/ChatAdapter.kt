package com.yasinenessisik.adopt_a_pet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.databinding.ReceiverBinding
import com.yasinenessisik.adopt_a_pet.databinding.RecyclerRowMessageBinding
import com.yasinenessisik.adopt_a_pet.databinding.SendBinding
import com.yasinenessisik.adopt_a_pet.model.Message

class ChatAdapter(val messageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var bindingS: SendBinding
    private lateinit var bindingR: ReceiverBinding
    val ITEM_RECEIVER = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.receiver,parent,false)
            return ReceiveViewHolder(view)

        }else{
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.send,parent,false)
            return SendViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass == SendViewHolder::class.java){
            val currentMessage=messageList[position]


            val viewHolder = holder as SendViewHolder
            holder.sentMessage.text = currentMessage.message

        }else{
            val currentMessage=messageList[position]


            val viewHolder = holder as ReceiveViewHolder
            holder.recieveMessage.text = currentMessage.message
        }
    }

    class SendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sent_message)
    }
    class ReceiveViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val recieveMessage = itemView.findViewById<TextView>(R.id.receive_message)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            ITEM_RECEIVER
        }
    }

}