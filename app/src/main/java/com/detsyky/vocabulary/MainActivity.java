package com.detsyky.vocabulary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> words_array = new ArrayList<>(); // temporary
    private ArrayList<String> translations_array = new ArrayList<>(); // temporary

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() { super.onResume(); }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void addWord(View v) {
        EditText word_input = (EditText) findViewById(R.id.word_input);
        EditText translation_input = (EditText) findViewById(R.id.translation_input);
        String word = word_input.getText().toString();
        String translation = translation_input.getText().toString();
        addWordToTable(word, translation);
        addWordToArray(word, translation);
        word_input.setText(""); // clean EditText input fields
        translation_input.setText("");
    }

    private void addWordToTable(String word, String translation) {
        ViewGroup.LayoutParams params;
        TableLayout vocabulary_table = (TableLayout)findViewById(R.id.vocabulary_table);
        vocabulary_table.setVisibility(View.VISIBLE);

        TableRow tableRow = new TableRow(this);
        TextView wordTextView = new TextView(this);
        TextView translationTextView = new TextView(this);

        TextView word_table_header = (TextView) findViewById(R.id.word_table_header);
        params = word_table_header.getLayoutParams();
        wordTextView.setLayoutParams(params);
        wordTextView.setPaddingRelative(4, 0, 0, 0);
        wordTextView.setBackgroundResource(R.drawable.cell_background);
        wordTextView.setText(word);

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

    //temporary method
    private void addWordToArray(String word, String translation) {
        words_array.add(word);
        translations_array.add(translation);
    }

}
