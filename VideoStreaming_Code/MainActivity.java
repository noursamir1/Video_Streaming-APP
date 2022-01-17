package com.example.videostreamingapp;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username;
    EditText password;
    Button login;
    Button register;
    ProgressDialog progress;
    FirebaseAuth firebaseauth;
    DatabaseReference firebasedatabase;
    ArrayList<user> clients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(MainActivity.this);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.email_editText);
        password = (EditText)findViewById(R.id.password_editText);
        login = (Button)findViewById(R.id.login_button);
        register = (Button)findViewById(R.id.register_button);
        progress = new ProgressDialog(this);
        clients = new ArrayList<user>();
        firebaseauth= FirebaseAuth.getInstance();
        firebasedatabase= FirebaseDatabase.getInstance("https://multimedia-streaming-3ab5e-default-rtdb.firebaseio.com/").getReference("Users");
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }
    public void LoginUser() {
        final String Username = username.getText().toString();
        String Password = password.getText().toString();
        if(TextUtils.isEmpty(Username)) {
            Toast.makeText(this,"Enter Username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Password)) {
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setMessage("Signing In...");
        //progress.show();
        String Email="";
        boolean flag=false;
        for(int i=0;i<clients.size();i++) {
            user current = clients.get(i);
            if(Username.equals(current.getUsername())) {
                Email=current.getEmail();
                flag=true;
            }
        }
        if(!flag) {
            Toast.makeText(MainActivity.this,"Username is invalid ",Toast.LENGTH_LONG).show();
            return;
        }
        firebaseauth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progress.hide();
                    Toast.makeText(MainActivity.this,"Signed in successfully",Toast.LENGTH_SHORT).show();
                    Intent ListActivity = new Intent(getApplicationContext(),TempActivity.class);
                   /* V.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); */
                    finish();
                    startActivity(ListActivity);
                } else {
                    progress.hide();
                    Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void onStart() {
        super.onStart();
        firebasedatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    user current = snapshot.getValue(user.class);
                    clients.add(current);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onClick(View view) {
        if(view==login) {
            LoginUser();
        }
        if(view==register) {
            Intent registerintent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(registerintent);
        }
    }
}