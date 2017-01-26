package com.detsyky.vocabulary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {



    String name,translation;
    public static final String TAG_N="message_n";
    public static final String TAG_T="message_t";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent=getIntent();
        name=intent.getStringExtra(TAG_N);
        translation=intent.getStringExtra(TAG_T);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tv_name=(TextView)findViewById(R.id.word_etable);
        TextView tv_translation=(TextView)findViewById(R.id.translation_etable);
        tv_name.setText(name);
        tv_translation.setText(translation);
    }
    public void Edit(View view)
    {
        Intent intent=new Intent(this, EditActivitySecond.class);
        intent.putExtra(EditActivity.TAG_N,name);
        intent.putExtra(EditActivity.TAG_T,translation);
        startActivity(intent);
    }
}
