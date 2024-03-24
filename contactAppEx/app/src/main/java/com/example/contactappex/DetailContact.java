package com.example.contactappex;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contactappex.databinding.DetailContactBinding;

public class DetailContact extends AppCompatActivity {
    private AppDatabase appDatabase;
    private ContactDao contactDao;
    private DetailContactBinding binding;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DetailContactBinding.inflate(getLayoutInflater());
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
                    Contact contact = contactDao.getByID(id);
                    binding.tvName.setText(contact.getName());
                    binding.tvPhone.setText(contact.getMobile());
                    binding.tvEmail.setText(contact.getEmail());
                }
            });
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(DetailContact.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(DetailContact.this, EditContact.class);
                edit.putExtra("id", id);
                startActivity(edit);
            }
        });
    }
}
