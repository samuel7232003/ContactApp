package com.example.contactappex;

import android.content.Context;

import androidx.room.Database;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Image.class, version = 1)
public abstract class ImageDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();

    private static ImageDatabase instance;

    public static ImageDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    ImageDatabase.class, "images").build();
        }
        return instance;
    }
}
