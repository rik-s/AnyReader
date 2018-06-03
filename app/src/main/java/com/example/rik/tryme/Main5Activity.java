package com.example.rik.tryme;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by rik on 27-04-2018.
 */

public class Main5Activity extends Activity implements TextToSpeech.OnInitListener {

    TextToSpeech tts;
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    String sendtxt;
    String zest=" Welcome to the application ! I will guide you through the whole process . Say one for " +"\n"+
            " The Text Reader " +"\n"+
            " Or two for " +"\n"+
            "Object Recognition " +"\n"+
            "Or say " +"\n"+
            "three for " +"\n"+
            " Demographics Support " +"\n"+"\n"+
            " Say when prompted to ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity5_main);
        tts = new TextToSpeech(Main5Activity.this, Main5Activity.this);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);



        // hide the action bar
//        getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
//        Toast.makeText(Main5Activity.this,"catch 1",Toast.LENGTH_LONG).show();
//        sendto(sendtxt);
//        Toast.makeText(Main5Activity.this,"catch 2",Toast.LENGTH_LONG).show();

    }

    public void sendto (String abcd)
    {
        Toast.makeText(Main5Activity.this,"TRY 1",Toast.LENGTH_LONG).show();
        if (abcd.equals("1") || abcd.equals("one"))
        {
            Toast.makeText(Main5Activity.this,"TRY 2",Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(Main5Activity.this, Main4Activity.class);
//            myIntent.putExtra("key", value); //Optional parameters
            Main5Activity.this.startActivity(myIntent);
            Toast.makeText(Main5Activity.this,"TRY 3",Toast.LENGTH_LONG).show();
        }
        else if (abcd.equals("2") || abcd.equals("tu") || abcd.equals("two") || abcd.equals("too") || abcd.equals("to"))
        {
            Intent myIntent = new Intent(Main5Activity.this, MainActivity.class);
//            myIntent.putExtra("key", value); //Optional parameters
            Main5Activity.this.startActivity(myIntent);
        }
        else if (abcd.equals("3") || abcd.equals("three") || abcd.equals("free") || abcd.equals("shree") || abcd.equals("shri") || abcd.equals("Shri") || abcd.equals("Shree"))
        {
            Intent myIntent = new Intent(Main5Activity.this, Main2Activity.class);
//            myIntent.putExtra("key", value); //Optional parameters
            Main5Activity.this.startActivity(myIntent);
        }
        else
        {

        }
    }


    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    sendtxt=result.get(0);
                    sendto(sendtxt);

                }
                break;
            }

        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

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
                Toast.makeText(Main5Activity.this," else ",Toast.LENGTH_SHORT).show();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

//        String text = txtText.getText().toString();
        Toast.makeText(Main5Activity.this," void ",Toast.LENGTH_SHORT).show();
        tts.speak(zest, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
