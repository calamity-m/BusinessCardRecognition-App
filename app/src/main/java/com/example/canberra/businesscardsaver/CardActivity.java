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
import android.widget.TextView;

import java.util.List;

public class CardActivity extends AppCompatActivity {

    private String title;
    private String name;
    private String company;
    private String website;
    private String phoneNumber;
    private String address;
    private String emailAddress;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        this.title = title;

        String name = extras.getString("name");
        this.name = name;

        String company = extras.getString("company");
        this.company = company;

        String website = extras.getString("website");
        this.website = website;

        String phoneNumber = extras.getString("phoneNumber");
        this.phoneNumber = phoneNumber;

        String address = extras.getString("address");
        this.address = address;

        String emailAddress = extras.getString("emailAddress");
        this.emailAddress = emailAddress;

        String id = extras.getString("id");
        this.id = id;

        TextView textView = (TextView) findViewById(R.id.textViewTitleView);
        textView.setText("Card: " + name);

        textView = (TextView) findViewById(R.id.textViewTitle);
        textView.setText(title);

        textView = (TextView) findViewById(R.id.textViewName);
        textView.setText(name);

        textView = (TextView) findViewById(R.id.textViewCompany);
        textView.setText(company);

        textView = (TextView) findViewById(R.id.textViewWebsite);
        textView.setText(website);

        textView = (TextView) findViewById(R.id.textViewPhoneNumber);
        textView.setText(phoneNumber);

        textView = (TextView) findViewById(R.id.textViewAddress);
        textView.setText(address);

        textView = (TextView) findViewById(R.id.textViewEmailAddress);
        textView.setText(emailAddress);

    }

    public void editCard(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("name", name);
        intent.putExtra("company", company);
        intent.putExtra("website", website);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("address", address);
        intent.putExtra("emailAddress", emailAddress);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void deleteCard(View view) {
        BusinessCardDbHelper mydb = new BusinessCardDbHelper(this, "BusinessCardDb", null, 11);
        mydb.deleteCard(id);

        Intent intent = new Intent(this, ListActivity.class);
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
