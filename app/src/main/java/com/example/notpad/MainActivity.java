package com.example.notpad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottom_navigation_id);




        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_id,new NotesFragment()).commit();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId()==R.id.action_notes){

                    fragment = new NotesFragment();
                }if (item.getItemId()==R.id.action_note_add){
                    fragment = new AddNoteFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_id,fragment).commit();
                return true;

            }
        });


    }


}
