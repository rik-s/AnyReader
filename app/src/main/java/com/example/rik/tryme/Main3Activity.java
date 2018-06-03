package com.example.rik.tryme;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by rik on 26-04-2018.
 */

public class Main3Activity extends Activity implements TextToSpeech.OnInitListener {


    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_main);

//        TextToSpeech tts = new TextToSpeech(Main3Activity.this, Main3Activity.this);
//        tts.setLanguage(Locale.US);
//        tts.speak("Text to say aloud", TextToSpeech.QUEUE_ADD, null);


        tts = new TextToSpeech(Main3Activity.this, Main3Activity.this);

        Toast.makeText(Main3Activity.this," start ",Toast.LENGTH_SHORT).show();
        speakOut();
        Toast.makeText(Main3Activity.this," end ",Toast.LENGTH_SHORT).show();
//        // button on click event
//        btnSpeak.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                speakOut();
//            }
//
//        });
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

                speakOut();
                Toast.makeText(Main3Activity.this," else ",Toast.LENGTH_SHORT).show();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

//        String text = txtText.getText().toString();
        Toast.makeText(Main3Activity.this," void ",Toast.LENGTH_SHORT).show();
        tts.speak(" This is it. ", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}