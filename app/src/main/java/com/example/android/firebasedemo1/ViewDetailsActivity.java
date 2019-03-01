package com.example.android.firebasedemo1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    //add Firebase Database stuff
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseListener;
    private DatabaseReference databaseReference;
    private String userID;
    TextView userDetailsTextView;
    Button buttonUpdateDetails,buttonLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        userDetailsTextView = findViewById(R.id.user_details);

        firebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.v("ViewDetailsActivity", "onAuthStateChanged:signed_in:" + user.getUid());
                toastMessage("Successfully signed in with: " + user.getEmail());
            } else {
                // User is signed out
                Log.d("ViewDetailsActivity", "onAuthStateChanged:signed_out");
                toastMessage("Successfully signed out.");
            }
                // ...
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonUpdateDetails = findViewById(R.id.update_user_details);
        buttonLogout = findViewById(R.id.logout1);

        buttonUpdateDetails.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        /*{
            @Override
            public void onClick(View view) {

            }
        });*/
    }


    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            UserInformation uInfo = new UserInformation();
            uInfo = ds.child(userID).getValue(UserInformation.class);

            if (uInfo != null) {
                //display all the information
                Log.v("ViewDetailsActivity", "showData: name: " + uInfo.getName());
                Log.v("ViewDetailsActivity", "showData: address: " + uInfo.getAddress());

                String details = "Full Name: " + uInfo.getName() + "\nAddress: " + uInfo.getAddress();
                userDetailsTextView.setText(details);
                Log.v("ViewDetailsActivity", details);
                return;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdateDetails){
            //LoginActivity
            finish(); // to close current activity
            startActivity(new Intent(this, ProfileActivity.class));

        }else if(v == buttonLogout){
            firebaseAuth.signOut();
            toastMessage("Signing Out...");
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }


}
