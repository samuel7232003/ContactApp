package com.example.contactappex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contactappex.databinding.EditContactBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

public class EditContact extends AppCompatActivity {
    private EditContactBinding binding;
    private AppDatabase appDatabase;
    private ContactDao contactDao;
    private ImageDatabase imageDatabase;
    private ImageDao imageDao;
    private Contact contact;
    private int id;
    private int i = 0;
    private String i1 = "null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EditContactBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        Intent received = getIntent();
        if(received!=null){
            id = received.getIntExtra("id", -1);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase = AppDatabase.getInstance(getApplicationContext());
                    contactDao = appDatabase.contactDao();
                    imageDatabase = ImageDatabase.getInstance(getApplicationContext());
                    imageDao = imageDatabase.imageDao();

                    contact = contactDao.getByID(id);
                    binding.etNewName.setText(contact.getName());
                    binding.etNewMobile.setText(contact.getMobile());
                    binding.etNewEmail.setText(contact.getEmail());

                    Image image = imageDao.getByIDContact(id);
                    if(image!=null){
                        byte[] imageBytes = Base64.decode(image.getImg(), Base64.DEFAULT );
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//
                        binding.ivAvatar.setImageBitmap(bitmap);

                    }
                }
            });
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(EditContact.this, DetailContact.class);
                detail.putExtra("id", id);
                startActivity(detail);
            }
        });

        binding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = binding.etNewName.getText().toString();
                String newMobile = binding.etNewMobile.getText().toString();
                String newEmail = binding.etNewEmail.getText().toString();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        contactDao.update(id, newName, newMobile, newEmail);
                        if(!i1.equals("null")){
                            Image i = imageDao.getByIDContact(id);
                            if(i==null){
                                imageDao.insert(new Image(id, i1));
                            }
                            else{
                                imageDao.updateImage(i1, id);
                            }
                        }
                    }
                });
                Intent detail = new Intent(EditContact.this, DetailContact.class);
                detail.putExtra("id", id);
                startActivity(detail);
            }
        });

        binding.ivEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Log.i("photo", "" + intent);
                startActivityForResult(intent, i);
                i = i + 1;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if(resultCode == RESULT_OK){
                    Uri targetUri = data.getData();
                    Bitmap bitmap;
                    try{
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                        binding.ivAvatar.setImageBitmap(bitmap);

                        i1 = bitmap.toString();
                        Log.i("firstimage....", ""+i1);

                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
