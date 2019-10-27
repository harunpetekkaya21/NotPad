package com.example.notpad;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NotePadDao {


    //get data from table
    public ArrayList<NotePad> getData(MytabaseHelper mytabaseHelper){
        ArrayList<NotePad> notePadArrayList = new ArrayList<>();

        SQLiteDatabase db =mytabaseHelper.getWritableDatabase();
        Cursor c =db.rawQuery("SELECT * FROM notepad",null);
        while (c.moveToNext()){
            NotePad item = new NotePad(c.getInt(c.getColumnIndex("note_id"))
                    ,c.getString(c.getColumnIndex("note_title"))
                    ,c.getString(c.getColumnIndex("note_description"))
                    ,c.getString(c.getColumnIndex("note_category"))
                    ,c.getBlob(c.getColumnIndex("note_image"))

            );

            notePadArrayList.add(item);


        }
        db.close();
        return notePadArrayList;

    }



    public void deleteNote(MytabaseHelper mytabaseHelper,int note_id){

        SQLiteDatabase db = mytabaseHelper.getWritableDatabase();
        db.delete("notepad","note_id=?",new String[]{String.valueOf(note_id)});
        db.close();
    }

    public void addNote(MytabaseHelper mytabaseHelper,String noteTitle,String noteDescription,String noteCategory,byte[] noteImage){
        SQLiteDatabase db = mytabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("note_title",noteTitle);
        values.put("note_description",noteDescription);
        values.put("note_category",noteCategory);
        values.put("note_image",noteImage);
        db.insertOrThrow("notepad",null,values);
        db.close();
    }

    public void updateNote(MytabaseHelper mytabaseHelper,int noteId,String noteTitle,String noteDescription,String noteCategory,byte[] noteImage){
        SQLiteDatabase db = mytabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("note_title",noteTitle);
        values.put("note_description",noteDescription);
        values.put("note_category",noteCategory);
        values.put("note_image",noteImage);
        db.update("notepad",values,"note_id=?",new String[]{String.valueOf(noteId)});
        db.close();
    }

}
