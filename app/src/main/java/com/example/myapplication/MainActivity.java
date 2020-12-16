package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(this);
        db.insert("first","height","fat","weight");
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.info);
        Button button2 = (Button) findViewById(R.id.start);

        TextView textview1 = (TextView)findViewById(R.id.height);
        TextView textview2 = (TextView)findViewById(R.id.fat);
        TextView textview3 = (TextView)findViewById(R.id.weight);

        Cursor data = db.getData("id");
        String h = data.getString(2);
        String f = data.getString(3);
        String w = data.getString(4);

        textview1.setText(h);
        textview2.setText(f);
        textview3.setText(w);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}