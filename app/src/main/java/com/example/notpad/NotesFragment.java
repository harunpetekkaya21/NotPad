package com.example.notpad;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class NotesFragment extends Fragment {
    View view;
    private RecyclerView myRecyclerView;
    private ArrayList<NotePad> notePadArrayList;
    private MytabaseHelper mytabaseHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notePadArrayList = new ArrayList<>();
        mytabaseHelper = new MytabaseHelper(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notes, container, false);
        myRecyclerView = view.findViewById(R.id.recyclerviewNotes_id);
        notePadArrayList = new NotePadDao().getData(mytabaseHelper);
        final MyCardItemAdapter adapter = new MyCardItemAdapter(getContext(), notePadArrayList);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setAdapter(adapter);



        //swipe buttonssss need delete and update...
        MySwipeHelper swipeHelper = new MySwipeHelper(getContext(), myRecyclerView, 200) {
            @Override
            public void instantiateMyButton(final RecyclerView.ViewHolder viewHolder, ArrayList<MySwipeHelper.MyButton> buffer) {
                buffer.add(new MyButton(getContext(), // button delete
                        "Delete",
                        30,
                        0,
                        Color.parseColor("#FF3C30"),
                        new MyButtonClickListener() {

                            @Override
                            public void onClick(int pos) {

                                new NotePadDao().deleteNote(mytabaseHelper, notePadArrayList.get(viewHolder.getAdapterPosition()).getNoteId());
                                Toast.makeText(getContext(), "Deleted Note", Toast.LENGTH_SHORT).show();
                                //update list
                                notePadArrayList = new NotePadDao().getData(mytabaseHelper);
                                myRecyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                refresh();



                            }
                        }

                ));

                buffer.add(new MyButton(getContext(), // button update
                        "Update",
                        30,
                        0,
                        Color.parseColor("#FF9502"),
                        new MyButtonClickListener() {

                            @Override
                            public void onClick(int pos) {
                                //send data flag

                                AddNoteFragment addNoteFragment = new AddNoteFragment();
                                Bundle arg = new Bundle();

                                arg.putSerializable("obj",notePadArrayList.get(viewHolder.getAdapterPosition()));
                                arg.putInt("flag",1);
                                addNoteFragment.setArguments(arg);

                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.frameLayout_id, addNoteFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();


                            }
                        }

                ));

            }


        };

        return view;
    }


    public void refresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(NotesFragment.this).attach(NotesFragment.this).commit();
    }







}
