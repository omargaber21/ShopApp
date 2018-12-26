package com.example.omar.pushnpull;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class mAdapter extends ArrayAdapter<Items> {
    public mAdapter(@NonNull Context context, @NonNull List<Items> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_row,parent,false);
        TextView name,price,size,code,quantity;
        name=convertView.findViewById(R.id.item_name);
        price=convertView.findViewById(R.id.item_price);
        size=convertView.findViewById(R.id.item_size);
        code=convertView.findViewById(R.id.item_code);
        quantity=convertView.findViewById(R.id.item_quantity);
        Items item=getItem(position);
        name.setText(item.getName());
        price.setText(String.valueOf(item.getPrice()));
        code.setText(item.getCode());
        return convertView;
    }
}
