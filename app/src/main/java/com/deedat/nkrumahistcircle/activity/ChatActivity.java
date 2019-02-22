package com.deedat.nkrumahistcircle.activity;


import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.deedat.nkrumahistcircle.R;
import com.deedat.nkrumahistcircle.model.ChatMessage;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;


/**

 */
public class ChatActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("messages");
    private FirebaseListAdapter<ChatMessage> adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);
        final EditText input = (EditText) findViewById(R.id.input);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database

                String id = myRef.push().getKey();
                String message=input.getText().toString().trim();
                //money exp = new money(R.drawable.ic_remove_circle, description, datestring, Double.parseDouble(amount));
                //myRef.child(id).setValue(exp);
                ChatMessage cm=new ChatMessage(message,FirebaseAuth.getInstance().getCurrentUser().getEmail());
                myRef.push().setValue(cm) .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed
                                // ...
                            }
                        });
                ;


                // Clear the input
                input.setText("");
            }
        });


        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        //long count=0;

        String current_user;
        current_user=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long  count = dataSnapshot.getChildrenCount();

                Log.d("TAG", "count= " + count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        // myRef.child("messageUser").child(current_user);

//Log.i("myname",current_user);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
       // if(firebaseUser.getId()==uid)
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R.layout.message_self, FirebaseDatabase.getInstance().getReference("messages")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                android.text.format.DateFormat df = new android.text.format.DateFormat();
                // Format the date before showing it
                messageTime.setText(df.format("dd-MM-yyyy (HH:mm)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }


    }









