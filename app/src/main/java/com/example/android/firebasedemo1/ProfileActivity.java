package com.example.android.firebasedemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText editTextFullName;
    private EditText editTextAddress;
    private TextView textViewProfile;
    private Button buttonSaveInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //Login Activity
            finish();//close current activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");
        FirebaseUser user = firebaseAuth.getCurrentUser();

        editTextFullName = findViewById(R.id.fullname);
        editTextAddress = findViewById(R.id.address);
        textViewProfile = findViewById(R.id.profile);
        textViewProfile.setText("Welcome "+user.getEmail());
        buttonSaveInfo = findViewById(R.id.saveInfo);
        buttonSaveInfo.setOnClickListener(this);

    }

    private void saveUserInformation(){
        String name = editTextFullName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, address,"20","user");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //databaseReference.child(user.getUid()).setValue(user);

        databaseReference.child(user.getUid()).child("name").setValue(name);
        databaseReference.child(user.getUid()).child("address").setValue(address);
        Toast.makeText(this," Information Saved ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        if(v == buttonSaveInfo){
            //save user info
            saveUserInformation();
            finish();
            startActivity(new Intent(this, ViewDetailsActivity.class));
        }

    }
}
