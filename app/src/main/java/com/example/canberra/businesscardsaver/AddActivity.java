package com.example.canberra.businesscardsaver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.LanguageCodes;
import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.OCR;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 1;
    private Uri mImageUri;
    private Bitmap mBitmap;
    private VisionServiceClient client;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (client==null){
            client = new VisionServiceRestClient("d7625d4488404b54a846211f0912e3e7");
        }

        mTextView = (TextView) findViewById(R.id.textViewDisplay);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    public void load(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM);
        }
    }

    public void capture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            mBitmap = (Bitmap) extras.get("data");
        }
        if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == RESULT_OK){
            // If image is selected successfully, set the image URI and bitmap.
            mImageUri = data.getData();
            mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                    mImageUri, getContentResolver());
        }

        if (mBitmap != null) {
            runOCR();
        }
    }

    public void runOCR () {
        try {
            new doRequest().execute();
        } catch (Exception e) {
            mTextView.setText("Error encountered. Exception is: " + e.toString());
        }
    }

    private String process() throws VisionServiceException, IOException {
        Gson gson = new Gson();

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        OCR ocr;
        ocr = this.client.recognizeText(inputStream, LanguageCodes.AutoDetect, true);

        String result = gson.toJson(ocr);
        Log.d("result", result);

        return result;
    }


    private class doRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;

        public doRequest() {
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;    // Store error
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            // Display based on error existence
            if (e != null) {
                mTextView.setText("Error: " + e.getMessage());
                this.e = null;
            } else {
                Gson gson = new Gson();
                OCR r = gson.fromJson(data, OCR.class);

                String title = "";
                String name = "";
                String company = "";
                String website = "";
                String phoneNumber = "";
                String address = "";
                String emailAddress = "";
                String result = "";
                for (Region reg : r.regions) {
                    for (Line line : reg.lines) {
                        for (Word word : line.words) {
                            result += word.text + " ";
                            // Find our Phone Number
                            if (Patterns.PHONE.matcher(word.text).matches()) {
                                phoneNumber = word.text;
                            }
                            // Find our Email Address
                            if (Patterns.EMAIL_ADDRESS.matcher(word.text).matches()) {
                                emailAddress += word.text;
                            }
                            // Find Our Website
                            if (Patterns.WEB_URL.matcher(word.text).matches()) {
                                website += word.text;
                            }
                        }

                        // Find Our Address
                        if (result.startsWith("Address")) {
                            // We have an address
                            String altered = result.replaceAll("Address: ", "");
                            altered = altered.replaceAll("Address:", "");
                            altered = altered.replaceAll("Address", "");
                            address = altered;
                        }
                        // Find Our Name
                        if (whiteSpaceAmount(result) == 2) {
                            // Regex sourced from
                            // http://stackoverflow.com/questions/15805555/java-regex-to-validate-full-name-allow-only-spaces-and-letters
                            // See answer by Ziza, answered March 18 2014 at 15:32
                            String regexName = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
                            Pattern p = Pattern.compile(regexName, Pattern.CASE_INSENSITIVE);
                            if (p.matcher(result).matches()) {
                                name = result;
                            }
                        }
                        // Find Our Company,
                        // Assumes that company will always be capitals, but fails when name is also capitals only
                        if (result.equals(result.toUpperCase())) {
                            // Assuming company will always be in capitals
                            company = result;
                        }

                        result = "";
                    }
                    result = "";
                }

                BusinessCard card = new BusinessCard(title,
                        name, company, website, phoneNumber, address, emailAddress);

                doAnalysis(card);

            }
        }
    }

    private int whiteSpaceAmount(String s) {
        int cnt = 0;

        if (s != null) {
            for (int i = 0; i < s.length(); i++) {
                if (Character.isWhitespace(s.charAt(i)))
                    cnt += 1;
            }
        }
        return  cnt;
    }

    private void doAnalysis(BusinessCard card) {
        BusinessCardDbHelper mydb = new BusinessCardDbHelper(this, "BusinessCardDb", null, 11);

        // Add this card to the database
        mydb.insertCard(card);

        Intent intent = new Intent(this, CardActivity.class);
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
