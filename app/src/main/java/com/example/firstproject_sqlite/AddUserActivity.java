package com.example.firstproject_sqlite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class AddUserActivity extends AppCompatActivity {
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Button clear = (Button) findViewById(R.id.clearbutton);
        Button adduser = (Button) findViewById(R.id.adduserbutton) ;
        Button back = (Button) findViewById(R.id.backbutton_adduser);
        final TextView username = (TextView) findViewById(R.id.usernametv);
        final TextView password = (TextView) findViewById(R.id.passwordtv);
        final TextView email = (TextView) findViewById(R.id.emailtv);
        final TextView phone = (TextView) findViewById(R.id.phonetv);
        final TextView address = (TextView) findViewById(R.id.adresstv);
        final Database db = new Database(this);
        alertDialogBuilder = new AlertDialog.Builder(this);

        adduser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String pass = MainActivity.computeSHAHash(password.getText().toString());
                db.addUser(new User(username.getText().toString(), pass,email.getText().toString(),phone.getText().toString(),address.getText().toString()));
                alertDialogBuilder.setMessage("User added successfully");
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                password.setText("");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddUserActivity.this, WorkActivity.class));
            }
        });
    }
}