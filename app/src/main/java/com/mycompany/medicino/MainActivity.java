package com.mycompany.medicino;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.alexboldtcompany.medicino.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, MedicineList.class);
        EditText editText = (EditText) findViewById(R.id.edit_query);
        String searchTerm = editText.getText().toString();
        //Uri pathName = Uri.parse("android.resource://com.mycompany.medicnino/" + R.raw.medicine);
        //String textFile = getResources().getString(R.string.medicine);
        //AssetManager assetManager = getAssets();
        //DataInputStream toScan=null;
        //try {
          //  toScan = new DataInputStream(getApplicationContext().getAssets().open("medicine.txt"));
        //}
        //catch(Exception e){

        //}
        DrugScanner query = new DrugScanner();
        //DrugScanner query = new DrugScanner("app/src/main/res/medicine.txt");
        DrugScanner.Node[] input = query.search(searchTerm);
        String answer="";
        if(input!=null) {
            for (DrugScanner.Node n : input) {
                for (int i = 0; i < n.info().length; i++) {
                    if (i == 0) {
                        answer += "Generic name: " + "\n" + n.info()[i] + "\n" + "\n" + "Brands: " + "\n";
                    } else {
                        answer += n.info()[i] + "\n";
                    }
                }
                if (n.infoAlt().length > 0) {
                    answer += "\n" + "Alternatives:" + "\n";
                    for (int k = 0; k < n.infoAlt().length; k++) {
                        answer += n.infoAlt()[k] + "\n";
                    }
                }
            }
        }
        if(answer.length() == 0){
            answer = "No medicine found, please try again.";
        }

        //System.out.println(answer);


        //intent.putExtra(EXTRA_MESSAGE, searchTerm);
        intent.putExtra(EXTRA_MESSAGE, answer);
        startActivity(intent);
    }
}
