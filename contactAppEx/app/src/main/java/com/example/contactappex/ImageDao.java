package com.example.contactappex;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ImageDao {
    @Query("SELECT * FROM image WHERE idContact = :idContact")
    Image getByIDContact(int idContact);

    @Insert
    void insert(Image... images);
    @Query("UPDATE image SET img = :img WHERE idContact = :idContact")
    void updateImage(String img, int idContact);
}
