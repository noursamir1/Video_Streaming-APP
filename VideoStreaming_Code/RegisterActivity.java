package com.example.videostreamingapp;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText fullname;
    EditText username;
    EditText email;
    EditText password;
    EditText confirmpassword;
    Button register;
    Button login;
    ProgressDialog progress;
    ArrayList<user> clients;
    FirebaseAuth firebaseauth;
    DatabaseReference firebasedatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        fullname=(EditText)findViewById(R.id.fullname_editText);
        username=(EditText)findViewById(R.id.username_editText);
        email=(EditText)findViewById(R.id.email2_editText);
        password=(EditText)findViewById(R.id.pass_editText);
        confirmpassword=(EditText)findViewById(R.id.con_editText);
        register=(Button)findViewById(R.id.registerbutton);
        login=(Button)findViewById(R.id.loginbutton);
        progress = new ProgressDialog(this);
        clients = new ArrayList<user>();
        firebaseauth= FirebaseAuth.getInstance();
        firebasedatabase= FirebaseDatabase.getInstance("https://multimedia-streaming-3ab5e-default-rtdb.firebaseio.com/").getReference("Users");
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }
    public void RegisterUser() {
        String full = fullname.getText().toString();
        String user = username.getText().toString();
        String Email = email.getText().toString();
        String pass = password.getText().toString();
        String confirm = confirmpassword.getText().toString();
        if(TextUtils.isEmpty(full)) {
            Toast.makeText(this,"Enter Fullname",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(user)) {
            Toast.makeText(this,"Enter Username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Email)) {
            Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)) {
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(confirm)) {
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pass.equals(confirm)) {
            Toast.makeText(this,"Enter Password Correctly",Toast.LENGTH_SHORT).show();
            return;
        }
        boolean flag1=false;
        boolean flag2=false;
        for(int i=0;i<clients.size();i++) {
            user current = clients.get(i);
            if(user.equals(current.getUsername())) {
                flag1=true;
                break;
            }
            if(Email.equals(current.getEmail())) {
                flag2=true;
                break;
            }
        }
        if(flag1) {
            Toast.makeText(this,"Username is already used",Toast.LENGTH_SHORT).show();
            return;
        }
        if(flag2) {
            Toast.makeText(this,"Email Address is already used",Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setMessage("Registering User...");
        progress.show();
        String Id = firebasedatabase.push().getKey().toString();
        user current = new user(Id,user,Email);
        firebasedatabase.child(Id).setValue(current);
        firebaseauth.createUserWithEmailAndPassword(Email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progress.hide();
                    Toast.makeText(RegisterActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    progress.hide();
                    task.getException().getMessage().toString();
                    Toast.makeText(RegisterActivity.this,"Could not Register successfully ",Toast.LENGTH_LONG).show();
                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
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
        if(view==register) {
            RegisterUser();
        }
        if(view==login) {
            finish();
        }
    }
}