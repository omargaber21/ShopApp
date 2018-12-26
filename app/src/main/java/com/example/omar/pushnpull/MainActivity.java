package com.example.omar.pushnpull;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList<Items> items;
ListView listView;
Button addButton,GoToProductsBtn;
mAdapter adapter;
    Items Current_Item;
    Toolbar toolbar;
    private FirebaseDatabase database;
    private DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=FirebaseDatabase.getInstance();
        mReference=database.getReference().child("items");
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Online Shopping");

        listView=findViewById(R.id.listView);
        addButton=findViewById(R.id.btnAdd);
        ReadData();
        GoToProductsBtn=findViewById(R.id.btnGoToProductActivity);
        GoToProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent products=new Intent(MainActivity.this,ProductsActivity.class);
                startActivity(products);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addActivity =new Intent(MainActivity.this,AddActivity.class);
                startActivity(addActivity);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               Current_Item = (Items) adapterView.getItemAtPosition(i);
               UpdateData(Current_Item);
                return true;
            }
        });
    }


    private void ReadData(){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items=new ArrayList<>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Items item=postSnapshot.getValue(Items.class);
                    items.add(item);
                }
                adapter=new mAdapter(MainActivity.this,items);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void UpdateData(final Items current_Item){

        String items[]={"تعديل المنتج","مسح المنتج"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Item");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    Intent update=new Intent(MainActivity.this,UpdateActivity.class);
                    update.putExtra("key",Current_Item.getId());
                    startActivity(update);


                }
                else{
                 mReference.child(Current_Item.getId()).removeValue();

                }
            }
        });
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this,StartActivity.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
