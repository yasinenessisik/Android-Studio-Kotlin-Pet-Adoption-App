package com.yasinenessisik.adopt_a_pet.views

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.yasinenessisik.adopt_a_pet.adapters.ChatAdapter
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
        database = FirebaseDatabase.getInstance().getReference()
        val intent = getIntent()
        val email = intent.getStringExtra("email")
        val receiverUid = intent.getStringExtra("uid")

        if (receiverUid != null) {
            database.child("user").child(receiverUid).child("downloadUrl").get()
                .addOnSuccessListener {
                    Picasso.get().load(it.value.toString()).into(binding.profileImage)
                }
        }
        if (receiverUid != null) {
            database.child("user").child(receiverUid).child("nickname").get()
                .addOnSuccessListener {
                    binding.userNickname.setText(it.value.toString())
                }
        }


        val senderUid = FirebaseAuth.getInstance().currentUser?.uid


        senderRoom = receiverUid+senderUid
        receiverRoom = senderUid+receiverUid



        messageBox = binding.messageBox
        sendButton = binding.sendChat
        recyclerView = binding.recyclerView

        binding.userMail.setText(email)
        messageList = ArrayList<Message>()
        chatAdapter = ChatAdapter(messageList)
        recyclerView.layoutManager=LinearLayoutManager(applicationContext)
        recyclerView.adapter = chatAdapter

        binding.back.setOnClickListener {
            onBackPressed()
        }



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

            sendMessage()

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

    fun sendMessage(){
        val message = messageBox.text.toString()

        if (!message.equals("")) {
            val messageObject = Message(message, FirebaseAuth.getInstance().currentUser?.uid)

            database =
                FirebaseDatabase.getInstance("https://adopt-a-pet-f6709-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference()
            database.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                database.child("chats").child(receiverRoom!!).child("messages").push()
                    .setValue(messageObject)
            }
            messageBox.setText("")
        }
    }



}