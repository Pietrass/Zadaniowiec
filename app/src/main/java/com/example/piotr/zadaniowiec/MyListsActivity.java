package com.example.piotr.zadaniowiec;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MyListsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDodajDialog();
            }
        });
        fab.setImageResource(R.drawable.ic_add);
    }

    private void showDodajDialog() {
        FragmentManager fm = getSupportFragmentManager();
        // DodajFragment dodajFragment = DodajFragment.newInstance();
        // dodajFragment.show(fm, "dialog");
    }

}
