package com.example.notpad;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;



public class AddNoteFragment extends Fragment {
    private EditText etTitle, etCategory, etDescription;
    private Button btnAdd;
    private ImageView selectImage;
    private View view;
    private MytabaseHelper mytabaseHelper;
    private TextView textViewAdd,textViewUpdate;
    private Button btnUpdate;
    private NotePad updateData;
    private final int REQUEST_CODE_GALLERY=21;
    private boolean flag=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mytabaseHelper = new MytabaseHelper(getContext());
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            int myInt = bundle.getInt("flag");

           // if (myInt==1){
            if (myInt==1){
                flag=true;
            }


                updateData= (NotePad) getArguments().getSerializable("obj");

            }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_note, container, false);
        etTitle = view.findViewById(R.id.etTitle);
        etCategory = view.findViewById(R.id.etCategory);
        etDescription = view.findViewById(R.id.etDescription);
        selectImage = view.findViewById(R.id.selectImage);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnUpdate=view.findViewById(R.id.btnUpdate);
        textViewUpdate=view.findViewById(R.id.textViewUpdate);
        textViewAdd =view.findViewById(R.id.textViewAdd);



        //when clicked update button


        if (flag==true) {


            //gunclle butonuna tiklandiginda gelen verileri set ediyoruz
            etTitle.setText(updateData.getNoteTitle());
            etCategory.setText(updateData.getNoteCategory());
            etDescription.setText(updateData.getNoteDescription());
            byte[] img = updateData.getNoteImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            selectImage.setImageBitmap(bitmap);

          btnAdd.setVisibility(View.INVISIBLE);
          textViewAdd.setVisibility(View.INVISIBLE);



        }
        if (flag==false){
           btnUpdate.setVisibility(View.INVISIBLE);
           textViewUpdate.setVisibility(View.INVISIBLE);

        }


        //update data
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NotePadDao().updateNote(mytabaseHelper,updateData.getNoteId()
                        ,etTitle.getText().toString().trim()
                        ,etDescription.getText().toString()
                        ,etCategory.getText().toString().trim()
                        ,imageToByte(selectImage));
            }


        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sava data

                String noteTitle = etTitle.getText().toString().trim();
                String noteCategory = etCategory.getText().toString().trim();
                String noteDescription = etDescription.getText().toString();

                //add note


                try {

                    new NotePadDao().addNote(mytabaseHelper,noteTitle,noteDescription,noteCategory,imageToByte(selectImage));
                    Toast.makeText(getContext(),"Added successfully",Toast.LENGTH_SHORT).show();

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(AddNoteFragment.this).attach(AddNoteFragment.this).commit();
                    //reset views

                    etTitle.setText("");
                    etCategory.setText("");
                    etDescription.setText("");


                    //selectImage.setImageResource(R.drawable.ic_add_a_photo_black_24dp);


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    public static byte[] imageToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,10,stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }


    ///permissionsss
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                //galaery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,REQUEST_CODE_GALLERY);


            }else {
                Toast.makeText(getContext(),"Dont have permission to access file location",Toast.LENGTH_SHORT).show();
            }
            return;



        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_GALLERY && resultCode== Activity.RESULT_OK){
            Uri imageUri = data.getData();
            selectImage.setImageURI(imageUri);

        }

    }







}
