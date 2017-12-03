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
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_edit);
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

        EditText editText = (EditText) findViewById(R.id.editTextTitle);
        editText.setText(title);

        editText = (EditText) findViewById(R.id.editTextName);
        editText.setText(name);

        editText = (EditText) findViewById(R.id.editTextCompany);
        editText.setText(company);

        editText = (EditText) findViewById(R.id.editTextWebsite);
        editText.setText(website);

        editText = (EditText) findViewById(R.id.editTextPhoneNumber);
        editText.setText(phoneNumber);

        editText = (EditText) findViewById(R.id.editTextAddress);
        editText.setText(address);

        editText = (EditText) findViewById(R.id.editTextEmailAddress);
        editText.setText(emailAddress);

    }

    public void saveCard(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextTitle);
        title = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editTextName);
        name = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editTextCompany);
        company = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editTextWebsite);
        website = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editTextPhoneNumber);
        phoneNumber = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editTextAddress);
        address = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editTextEmailAddress);
        emailAddress = editText.getText().toString();

        BusinessCard updatedCard = new BusinessCard(
                title, name, company, website, phoneNumber, address, emailAddress
        );
        BusinessCardDbHelper mydb = new BusinessCardDbHelper(this, "BusinessCardDb", null, 11);
        mydb.updateCard(id, updatedCard);
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void cancelCard(View view) {
        Intent intent = new Intent(this, CardActivity.class);
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
