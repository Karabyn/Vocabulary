package com.detsyky.vocabulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.detsyky.vocabulary.Translator.Language;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        FirstActivity.pref = getSharedPreferences("main", MODE_PRIVATE);
        String getStatus = FirstActivity.pref.getString("register2", "nil");
        if (getStatus.equals("true"))
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void goToMainActivity(View view)
    {
        EditText editText = (EditText)findViewById(R.id.NameVoc);
        String name = "";
        if(!editText.getText().toString().equals(""))
        {
            name = editText.getText().toString();
            Language langNative = (Language) getIntent().getSerializableExtra("Native");
            Language langLearn = (Language) getIntent().getSerializableExtra("Learn");
            FirstActivity.editor.putString("register2", "true");
            FirstActivity.editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Native", langNative);
            intent.putExtra("Learn", langLearn);
            FirstActivity.editor.putString("VocabName",name);
            FirstActivity.editor.commit();
            startActivity(intent);
            finish();
        }
    }
}

