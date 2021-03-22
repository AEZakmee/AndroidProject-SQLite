package com.example.firstproject_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SearchUserActivity extends AppCompatActivity {

    private ArrayAdapter<String> aad;
    final Database db = new Database(this);
    List<String> usersString;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        usersString = db.getAllUsersStringName();
        aad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersString);
        listview = findViewById(R.id.lv_listView);
        listview.setAdapter(aad);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        TextView tv = findViewById(R.id.lv_emptyTextView);
        listview.setEmptyView(tv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu,menu);
        MenuItem search = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) search.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                aad.getFilter().filter(newText);
                Log.e("filter", newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}