package com.example.notesapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference database;
    ListView listView;
    Title title;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        final EditText tv = new EditText(this);
        tv.setGravity(Gravity.CENTER);
        new AlertDialog.Builder(this)
                .setMessage("Enter the tittle of note")
                .setView(tv)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String head = tv.getText().toString();
                        arrayList.add(head);
                        title = new Title();
                        title.setTitle(head);
                        database.child("Note No n").setValue(title);
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

//        Toast.makeText(getApplicationContext(), tv.getText().toString(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.list);
        database = FirebaseDatabase.getInstance().getReference().child("Member");


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrayList.remove(i);
                arrayAdapter.notifyDataSetChanged();
//                listView.requestLayout();
                //adapterView.refreshDrawableState();
                return true;
            }
        });

        arrayList = new ArrayList<>();
        arrayList.add("Negan");
        arrayList.add("Rick Grimes");
        arrayList.add("Daryl");


        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv1 = (TextView) view;
                String str = tv1.getText().toString();
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });

    }
}