package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private DBHelper db;
    EditText edit_height, edit_fat ,edit_weight;//표시되어지는 키, 체지방, 무게


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        db = new DBHelper(this);
        Button button3 = (Button) findViewById(R.id.sign);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                edit_height = (EditText)findViewById(R.id.heightsign); //
                edit_fat = (EditText)findViewById(R.id.fatsign);
                edit_weight = (EditText)findViewById(R.id.weightsign); // 적어놓은것을 저장.
                String ID = "first";
                String HEIGHT = edit_height.getText().toString();
                String FAT = edit_fat.getText().toString();
                String WEIGHT = edit_weight.getText().toString();

                db.update( ID, HEIGHT, FAT, WEIGHT);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}