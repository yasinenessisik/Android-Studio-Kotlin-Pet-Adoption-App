package com.yasinenessisik.adopt_a_pet.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.yasinenessisik.adopt_a_pet.adapters.ChatAdapter
import com.yasinenessisik.adopt_a_pet.adapters.HomeReyclerAdapter
import com.yasinenessisik.adopt_a_pet.databinding.ActivityChatBinding
import com.yasinenessisik.adopt_a_pet.model.Message

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var recyclerView: RecyclerView

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var messageList: ArrayList<Message>

    private lateinit var database: DatabaseReference

    var receiverRoom: String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_chat)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = getIntent()
        val email = intent.getStringExtra("email")
        val receiverUid = intent.getStringExtra("uid")



        val senderUid = FirebaseAuth.getInstance().currentUser?.uid


        senderRoom = receiverUid+senderUid
        receiverRoom = senderUid+receiverUid



        messageBox = binding.messageBox
        sendButton = binding.sendChat
        recyclerView = binding.recyclerView

        messageList = ArrayList<Message>()
        chatAdapter = ChatAdapter(messageList)
        recyclerView.layoutManager=LinearLayoutManager(applicationContext)
        recyclerView.adapter = chatAdapter




        database = FirebaseDatabase.getInstance().getReference()
        database.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()

                for(postSnapshot in snapshot.children){

                    val message = postSnapshot.getValue(Message::class.java)

                    messageList.add(message!!)

                }
                if (messageList.size>0) {
                    recyclerView.smoothScrollToPosition(messageList.size - 1);
                }
                chatAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {



            }

        })

        binding.sendChat.setOnClickListener{
            val message = messageBox.text.toString()
            val messageObject = Message(message, FirebaseAuth.getInstance().currentUser?.uid)

            database = FirebaseDatabase.getInstance("https://adopt-a-pet-f6709-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
            println(database)
            database.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                database.child("chats").child(receiverRoom!!).child("messages").push().setValue(messageObject)
            }
            messageBox.setText("")


        }

    }

    override fun onBackPressed() {
        if ( getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
            finish()
            return;
        }
        super.onBackPressed();
    }


}