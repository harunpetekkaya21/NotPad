package com.example.notpad;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.DialogCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyCardItemAdapter extends RecyclerView.Adapter<MyCardItemAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<NotePad> notePadArrayList;
    Dialog myDialog;
    private final int REQUEST_CODE_GALLERY=21;

    public MyCardItemAdapter(Context mContext, ArrayList<NotePad> notePadArrayList) {
        this.mContext = mContext;
        this.notePadArrayList = notePadArrayList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.card_item, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);


        //diaog init
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_note);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.cardView_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialogNoteTitle_id = myDialog.findViewById(R.id.dialogNoteTitle_id);
                TextView dialogNoteDescription_id = myDialog.findViewById(R.id.dialogNoteDescription_id);
                TextView dialogNoteCategory_id = myDialog.findViewById(R.id.dialogNoteCategory_id);
                ImageView dialogNoteImg_id = myDialog.findViewById(R.id.dialogNoteImg_id);
                Button dialog_buttonEsc_id=myDialog.findViewById(R.id.dialog_buttonEsc_id);

                dialog_buttonEsc_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.cancel();
                    }
                });

                //dialogdaki verileri set ediyoruz
                dialogNoteTitle_id.setText(notePadArrayList.get(vHolder.getAdapterPosition()).getNoteTitle());
                dialogNoteDescription_id.setText(notePadArrayList.get(vHolder.getAdapterPosition()).getNoteDescription());
                dialogNoteCategory_id.setText(notePadArrayList.get(vHolder.getAdapterPosition()).getNoteCategory());
                //set image bitmap
                byte[] imageNote = notePadArrayList.get(vHolder.getAdapterPosition()).getNoteImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageNote, 0, imageNote.length);
                dialogNoteImg_id.setImageBitmap(bitmap);
                Toast.makeText(mContext, "Test Click" + String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                myDialog.show();

            }


        });


        return vHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNamtvNoteTitle_id.setText(notePadArrayList.get(position).getNoteTitle());
        holder.tvNoteDescription_id.setText(notePadArrayList.get(position).getNoteDescription());
        holder.tvNoteCategory_id.setText(notePadArrayList.get(position).getNoteCategory());


        //set image bitmap
        byte[] imageNote = notePadArrayList.get(position).getNoteImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageNote, 0, imageNote.length);
        holder.imgNote_id.setImageBitmap(bitmap);


    }

    @Override
    public int getItemCount() {
        return notePadArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView_id;
        private TextView tvNamtvNoteTitle_id;
        private TextView tvNoteDescription_id;
        private TextView tvNoteCategory_id;
        private ImageView imgNote_id;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamtvNoteTitle_id = itemView.findViewById(R.id.tvNoteTitle_id);
            tvNoteDescription_id = itemView.findViewById(R.id.tvNoteDescription_id);
            tvNoteCategory_id = itemView.findViewById(R.id.tvNoteCategory_id);
            cardView_id = itemView.findViewById(R.id.cardView_id);
            imgNote_id = itemView.findViewById(R.id.imgNote_id);




        }
    }



}
