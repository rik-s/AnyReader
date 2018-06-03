package com.example.rik.tryme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.DemographicsModel;
import clarifai2.dto.prediction.Concept;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    Object predictions;

    int count=0;
    String age;
    String gen;
    String cul;
    String gen1;
    String gen2;
    public ClarifaiClient client;
    String[] outa=new String[10];
    int numf;
    StringBuilder ddayc=new StringBuilder("");
    String zest;
    String first;
    String second;
    String third;
    String abcdef = "/storage/emulated/0/bluetooth/abc2.jpg";

    ImageView imgvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgvv = (ImageView)findViewById(R.id.imgv);

        tts = new TextToSpeech(MainActivity.this, MainActivity.this);
        client = new ClarifaiBuilder("b41b4f34c9b348d3852100a9cf7e2337").buildSync();
        File f = new File(abcdef);
        saveBitmapToFile(f);
        onImagePicked();

    }

    public File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private void onImagePicked() {
        // Now we will upload our image to the Clarifai API




        new AsyncTask<Void, Void, ClarifaiResponse>() {
            @Override protected ClarifaiResponse doInBackground(Void... params) {
                // The default Clarifai model that identifies concepts in images
                final ConceptModel generalModel = client.getDefaultModels().generalModel();

                return generalModel.predict()
                        .withInputs(ClarifaiInput.forImage(new File(abcdef)))
                        .executeSync();

            }

            @Override protected void onPostExecute(ClarifaiResponse response) {

                if (!response.isSuccessful()) {
//                    showErrorSnackbar(R.string.error_while_contacting_api);
                    return;
                }

                final Object predictions = response.rawBody();

                jso(String.valueOf(predictions),ddayc);


                Toast.makeText(MainActivity.this,String.valueOf(ddayc),Toast.LENGTH_LONG).show();
            }


        }.execute();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        Bitmap bitmapz = BitmapFactory.decodeFile(abcdef);
        imgvv.setImageBitmap(bitmapz);


        speakOut();

//        switch (eventaction) {
//            case MotionEvent.ACTION_DOWN:
//                Toast.makeText(this, "ACTION_DOWN AT COORDS "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
//                isTouch = true;
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                Toast.makeText(this, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
//                break;
//
//            case MotionEvent.ACTION_UP:
//                Toast.makeText(this, "ACTION_UP "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
//                break;
//        }
        return true;
    }

    public void jso(String abcde,StringBuilder sbc) {
        try {

            JSONObject parentobject=new JSONObject(abcde);
            JSONArray parentarray=parentobject.getJSONArray("outputs");
            JSONObject finalobject=parentarray.getJSONObject(0);
            JSONObject finalobject1=finalobject.getJSONObject("data");
            JSONArray parentarray1=finalobject1.getJSONArray("concepts");
            JSONObject finalobject2=parentarray1.getJSONObject(0);

            first = finalobject2.getString("name");

            JSONObject finalobject4=parentarray1.getJSONObject(1);
            second = finalobject4.getString("name");

            JSONObject finalobject6=parentarray1.getJSONObject(2);
            third = finalobject6.getString("name");

            sbc.append(" What it looks to me is, \n").append(first).append("\n or, \n").append(second).append("\n or, \n").append(third).append("\n Thank You !! ");


        } catch (JSONException e) {
            Toast.makeText(MainActivity.this,"CATCH jso",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


//    public void del()
//    {
//        File dir = new File("/storage/emulated/0/bluetooth/");
//        try {
//            FileUtils.deleteDirectory(dir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    void del() {

        File file = new File(abcdef);
        boolean deleted = file.delete();
//        deleteFile("/storage/emulated/0/bluetooth/abcd.jpg");


    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            del();
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
                Toast.makeText(MainActivity.this," else ",Toast.LENGTH_SHORT).show();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

//        String text = txtText.getText().toString();
        Toast.makeText(MainActivity.this," void ",Toast.LENGTH_SHORT).show();
        tts.speak(String.valueOf(ddayc), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
