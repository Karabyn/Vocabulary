package com.detsyky.vocabulary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.detsyky.vocabulary.Translator.Language;
import com.detsyky.vocabulary.Translator.Translate;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseHelper dbHelper;
    SQLiteDatabase database;
    public static final String TAG="message";

    private ArrayList<String> langSpinner = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        Translate.setKey("trnsl.1.1.20170116T214127Z.36a136aae5abc0a2.4273e8f2d437ee78105aa45d6ccaf32c005fb8a5");
        FirstActivity.pref = getSharedPreferences("main",MODE_PRIVATE);
        String savedText = FirstActivity.pref.getString("VocabName","").toString();
        langSpinner.add(savedText);
        Spinner spinner = (Spinner)findViewById(R.id.SpinVoc);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, langSpinner);
        spinner.setAdapter(adapter);
        first_table_display();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() { super.onResume();  }

    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * gets user input and calls external method to add the word
     * @since 11.01.2017
     * */
    public void addWord(View v) {
        EditText word_input = (EditText) findViewById(R.id.word_input);
        EditText translation_input = (EditText) findViewById(R.id.translation_input);
        String word = word_input.getText().toString();
        String translation = translation_input.getText().toString();
        if(!word.equals("")) {
            addWordToDatabase(word, translation);
            addWordToTable();
            word_input.setText(""); // clean EditText input fields
            translation_input.setText("");
        }
        else
        {
            Toast toast = Toast.makeText(this, "Please enter the word!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    /**
     * dynamically adds word to the table.
     * Creates new TextView fields and a new TableRow
     * @param word string from addWord method
     * @param translation string from addWord method
     * @since 11.02.2017
     */
    private void addWordToDatabase(String word, String translation) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.COLUMN_WORD,word);
        contentValues.put(dbHelper.COLUMN_TRANSLATE,translation);
        database.insert(dbHelper.TABLE_NAME,null, contentValues);
    }

    private void addWordToTable() {
        ViewGroup.LayoutParams params;
        TableLayout vocabulary_table = (TableLayout)findViewById(R.id.vocabulary_table);
        vocabulary_table.setVisibility(View.VISIBLE);

        TableRow tableRow = new TableRow(this);
        TextView wordTextView = new TextView(this);
        TextView translationTextView = new TextView(this);
        String word = "";
        String translation = "";
        Cursor cursor = database.query(dbHelper.TABLE_NAME,null,null,null,null,null,null);


        if(cursor.moveToLast())
        {
            word = cursor.getString(1);
            translation = cursor.getString(2);
        }

        TextView word_table_header = (TextView) findViewById(R.id.word_table_header);
        params = word_table_header.getLayoutParams();
        wordTextView.setLayoutParams(params);
        wordTextView.setPaddingRelative(4, 0, 0, 0);
        wordTextView.setBackgroundResource(R.drawable.cell_background);
        wordTextView.setText(word);
        wordTextView.setOnClickListener(this);

        TextView translation_table_header = (TextView) findViewById(R.id.translation_table_header);
        params = translation_table_header.getLayoutParams();
        translationTextView.setLayoutParams(params);
        translationTextView.setPaddingRelative(4, 0, 0, 0);
        translationTextView.setBackgroundResource(R.drawable.cell_background);
        translationTextView.setText(translation);

        tableRow.addView(wordTextView);
        tableRow.addView(translationTextView);
        vocabulary_table.addView(tableRow);
        database.close();
        cursor.close();
    }

    public void translate(View view) {
        RetrieveFeedTask elseThread = new RetrieveFeedTask();
        EditText et = (EditText)findViewById(R.id.word_input);
        String wordToTranslate = et.getText().toString();
        EditText et2 = (EditText)findViewById(R.id.translation_input);
        String translation = "";
        elseThread.execute(wordToTranslate);
        try {
            translation = elseThread.get().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        et2.setText(translation);
    }

    public void first_table_display()
    {
        ViewGroup.LayoutParams params;
        TableLayout vocabulary_table = (TableLayout)findViewById(R.id.vocabulary_table);
        vocabulary_table.setVisibility(View.VISIBLE);


        String word = "";
        String translation = "";
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(dbHelper.TABLE_NAME, null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                TableRow tableRow = new TableRow(this);
                TextView wordTextView = new TextView(this);
                TextView translationTextView = new TextView(this);
                word = cursor.getString(1);
                translation = cursor.getString(2);

                TextView word_table_header = (TextView) findViewById(R.id.word_table_header);
                params = word_table_header.getLayoutParams();
                wordTextView.setLayoutParams(params);
                wordTextView.setPaddingRelative(4, 0, 0, 0);
                wordTextView.setBackgroundResource(R.drawable.cell_background);
                wordTextView.setText(word);
                wordTextView.setOnClickListener(this);

                TextView translation_table_header = (TextView) findViewById(R.id.translation_table_header);
                params = translation_table_header.getLayoutParams();
                translationTextView.setLayoutParams(params);
                translationTextView.setPaddingRelative(4, 0, 0, 0);
                translationTextView.setBackgroundResource(R.drawable.cell_background);
                translationTextView.setText(translation);

                tableRow.addView(wordTextView);
                tableRow.addView(translationTextView);
                vocabulary_table.addView(tableRow);

            }
            while (cursor.moveToNext());
        }
        database.close();
        cursor.close();
    }

    @Override
    public void onClick(View view) {
        TextView tv=(TextView)view;
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(dbHelper.TABLE_NAME, null, null, null, null, null, null);

        cursor.moveToFirst();
        String translation="";
        do
        {
            if(cursor.getString(1).equals(tv.getText().toString()))
            {
                translation=cursor.getString(2);
            }
        }
        while(cursor.moveToNext());
        database.close();
        cursor.close();

        Intent intent=new Intent(this, EditActivity.class);
        intent.putExtra(EditActivity.TAG_N, tv.getText().toString());
        intent.putExtra(EditActivity.TAG_T, translation);
        startActivity(intent);

    }
}
