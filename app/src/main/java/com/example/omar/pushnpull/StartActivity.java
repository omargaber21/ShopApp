package com.example.omar.pushnpull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class StartActivity extends AppCompatActivity {
TextView register;
EditText emailtext,passwordtext;
Button login;
FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        register= (TextView) findViewById(R.id.register_activity_go);
        emailtext= (EditText) findViewById(R.id.login_email);
        passwordtext= (EditText) findViewById(R.id.login_password);
        login= (Button) findViewById(R.id.login_btn);

       // FirebaseAuth.getInstance().signOut();  // for errors logout


        //on register click
        openRegisterActivity();
        mAuth=FirebaseAuth.getInstance();
        //Login progress
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Login();
            }
        });






    }
    @Override
    protected void onStart() {
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser != null) {
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users user=dataSnapshot.getValue(Users.class);

                    if(user.isAdmin()){

                        Intent seller=new Intent(StartActivity.this,MainActivity.class);
                        startActivity(seller);
                        finish();
                    }else{
                        Intent customer=new Intent(StartActivity.this,ProductsActivity.class);
                        startActivity(customer);
                        finish();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }


        super.onStart();
    }
    private void Login(){
        String email=emailtext.getText().toString();
        String password=passwordtext.getText().toString();
        if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)||email.equals(" ")||password.equals(" ")){
            Toast.makeText(StartActivity.this,"Empty Fields , check username and password", Toast.LENGTH_LONG).show();

        }else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i=new Intent(StartActivity.this,ProductsActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(StartActivity.this,"Login Failed(Wrong Username or Password", Toast.LENGTH_LONG).show();

                            }

                            // ...
                        }
                    });
        }
    }

    private void openRegisterActivity(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}



/*
  if(currentUser != null &&currentUser.getUid().equals("ZP9oYH5rScQNmLcwfHoUK2wZiqA2") ){

                Intent seller=new Intent(StartActivity.this,MainActivity.class);
                startActivity(seller);
                finish();
        }*/