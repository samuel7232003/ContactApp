package com.example.contactappex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactappex.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Contact> contactList;
    private ContactsAdapter contactsAdapter;
    private AppDatabase appDatabase;
    private ContactDao contactDao;
    int n;
    List<Contact> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<Contact>();
        contactsAdapter = new ContactsAdapter(contactList);
        binding.rvContacts.setAdapter(contactsAdapter);


        //contactList.add(new Contact("Nguyen Van A", "0123456789", "a@gmail.com"));
        //contactList.add(new Contact("Nguyen Van B", "0123456788", "b@gmail.com"));
        //contactList.add(new Contact("Nguyen Van C", "0123456787", "c@gmail.com"));
        getAllContacts();

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNewContact.class);
                startActivity(intent);

            }
        });
        binding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvTitle.setVisibility(View.GONE);
                binding.etSearch.setVisibility(View.VISIBLE);
                binding.ivSearch.setVisibility(View.GONE);
                binding.etSearch.requestFocus();
                binding.ivOutSeach.setVisibility(View.VISIBLE);
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
 //               int n = contactDao.getAll().size();
                contactList.clear();
                for(int i = 1; i< n; i++){
                    if(list.get(i).getName().contains(s.toString())){
                        contactList.add(list.get(i));
                    }
                }
                contactsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.ivOutSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etSearch.setText("");
                binding.tvTitle.setVisibility(View.VISIBLE);
                binding.etSearch.setVisibility(View.GONE);
                binding.ivSearch.setVisibility(View.VISIBLE);
                binding.ivOutSeach.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.etSearch.getWindowToken(), 0);
                getAllContacts();
            }
        });

        contactsAdapter.setClickListener(new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent detailContact = new Intent(MainActivity.this, DetailContact.class);
                int id = list.get(position+1).getId();
                detailContact.putExtra("id", id);
                startActivity(detailContact);
            }
        });
    }
    public void getAllContacts(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase = AppDatabase.getInstance(getApplicationContext());
                contactDao = appDatabase.contactDao();
                list = contactDao.getAll();
                n = list.size();
                //contactDao.insert(new Contact("Nguyen Van B", "0123456788", "b@gmail.com"));
                contactList.clear();
                for(int i = 1; i< n; i++){
                    contactList.add(list.get(i));
                }
                contactsAdapter.notifyDataSetChanged();
            }
        });
    }

    interface ClickListener{
        public void onClick(View view, int position);
    }

}