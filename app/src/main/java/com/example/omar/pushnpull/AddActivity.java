package com.example.omar.pushnpull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
TextInputEditText name,price,quantity,size,code;
Button btnadd;
ImageButton capture_image;
private FirebaseDatabase database=FirebaseDatabase.getInstance();
private static final int REQUEST_IMAGE_CAPTURE = 1;
private DatabaseReference mReference=database.getReference("items");
    private StorageReference mStorageRef;
    String key=mReference.push().getKey();
    String imageurl;
    List<String> item_size=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        name=findViewById(R.id.item_name);
        price=findViewById(R.id.item_price);
        quantity=findViewById(R.id.item_quantity);
        btnadd=findViewById(R.id.btnAdd);
        size=findViewById(R.id.item_size);
        capture_image=findViewById(R.id.image_capture);
        code=findViewById(R.id.item_code);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading your image");
        progressDialog.setCancelable(false);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item_name=name.getText().toString();
                int item_price= Integer.parseInt(price.getText().toString());
                String item_code=code.getText().toString();
                String seller_email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                Items item=new Items(key,item_name,item_price,item_size,item_code,imageurl,seller_email);
                mReference.child(key).setValue(item);
                finish();
            }
        });
        capture_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gallery=new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                if (gallery.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(gallery
                            , REQUEST_IMAGE_CAPTURE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            Uri imageUri=data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(this);
            progressDialog.show();

        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                Uri resultUri =result.getUri();
                final StorageReference filepath=mStorageRef.child("item_image").child(key+".jpg");
                UploadTask uploadTask=filepath.putFile(resultUri);
                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }

                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri downloadUri=task.getResult();
                            if (downloadUri != null) {

                                imageurl = downloadUri.toString();
                             progressDialog.dismiss();
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(imageurl!=null){
            StorageReference photoRef = mStorageRef.child("item_image").child(key+".jpg");
            photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"Image is not uploaded",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                }
            });
        }
        super.onBackPressed();
    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkbox_smallSize:
                if (checked) {item_size.add("Small");
                }
            else{item_size.remove("Small");
                }
                break;

            case R.id.checkbox_mediumSize:
                if (checked){ item_size.add("Medium");
                }
            else{item_size.remove("Medium");
                }
                break;

            case R.id.checkbox_LargeSize:
                if (checked){item_size.add("Large");
                }
                else{ item_size.remove("Large"); }
                break;

            case R.id.checkbox_XlargeSize:
                if (checked){item_size.add("XLarge");
                }
                else{ item_size.remove("XLarge"); }
                break;
            case R.id.checkbox_XXLargeSize:
                if (checked){item_size.add("XXLarge");
                }
                else{ item_size.remove("XXLarge"); }
                break;
            case R.id.checkbox_XXXLargeSize:
                if (checked){item_size.add("XXXLarge");
                }
                else{ item_size.remove("XXXLarge"); }
                break;
            case R.id.checkbox_4XLargeSize:
                if (checked){item_size.add("4XLarge");
                }
                else{ item_size.remove("4XLarge"); }
                break;
        }
    }
}
