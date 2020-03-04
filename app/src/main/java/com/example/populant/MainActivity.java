package com.example.populant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText etname;
    EditText etCity;
    EditText etHouseNumber;


    Button btnView;
    Button btnSignOut;
    Button btnAddUser;
    Button btnDelete;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog PD;

    DatabaseReference databaseUsers;



    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);

        etname = (EditText) findViewById(R.id.name);
        etCity = (EditText) findViewById(R.id.city);
        etHouseNumber = (EditText) findViewById(R.id.houseNumber);
        btnAddUser = (Button) findViewById(R.id.add_user_button);

        btnSignOut = (Button) findViewById(R.id.sign_out_button);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {
                signOut();
            }
        });





    }

    private void addUser(){
        String name  = etname.getText().toString().trim();
        String city  = etCity.getText().toString().trim();
        String houseNumber  = etHouseNumber.getText().toString().trim();

        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(city) || !TextUtils.isEmpty(houseNumber)) {





            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseUsers.push().getKey();

            //creating an Artist Object
            User user = new User(id, name, city, houseNumber);

            //Saving the Artist
            databaseUsers.child(id).setValue(user);

            //setting edittext to blank again
            etname.setText("");
            etCity.setText("");
            etHouseNumber.setText("");


            //displaying a success toast
            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }

    }

    public void signOut() {
        // [START auth_sign_out]
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        // [END auth_sign_out]
    }


    public void view(){

        databaseUsers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println(user.toString());
                System.out.println(dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override    protected void onResume() {
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        super.onResume();
    }
}