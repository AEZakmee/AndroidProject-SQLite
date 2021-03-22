package com.example.firstproject_sqlite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    final Database db = new Database(this);
    public void login(View view){
        startActivity(new Intent(MainActivity.this, WorkActivity.class));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createadmin(){
        if(db.getUserCount()==0) {
            String pass = computeSHAHash("1234");
            User Recl = new User("admin", pass);
            db.addUser(Recl);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView username = (TextView) findViewById(R.id.userNameBox);
        final TextView password = (TextView) findViewById(R.id.passwordBox);
        final Button login = (Button) findViewById(R.id.button);
        //ImageView img = (ImageView) findViewById(R.id.imageView);
        //img.setImageResource(R.mipmap.tusofialogo);
        alertDialogBuilder = new AlertDialog.Builder(this);
        //db.onDeleteTable();
        createadmin();
        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                List<User> users = db.getAllUsers();
                boolean log = true;
                Log.e("login", "starting loop, users size: " + users.size());
                String pass = MainActivity.computeSHAHash(password.getText().toString());
                for(User us : users){

                    if(username.getText().toString().equals(us.getUserName())&&
                    pass.equals(us.getPassword())){
                        log = false;
                        open("Login Successful!", log);
                        break;
                    }
                    Log.e("login",us.getUserName());
                    Log.e("login", us.getPassword());
                }
                if(log){
                    open("invalid", log);
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }

            public void open(String ss, final boolean log){
                alertDialogBuilder.setMessage(ss);
                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(log == false) startActivity(new Intent(MainActivity.this, WorkActivity.class));
                    }
                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String computeSHAHash(String password){
        String SHAHash = " ";
        MessageDigest mdSha1 = null;
        try{
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mdSha1.update(password.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] data = mdSha1.digest();
        try{
            SHAHash=convertToHex(data);
        }catch(IOException e){

        }
        return SHAHash;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String convertToHex(byte[] data) throws  IOException{
        StringBuffer sb = new StringBuffer();
        String hex=null;
        hex = Base64.getEncoder().encodeToString(data);
        sb.append(hex);
        return sb.toString();
    }
}