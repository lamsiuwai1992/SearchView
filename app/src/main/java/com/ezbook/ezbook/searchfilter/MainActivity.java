package com.ezbook.ezbook.searchfilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{
    MyRecyclerViewAdapter adapter;
    MaterialSearchView searchView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] countryName = {"Afghanistan", "Algeria", "Armenia", "Austria", "Aruba", "Bahrain", "Bhutan", "Brazil", "Canada", "China", "Croatia", "Cuba", "Dominica","Egypt","Ethiopia","Finland","France","Germany","Greece","Greenland","Guatemala","Guyana","Hong Kong","Iceland","Japan","Macau","Malaysia","Mexico","Netherlands"};
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this,countryName);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // this method will be called when user submit the on the search view
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //this method will be called when the there is text changing in the search view
                if(newText != null && !newText.isEmpty()){
                    String searchText = newText.toLowerCase();
                    List<String> stringList = new ArrayList<String>();
                    for(int i = 0 ; i < countryName.length; i++){
                        if(countryName[i].toString().toLowerCase().contains(searchText))
                            stringList.add(countryName[i]);
                     }

                    String[] tempCountry = new String [stringList.size()];
                    for(int i = 0 ;i<stringList.size() ;i++){
                        tempCountry[i] =stringList.get(i);
                    }
                    final MyRecyclerViewAdapter newListAdapter = new MyRecyclerViewAdapter(getApplicationContext(),tempCountry);
                    newListAdapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(MainActivity.this, "You clicked  " + newListAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                        }
                    });
                    recyclerView.setAdapter(newListAdapter);
                return true;
                }
                return false ;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                // this method will be called when the search view is shown
            }

            @Override
            public void onSearchViewClosed() {
                // this method will be called when the search view is closed
                final MyRecyclerViewAdapter orginalListApadater = new MyRecyclerViewAdapter(getApplicationContext(),countryName);
                orginalListApadater.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "You clicked  " + orginalListApadater.getItem(position), Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView.setAdapter(orginalListApadater);
            }
        });


    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(MainActivity.this, "You clicked  " + adapter.getItem(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
}
