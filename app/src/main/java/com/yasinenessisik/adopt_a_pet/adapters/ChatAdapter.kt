package com.yasinenessisik.adopt_a_pet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.yasinenessisik.adopt_a_pet.R
import com.yasinenessisik.adopt_a_pet.databinding.ReceiverLayoutBinding
import com.yasinenessisik.adopt_a_pet.databinding.SendLayoutBinding
import com.yasinenessisik.adopt_a_pet.model.Message

class ChatAdapter(val messageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var bindingS: SendLayoutBinding
    private lateinit var bindingR: ReceiverLayoutBinding
    val ITEM_RECEIVER = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.receiver_layout,parent,false)
            bindingR = ReceiverLayoutBinding.bind(view)
            return ReceiveViewHolder(view)

        }else{
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.send_layout,parent,false)
            bindingS = SendLayoutBinding.bind(view)
            return SendViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage=messageList[position]
        holder.setIsRecyclable(false);
        if(holder.javaClass == SendViewHolder::class.java){

            val viewHolder = holder as SendViewHolder
            bindingS.sentMessage.text = currentMessage.message

        }else{
            val viewHolder = holder as ReceiveViewHolder
            bindingR.receiveMessage.text = currentMessage.message
        }
    }
    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){

            return ITEM_SENT
        }else{

            return ITEM_RECEIVER
        }
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class SendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sent_message)
    }
    class ReceiveViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val recieveMessage = itemView.findViewById<TextView>(R.id.receive_message)
    }


}