package com.example.contactappex;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact ")
    List<Contact> getAll();
    @Insert
    void insert(Contact... contacts);
    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact getByID(int id);

    @Query("UPDATE contact SET name = :name, mobile = :mobile, email = :email WHERE id = :id")
    void update(int id, String name, String mobile, String email);

}
