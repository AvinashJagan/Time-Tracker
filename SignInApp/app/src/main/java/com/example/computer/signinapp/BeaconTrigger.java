package com.example.computer.signinapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Computer on 9/21/2017.
 */

public class BeaconTrigger extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,childref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main2);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Bundle bundle = getIntent().getExtras();
        String trigger = bundle.getString("trigger");
        if(trigger!=null){
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Location loc=new Location("B9407F30-F5F8-466E-AFF9-25556B57FE6D","Near",currentuser.getUid());
            databaseReference.child("USER_LOCATION").child(currentuser.getUid()).setValue(loc);
            trigger=null;
            Toast.makeText(getApplicationContext(),"Beacon Triggered",Toast.LENGTH_LONG).show();

        }


        Intent notifyIntent = new Intent(this,signinActivity.class);
        startActivity(notifyIntent);

    }
}
