package com.detsyky.vocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.detsyky.vocabulary.Translator.Language;
import java.lang.reflect.Field;

public class FirstActivity extends AppCompatActivity {
    private String lang[] = {Language.ALBANIAN.name(), Language.ARMENIAN.name(), Language.AZERBAIJANI.name(), Language.BELARUSIAN.name(), Language.BULGARIAN.name(),
            Language.CATALAN.name(), Language.CROATIAN.name(), Language.CZECH.name(), Language.DANISH.name(), Language.DUTCH.name(), Language.ENGLISH.name(),
            Language.ESTONIAN.name(), Language.FINNISH.name(), Language.FRENCH.name(), Language.GERMAN.name(), Language.GREEK.name(), Language.HUNGARIAN.name(),
            Language.ITALIAN.name(), Language.LATVIAN.name(), Language.LITHUANIAN.name(), Language.MACEDONIAN.name(), Language.NORWEGIAN.name(), Language.POLISH.name(),
            Language.PORTUGUESE.name(), Language.ROMANIAN.name(), Language.RUSSIAN.name(), Language.SERBIAN.name(), Language.SLOVAK.name(), Language.SLOVENIAN.name(),
            Language.SPANISH.name(), Language.SWEDISH.name(), Language.TURKISH.name(), Language.UKRAINIAN.name()};
    private Spinner spinnerNative, spinnerLearn;
    private Language langNative, langLearn;
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        firstUpp(lang);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lang);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNative = (Spinner) findViewById(R.id.Spin);
        spinnerNative.setAdapter(adapter);
        spinnerLearn = (Spinner) findViewById(R.id.Spin2);
        spinnerLearn.setAdapter(adapter);
        setSpinnerHeight(spinnerNative, 1500);
        setSpinnerHeight(spinnerLearn, 1500);
        pref = getSharedPreferences("main",MODE_PRIVATE);
        editor = pref.edit();
        String getStatus = pref.getString("register", "nil");
        if (getStatus.equals("true"))
        {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void clickOkButton(View view) {
        langNative = Language.fromString(spinnerNative.getSelectedItem().toString().toUpperCase());
        langLearn = Language.fromString(spinnerLearn.getSelectedItem().toString().toUpperCase());
        editor.putString("register", "true");
        editor.commit();
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("Native", langNative);
        intent.putExtra("Learn", langLearn);
        startActivity(intent);
        finish();
    }
        // Help methods
    public void firstUpp(String a[]) {
        for (int i = 0; i < a.length; i++) {
            a[i] = a[i].substring(0, 1) + a[i].substring(1).toLowerCase();
        }
    }
    public void setSpinnerHeight(Spinner spin, int size) {
        Field popup = null;
        try {
            popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = null;
            popupWindow = (android.widget.ListPopupWindow) popup.get(spin);
            popupWindow.setHeight(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

