package com.example.json_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView= findViewById(R.id.textView);
        new Thread(){
            @Override
            public void run(){
                try {
                    URL urlJSON =  new URL("https://antonymonline.ru/antonyms.json?word=lj%2Chsq");
                    URLConnection connection = urlJSON.openConnection();
                    Scanner scanner = new Scanner(connection.getInputStream());
                    String response = scanner.nextLine();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("antonyms");
                    for( int i =0 ;i<jsonArray.length();i++){
                        Log.d("ANTONYM "+i, jsonArray.getString(i));
                        final int k=i;
                        runOnUiThread(new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    Toast.makeText(getApplicationContext(),jsonArray.getString(k), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (IOException | JSONException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}