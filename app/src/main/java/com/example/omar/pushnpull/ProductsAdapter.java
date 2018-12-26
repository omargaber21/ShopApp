package com.example.omar.pushnpull;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends ArrayAdapter<Items> {
    public ProductsAdapter(@NonNull Context context,  @NonNull List<Items> objects) {
        super(context, 0, objects);
    }



    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mReference=database.getReference("bag");
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final FloatingActionButton addtoBag;
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.productlayout,parent,false);
        ViewCompat.setLayoutDirection(convertView.findViewById(R.id.product_name), ViewCompat.LAYOUT_DIRECTION_RTL);
        addtoBag=convertView.findViewById(R.id.fab);
        addtoBag.setRippleColor(Color.WHITE);
        TextView name,price,selleremail,size;
        Button increment_quantity,decrement_quantity;
        ImageView imageView;
        name=convertView.findViewById(R.id.product_name);
        price=convertView.findViewById(R.id.product_price);
        imageView=convertView.findViewById(R.id.Item_Image);
        size=convertView.findViewById(R.id.product_size);
        selleremail=convertView.findViewById(R.id.seller_email);
        final TextView quantity = convertView.findViewById(R.id.product_quantity);
        increment_quantity=convertView.findViewById(R.id.increment);
        decrement_quantity=convertView.findViewById(R.id.decrement);
        final Items item=getItem(position);
       /* increment_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setQuantity(item.getQuantity()+1);
                notifyDataSetChanged();
            }
        });
        decrement_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getQuantity()>0){
                item.setQuantity(item.getQuantity()-1);
                notifyDataSetChanged();}
            }
        });*/
        name.setText(item.getName());
        price.setText(String.valueOf(item.getPrice())+" جنيه");
       /* quantity.setText(String.valueOf(item.getQuantity()));*/
        selleremail.setText(item.getSelleremail());
        Picasso.get().load(item.getImageurl()).into(imageView);
       /* addtoBag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(item.getQuantity()>0){
                    addItemToBag(item);
                    }else {
                        Toast.makeText(getContext(), "تأكد من الكمية", Toast.LENGTH_LONG).show();
                    }
                }
            });*/





        return convertView;
    }
    private void addItemToBag(Items item){
       /* if(item.getQuantity()<=0){
            return;
        }*/
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String key =mReference.child(mAuth.getCurrentUser().getUid()).child("items").push().getKey();
        mReference.child(mAuth.getCurrentUser().getUid()).child("items").child(item.getId()).setValue(item);

    }
}
