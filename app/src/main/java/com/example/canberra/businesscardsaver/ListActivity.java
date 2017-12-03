package com.example.canberra.businesscardsaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<BusinessCard> cards = new ArrayList<BusinessCard>();
    BusinessCardDbHelper mydb = new BusinessCardDbHelper(this, "BusinessCardDb", null, 11);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cards = mydb.getAllCards();
        if (cards.isEmpty()) {
            // String title, String name, String company, String website, String phoneNumber, String address, String emailAddress
            mydb.insertCard(new BusinessCard("CEO", "Marco Monteno", "Marco. inc", "Marco.com", "62410918", "11A Colton Pl Downer", "markmonteno@gmail.com"));
            mydb.insertCard(new BusinessCard("Chief Engineer", "Bob Jane", "Bob. inc", "BobJaneSarah.com.au", "642 542 324", "1 Some Place Woden St. Yeah", "bobjane@hotmail.com"));
            mydb.insertCard(new BusinessCard("PR Department Head", "Sarah Wyatt", "Sarah Corporations", "Marco.com", "62410918", "50th Avenue Hulk St. Australia", "sarahspersonallongemail@gmail.com"));
            cards = mydb.getAllCards();
        }

        BusinessCardAdapter adapter = new BusinessCardAdapter(this, R.layout.my_listview_item, cards);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusinessCard card = cards.get(position);
                Intent intent = new Intent(view.getContext(), CardActivity.class);
                intent.putExtra("title", card.getTitle());
                intent.putExtra("name", card.getName());
                intent.putExtra("company", card.getCompany());
                intent.putExtra("website", card.getWebsite());
                intent.putExtra("phoneNumber", card.getPhoneNumber());
                intent.putExtra("address", card.getAddress());
                intent.putExtra("emailAddress", card.getEmailAddress());
                intent.putExtra("id", card.getId());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void add(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_main) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_list) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
