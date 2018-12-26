package com.example.omar.pushnpull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    EditText email_edittext,password_edittext,email_name,email_phone;
    Button register;
    private FirebaseAuth mAuth;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email_edittext= (EditText) findViewById(R.id.register_email);
        password_edittext= (EditText) findViewById(R.id.register_password);
        register= (Button) findViewById(R.id.register_btn);
        mAuth= FirebaseAuth.getInstance();
        email_name= (EditText) findViewById(R.id.register_name);
        email_phone= (EditText) findViewById(R.id.register_phone);
        mReference= FirebaseDatabase.getInstance().getReference();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        register(email_edittext.getText().toString(), password_edittext.getText().toString());
            }
        });
    }
    public void register(String email, String password){
        if(email_edittext.getText().toString().isEmpty()){
            email_edittext.setError("Empty field");
            return;
        }
        if(email_name.getText().toString().isEmpty()){
            email_name.setError("Empty field");
            return;
        }
        if(email_phone.getText().toString().isEmpty()){
            email_phone.setError("Empty field");
            return;
        }
        if(email_name.getText().toString().equals(" ")){
            email_name.setError("Empty field");
            return;
        }
        if(email_edittext.getText().toString().equals(" ")){
            email_edittext.setError("Empty field");
            return;
        }
        if(password_edittext.getText().toString().equals(" ")){
            password_edittext.setError("Empty field");
            return;
        }
        if(password_edittext.getText().toString().isEmpty()){
            password_edittext.setError("Empty field");
            return;
        }
        if(password_edittext.length()<8){
            password_edittext.setError("your password is less than 8 characters");
            return;
        }
        if(email_phone.length()!=11){
            email_phone.setError("your phone number must contains 11 numbers");
            return;
        }


        final ProgressDialog mProgress=new ProgressDialog(this);
        mProgress.setTitle("Registering your account ...");
                   mProgress.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String name = email_name.getText().toString();
                        String phone =email_phone.getText().toString();
                        String email = email_edittext.getText().toString();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Intent i = new Intent(RegisterActivity.this, ProductsActivity.class);
                        startActivity(i);
                        finish();
                        mReference.child("Users").child(user.getUid())
                                .setValue(new Users(name, phone,email,true)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                mProgress.dismiss();
                                }
                            }
                        });

                        Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_LONG).show();

                    } else {
                        mReference.child("Users").orderByChild("email").equalTo(email_edittext.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getValue()!=null){
                                    email_edittext.setError("email is already exists");


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });


    }





}

