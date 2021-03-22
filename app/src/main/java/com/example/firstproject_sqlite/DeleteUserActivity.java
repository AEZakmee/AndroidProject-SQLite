package com.example.firstproject_sqlite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class DeleteUserActivity extends AppCompatActivity {
    final Database db = new Database(this);
    int pos=-1;
    List<User> users;
    List<String> usersString;
    ListView listview;
    ArrayAdapter<String> aad;
    public void deletetable(View view){
        db.onDeleteTable();
        startActivity(new Intent(DeleteUserActivity.this, MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        Button back = (Button) findViewById(R.id.backbuttonusers);
        Button delete = (Button) findViewById(R.id.deleteuseract);
        users = db.getAllUsers();
        usersString = db.getAllUsersString();
        listview = findViewById(R.id.usersview);
        aad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersString);
        listview.setAdapter(aad);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeleteUserActivity.this, WorkActivity.class));
            }
        });

        listview.setOnItemClickListener((parent, view, position, id) -> {
            Log.e("click", String.valueOf(position));
            String printer = "You have chosen user with id: " + users.get(position).getId();
            pos = position;
            Toast.makeText(getApplicationContext(), printer, Toast.LENGTH_SHORT).show();
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos!=-1){
                    if(pos==0) {
                        Toast.makeText(getApplicationContext(), "Can't delete admin", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_SHORT).show();
                        db.deleteUser(users.get(pos));
                        users.remove(pos);
                        usersString.remove(pos);
                        pos = -1;
                        aad.notifyDataSetChanged();
                        listview.invalidateViews();
                        listview.refreshDrawableState();
                    }
                }
            }
        });
    }
}