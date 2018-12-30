package com.example.omar.pushnpull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {
TextInputEditText name,price,quantity,size,code;
String imageurl,Selleremail;
Button btnUpdate;
private FirebaseDatabase database=FirebaseDatabase.getInstance();
private DatabaseReference mReference=database.getReference("items");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name=findViewById(R.id.item_name);
        price=findViewById(R.id.item_price);
        btnUpdate=findViewById(R.id.btnUpdate);
        code=findViewById(R.id.item_code);
        ReadData();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item_name=name.getText().toString();
                int item_price= Integer.parseInt(price.getText().toString());
                int item_quantity= Integer.parseInt(quantity.getText().toString());
                ArrayList<String> item_size=new ArrayList<>();
                String item_code=code.getText().toString();
                String key= getIntent().getStringExtra("key");
                Items item=new Items(key,item_name,item_price,item_size,item_code,imageurl,Selleremail);
                mReference.child(key).setValue(item);
                finish();
            }
        });

    }
    private void ReadData(){
        String key= getIntent().getStringExtra("key");
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("items").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Items item=dataSnapshot.getValue(Items.class);
                     name.setText(item.getName());
                     price.setText(String.valueOf(item.getPrice()));
                     code.setText(item.getCode());
                     imageurl=item.getImageurl();
                     Selleremail=item.getSelleremail();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
