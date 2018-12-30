package com.example.omar.pushnpull;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class mAdapter extends ArrayAdapter<Items> {
    public mAdapter(@NonNull Context context, @NonNull List<Items> objects) {
        super(context, 0, objects);
    }

Context  context;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_row,parent,false);
        TextView name,price,code;
        Spinner size;
        context=getContext();
        ImageButton edit=convertView.findViewById(R.id.edit_button);
        name=convertView.findViewById(R.id.item_name);
        price=convertView.findViewById(R.id.item_price);
        size=convertView.findViewById(R.id.sizes_spinner);
        code=convertView.findViewById(R.id.item_code);
        final Items item=getItem(position);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminActivity a=new AdminActivity();
                a.UpdateData(item,context);
            }
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,item.getSize());
        size.setAdapter(adapter);
        name.setText(item.getName());
        price.setText(String.valueOf(item.getPrice()));
        code.setText(item.getCode());
        return convertView;
    }

}
