package com.example.firstproject_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        final TextView textusers = (TextView) findViewById(R.id.autoCompleteTextView);
        Button LogOut = (Button) findViewById(R.id.logout);
        Button showallusers = (Button) findViewById(R.id.showallusers);
        Button adduser = (Button) findViewById(R.id.adduser);
        Button delete = (Button) findViewById(R.id.deleteuser);
        Button search = (Button) findViewById(R.id.search);
        final Database db = new Database(this);
        final List<User> users = db.getAllUsers();
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkActivity.this, AddUserActivity.class));
            }
        });
        showallusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textusers.setText("");
                for(User us: users){
                    String log = textusers.getText() + "\n" + "ID: " + us.getId() + " ,User: " + us.getUserName();
                    textusers.setText(log);
                }
                Toast.makeText(WorkActivity.this,"All users are loaded", Toast.LENGTH_LONG).show();
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkActivity.this, MainActivity.class));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkActivity.this, DeleteUserActivity.class));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkActivity.this, SearchUserActivity.class));
            }
        });

    }

}