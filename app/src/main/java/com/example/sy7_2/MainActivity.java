package com.example.sy7_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this, "Persons.db", null, 2);

        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("name", "July");
                values.put("sex", "Boy");
                values.put("phone", "10987654321");

                db.insert("Person", null, values);
                values.clear();
                // 开始组装第二条数据
                values.put("name", "Admin");
                values.put("sex", "Girl");
                values.put("phone", "12345678910");

                db.insert("Person", null, values);
            }
        });

        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Cursor cursor = db.query("Person", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {

                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));
                        @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));

                        Log.d("MainActivity", "Person name is " + name);
                        Log.d("MainActivity", "Person sex is " + sex);
                        Log.d("MainActivity", "Person phone is " + phone);

                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}