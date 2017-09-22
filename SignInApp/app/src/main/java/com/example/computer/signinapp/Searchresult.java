package com.example.computer.signinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Searchresult extends AppCompatActivity {
    EditText ed1;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,childref;
    ArrayList<User_account> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ed1 = (EditText)findViewById(R.id.searchquery);
        FetchRecords();
    }
    public void FetchRecords() {
        users=new ArrayList<User_account>();

        Toast.makeText(getApplicationContext(),
                "fetching data..",Toast.LENGTH_SHORT).show();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("USER_ACCOUNT");
        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User_account user= postSnapshot.getValue(User_account.class);
                    if(user.getEname().equals(ed1.getText().toString().trim())) {
                        users.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        EmployeeRecordAdaptor EmpAdapter = new EmployeeRecordAdaptor(this, users);
        ListView listView = (ListView) findViewById(R.id.listview_Users);
        listView.setAdapter(EmpAdapter);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
