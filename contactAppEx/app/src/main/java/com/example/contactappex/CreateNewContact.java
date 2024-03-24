package com.example.contactappex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contactappex.databinding.ActivityMainBinding;
import com.example.contactappex.databinding.CreateNewContactBinding;

import java.io.FileNotFoundException;

public class CreateNewContact extends AppCompatActivity {
    private CreateNewContactBinding binding;
    private AppDatabase appDatabase;
    private ContactDao contactDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CreateNewContactBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewContact.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = String.valueOf(binding.etNewName.getText());
                String newMobile = String.valueOf(binding.etNewMobile.getText());
                String newEmail = String.valueOf(binding.etNewEmail.getText());
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase = AppDatabase.getInstance(getApplicationContext());
                        contactDao = appDatabase.contactDao();
                        contactDao.insert(new Contact(newName, newMobile, newEmail));
                    }
                });
                Intent intent = new Intent(CreateNewContact.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
