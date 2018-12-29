package com.example.piotr.zadaniowiec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<String> zadania;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zadania = getFromSharPrefs(this);
        if (zadania == null) {
            zadania = new ArrayList<>();
        }
        // recyclerView
        mRecyclerView = findViewById(R.id.recycle_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(zadania, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                showDodajDialog(item);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback cb = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                zadania.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(cb);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        // fab
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDodajDialog(null);
            }
        });
        fab.setImageResource(R.drawable.ic_add);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveToSharPrefs(this);
    }

    private void showDodajDialog(String requestCode) {
        FragmentManager fm = getSupportFragmentManager();
        DodajFragment dodajFragment = DodajFragment.newInstance(requestCode);
        dodajFragment.show(fm, "dialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveToSharPrefs(Context context) {
        SharedPreferences sharPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(zadania);
        editor.putString("zadania", json);
        editor.commit();
    }

    public ArrayList<String> getFromSharPrefs(Context context) {
        SharedPreferences sharPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = sharPrefs.getString("zadania", "");
        return gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
    }
}
