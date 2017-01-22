package com.detsyky.vocabulary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EditActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    SQLiteDatabase database;
    String name;
    public static final String TAG="message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        dbHelper = new DatabaseHelper(this);
        Intent intent=getIntent();
        name=intent.getStringExtra(TAG);
    }

    public void Edit(View view)
    {
        String c_name,e_name;
        android.widget.EditText edit=(android.widget.EditText)findViewById(R.id.et2);
        e_name=edit.getText().toString();
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(dbHelper.TABLE_NAME,null,null,null,null,null,null);
        cursor.moveToFirst();
        do
        {
            c_name=cursor.getString(1);
            if(name.equals(c_name))
            {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.COLUMN_TRANSLATE, e_name);
                database.update(DatabaseHelper.TABLE_NAME, cv, "_id" + " = ?", new String[]{cursor.getString(0)});
            }
        }
        while(cursor.moveToNext());
        database.close();
        cursor.close();
        Intent intent2=new Intent(this, MainActivity.class);
        startActivity(intent2);

    }
}
