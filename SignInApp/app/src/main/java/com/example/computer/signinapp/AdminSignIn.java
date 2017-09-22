package com.example.computer.signinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminSignIn extends AppCompatActivity {
    TextView uemail,navename;
    Button b1,b2;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,childref;
    String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = firebaseAuth.getCurrentUser();
        uemail = (TextView) findViewById(R.id.textAdminName);

        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("USER_ACCOUNT").child(currentuser.getUid());
        childref.child("ename").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                uemail.setText("Welcome " + value);

                //  navename.setText("Hey " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu_admin, menu);
        return true;
    }

    public void MyAccount(MenuItem item) {
        Intent intent = new Intent(this, MyAccount.class);
        startActivity(intent);
    }

    public void FetchUsers(MenuItem item){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }
}
