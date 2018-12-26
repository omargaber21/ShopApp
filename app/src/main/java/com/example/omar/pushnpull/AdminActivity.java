package com.example.omar.pushnpull;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends Fragment {
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
ArrayList<Items> items;
ListView listView;
Button addButton;
mAdapter adapter;
    Items Current_Item;
    Toolbar toolbar;
    private FirebaseDatabase database;
    private DatabaseReference mReference;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.activity_admin,container,false);
       database=FirebaseDatabase.getInstance();
        mReference=database.getReference().child("items");
        toolbar=v.findViewById(R.id.toolbar);
        listView=v.findViewById(R.id.listView);
        addButton=v.findViewById(R.id.btnAdd);
        ReadData();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addActivity =new Intent(getActivity(),AddActivity.class);
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
        return v;
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
                if(getActivity()!=null){
                adapter=new mAdapter(getContext(),items);
                listView.setAdapter(adapter);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void UpdateData(final Items current_Item){

        String items[]={"تعديل المنتج","مسح المنتج"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Item");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    Intent update=new Intent(getActivity(),UpdateActivity.class);
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


}
