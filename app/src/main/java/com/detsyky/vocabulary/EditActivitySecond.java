package com.detsyky.vocabulary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditActivitySecond extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    SQLiteDatabase database;
    private String name,translation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_second);
        dbHelper = new DatabaseHelper(this);
        Intent intent=getIntent();
        name=intent.getStringExtra(EditActivity.TAG_N);
        translation=intent.getStringExtra(EditActivity.TAG_T);
    }

   public void Apply(View view)
    {
        String n_word,t_word, temp;
        EditText et_name=(EditText)findViewById(R.id.et1);
        EditText et_translation=(EditText)findViewById(R.id.et2);
        n_word=et_name.getText().toString();
        t_word=et_translation.getText().toString();
        if(n_word.equals(""))
        {
            n_word=name;
        }
        else if(t_word.equals(""))
        {
            t_word=translation;
        }
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(dbHelper.TABLE_NAME,null,null,null,null,null,null);
        cursor.moveToFirst();
        do
        {
            temp=cursor.getString(1);
            if(name.equals(temp))
            {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.COLUMN_WORD, n_word);
                cv.put(DatabaseHelper.COLUMN_TRANSLATE, t_word);
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
