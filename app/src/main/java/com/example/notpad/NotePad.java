package com.example.notpad;

import java.io.Serializable;

public class NotePad implements Serializable {
    private int noteId;
    private String noteTitle;
    private String noteDescription;
    private String noteCategory;
    private byte[] noteImage;

    public NotePad() {
    }

    public NotePad(int noteId, String noteTitle, String noteDescription, String noteCategory, byte[] noteImage) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteCategory = noteCategory;
        this.noteImage = noteImage;
    }

    public NotePad(String noteTitle, String noteDescription, String noteCategory, byte[] noteImage) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteCategory = noteCategory;
        this.noteImage = noteImage;
    }

    public NotePad(int noteId, String noteTitle, String noteDescription, String noteCategory) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteCategory = noteCategory;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteCategory() {
        return noteCategory;
    }

    public void setNoteCategory(String noteCategory) {
        this.noteCategory = noteCategory;
    }

    public byte[] getNoteImage() {
        return noteImage;
    }

    public void setNoteImage(byte[] noteImage) {
        this.noteImage = noteImage;
    }
}
