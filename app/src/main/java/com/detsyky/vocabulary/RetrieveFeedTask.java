package com.detsyky.vocabulary;

import android.os.AsyncTask;
import com.detsyky.vocabulary.Translator.Language;
import com.detsyky.vocabulary.Translator.Translate;



/**
 * Created by naliv on 17.01.2017.
 */

class RetrieveFeedTask extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... words) {
        String word = words[0];

        String translatedText = "";
        try {
            translatedText = Translate.execute(word, Language.ENGLISH, Language.UKRAINIAN);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return translatedText;
    }

    protected void onPostExecute(String feed) {
        super.onPostExecute(feed);
    }
}
