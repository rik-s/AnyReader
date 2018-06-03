package com.example.rik.tryme;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.DemographicsModel;

/**
 * Created by rik on 19-04-2018.
 */

public class Main2Activity extends Activity implements TextToSpeech.OnInitListener {

    Bitmap bp;

    private TextToSpeech tts;
    int count=0;
    Object predictions;
    private final int requestCode = 20;
    Bitmap bitmap;


    String age;
    String gen;
    String cul;
    String gen1;
    String gen2;
    public ClarifaiClient client;
    String[] outa=new String[10];
    int numf;
    StringBuilder dday=new StringBuilder("");
    String zest;
    String abc = "/storage/emulated/0/bluetooth/abc2.jpg";
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);

        iv=(ImageView)findViewById(R.id.imagv);

        client = new ClarifaiBuilder("b41b4f34c9b348d3852100a9cf7e2337").buildSync();

        tts = new TextToSpeech(Main2Activity.this, Main2Activity.this);

//        
        File f = new File(abc);
        saveBitmapToFile(f);
        onImagePicked();

//        scam();
//        Toast.makeText(Main2Activity.this,String.valueOf(dday),Toast.LENGTH_LONG).show();




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
                final DemographicsModel generalModel = client.getDefaultModels().demographicsModel();


                return generalModel.predict()
                        .withInputs(ClarifaiInput.forImage(new File(abc)))
                        .executeSync();

            }

            @Override protected void onPostExecute(ClarifaiResponse response) {
                if (!response.isSuccessful()) {
//                    showErrorSnackbar(R.string.error_while_contacting_api);
                    return;
                }

                predictions = response.rawBody();

                count = lengthfind(String.valueOf(predictions));
                Toast.makeText(Main2Activity.this,"Number of people : "+Integer.toString(count),Toast.LENGTH_SHORT).show();

                if(count==1)
                {
//                    dday.append(" I see one person in front of you. ");
                    jso(String.valueOf(predictions),dday);
                    dday.append(" in front of you. ");
                    dday.append(" Thank You ! ");
                }
                else
                {

                    dday.append(" I see "+count+" people in front of you. ");
                    dday.append(" To your left, ");
                    for(int k=0;k<count;k++)
                    {
                        if(k==(count-1))
                        {
                            jso1(String.valueOf(predictions),k,dday);
                            dday.append(" Thank you ! ");
                        }
                        else
                        {
                            jso1(String.valueOf(predictions),k,dday);
                            if(gencheck(String.valueOf(predictions),k)=="Female"){dday.append(" To her left, ");}
                            else{dday.append(" To his left, ");}
                        }


                    }





                }

//                zest=dday.toString();
//                tts3();
                Toast.makeText(Main2Activity.this,String.valueOf(dday),Toast.LENGTH_LONG).show();
//                Toast.makeText(Main6Activity.this,zest,Toast.LENGTH_LONG).show();

//                jso(String.valueOf(predictions));

//                tts1();

//                imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
//        tts1();
//                del();
//                speakOut();
            }


        }.execute();


//        scam();
//        speakOut();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        speakOut();

        Bitmap bitmapz = BitmapFactory.decodeFile(abc);
        iv.setImageBitmap(bitmapz);

        return true;
    }


    public int lengthfind(String abc)
    {
        int countz=0;
        try
        {
            JSONObject parentobject=new JSONObject(abc);
//            Toast.makeText(MainActivity.this,"TRY 1",Toast.LENGTH_LONG).show();
            JSONArray parentarray=parentobject.getJSONArray("outputs");
            JSONObject finalobject=parentarray.getJSONObject(0);
            JSONObject finalobject1=finalobject.getJSONObject("data");
            JSONArray parentarray1=finalobject1.getJSONArray("regions");
            countz=parentarray1.length();


        }
        catch (JSONException e) {
            Toast.makeText(Main2Activity.this,"CATCH lengthfind",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return countz;

    }

    public void jso(String abcd,StringBuilder sb) {
        try {

            JSONObject parentobject=new JSONObject(abcd);
//            Toast.makeText(MainActivity.this,"TRY 1",Toast.LENGTH_LONG).show();
            JSONArray parentarray=parentobject.getJSONArray("outputs");
            JSONObject finalobject=parentarray.getJSONObject(0);
            JSONObject finalobject1=finalobject.getJSONObject("data");
            JSONArray parentarray1=finalobject1.getJSONArray("regions");
//            numf=parentarray1.length();
            JSONObject finalobject2=parentarray1.getJSONObject(0);
            JSONObject finalobject3=finalobject2.getJSONObject("data");
            JSONObject finalobject4=finalobject3.getJSONObject("face");

            JSONObject finalobject5=finalobject4.getJSONObject("age_appearance");
            JSONArray parentarray2=finalobject5.getJSONArray("concepts");
            JSONObject finalobject6=parentarray2.getJSONObject(0);


            JSONObject gfinalobject5=finalobject4.getJSONObject("gender_appearance");
            JSONArray gparentarray2=gfinalobject5.getJSONArray("concepts");
            JSONObject gfinalobject6=gparentarray2.getJSONObject(0);


            JSONObject cfinalobject5=finalobject4.getJSONObject("multicultural_appearance");
            JSONArray cparentarray2=cfinalobject5.getJSONArray("concepts");
            JSONObject cfinalobject6=cparentarray2.getJSONObject(0);


            outa[0]=finalobject6.getString("name");
            outa[1]=gfinalobject6.getString("name");
            outa[2]=cfinalobject6.getString("name");

//      gen2="masculine";
            if(outa[1].equals("masculine"))
            {gen2 ="Male";}
            else
            {gen2 ="Female";}
            sb.append(" I see a ").append(outa[0]).append(" year old ").append(gen2).append(" belonging to ").append(outa[2]).append(" culture. ");


        } catch (JSONException e) {
            Toast.makeText(Main2Activity.this,"CATCH jso",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }

    public String gencheck(String abcd, int c) {
        try {

            JSONObject parentobject=new JSONObject(abcd);
            JSONArray parentarray=parentobject.getJSONArray("outputs");
            JSONObject finalobject=parentarray.getJSONObject(0);
            JSONObject finalobject1=finalobject.getJSONObject("data");
            JSONArray parentarray1=finalobject1.getJSONArray("regions");

            JSONObject finalobject2=parentarray1.getJSONObject(c);
            JSONObject finalobject3=finalobject2.getJSONObject("data");
            JSONObject finalobject4=finalobject3.getJSONObject("face");

            JSONObject finalobject5=finalobject4.getJSONObject("age_appearance");
            JSONArray parentarray2=finalobject5.getJSONArray("concepts");
            JSONObject finalobject6=parentarray2.getJSONObject(0);


            JSONObject gfinalobject5=finalobject4.getJSONObject("gender_appearance");
            JSONArray gparentarray2=gfinalobject5.getJSONArray("concepts");
            JSONObject gfinalobject6=gparentarray2.getJSONObject(0);


            JSONObject cfinalobject5=finalobject4.getJSONObject("multicultural_appearance");
            JSONArray cparentarray2=cfinalobject5.getJSONArray("concepts");
            JSONObject cfinalobject6=cparentarray2.getJSONObject(0);


            outa[0]=finalobject6.getString("name");
            outa[1]=gfinalobject6.getString("name");
            outa[2]=cfinalobject6.getString("name");


            if(outa[1].equals("masculine"))
                gen2 ="Male";
            else
                gen2 ="Female";


//
        } catch (JSONException e) {
            Toast.makeText(Main2Activity.this,"CATCH gen check",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return gen2;

    }

    public String jso1(String abcd, int c, StringBuilder sb) {
        try {

            JSONObject parentobject=new JSONObject(abcd);
//            Toast.makeText(MainActivity.this,"TRY 1",Toast.LENGTH_LONG).show();
            JSONArray parentarray=parentobject.getJSONArray("outputs");
            JSONObject finalobject=parentarray.getJSONObject(0);
            JSONObject finalobject1=finalobject.getJSONObject("data");
            JSONArray parentarray1=finalobject1.getJSONArray("regions");

            JSONObject finalobject2=parentarray1.getJSONObject(c);
            JSONObject finalobject3=finalobject2.getJSONObject("data");
            JSONObject finalobject4=finalobject3.getJSONObject("face");

            JSONObject finalobject5=finalobject4.getJSONObject("age_appearance");
            JSONArray parentarray2=finalobject5.getJSONArray("concepts");
            JSONObject finalobject6=parentarray2.getJSONObject(0);


            JSONObject gfinalobject5=finalobject4.getJSONObject("gender_appearance");
            JSONArray gparentarray2=gfinalobject5.getJSONArray("concepts");
            JSONObject gfinalobject6=gparentarray2.getJSONObject(0);


            JSONObject cfinalobject5=finalobject4.getJSONObject("multicultural_appearance");
            JSONArray cparentarray2=cfinalobject5.getJSONArray("concepts");
            JSONObject cfinalobject6=cparentarray2.getJSONObject(0);


            outa[0]=finalobject6.getString("name");
            outa[1]=gfinalobject6.getString("name");
            outa[2]=cfinalobject6.getString("name");


            if(outa[1].equals("masculine"))
                gen2 ="Male";
            else
                gen2 ="Female";


//            Toast.makeText(MainActivity.this,Integer.toString(numf),Toast.LENGTH_LONG).show();
//            Toast.makeText(MainActivity.this,outa[0],Toast.LENGTH_LONG).show();
//            Toast.makeText(MainActivity.this,gen2,Toast.LENGTH_LONG).show();
//            Toast.makeText(MainActivity.this,outa[2],Toast.LENGTH_LONG).show();


            sb.append(" I see a ").append(outa[0]).append(" year old ").append(gen2).append(" belonging to ").append(outa[2]).append(" culture. ");

//      Toast.makeText(RecognizeConceptsActivity.this,kk,Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            Toast.makeText(Main2Activity.this,"CATCH jso1",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return gen2;

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


    public int scam()
    {
        try
        {
                if(count==1)
                {
//                    dday.append(" I see one person in front of you. ");
                    jso(String.valueOf(predictions),dday);
                    dday.append(" in front of you. ");
                    dday.append(" Thank You ! ");
                }
                else
                {

                    dday.append(" I see "+count+" people in front of you. ");
                    dday.append(" To your left, ");
                    for(int k=0;k<count;k++)
                    {
                        if(k==(count-1))
                        {
                            jso1(String.valueOf(predictions),k,dday);
                            dday.append(" Thank You ! ");
                        }
                        else
                        {
                            jso1(String.valueOf(predictions),k,dday);
                            if(gencheck(String.valueOf(predictions),k)=="Female"){dday.append(" To her left, ");}
                            else{dday.append(" To his left, ");}
                        }


                    }




                }

            Toast.makeText(Main2Activity.this,String.valueOf(dday),Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(Main2Activity.this,"CATCH scam",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return 0;
    }

    void del() {

        File file = new File(abc);
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
                Toast.makeText(Main2Activity.this," else ",Toast.LENGTH_SHORT).show();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

//        String text = txtText.getText().toString();
        Toast.makeText(Main2Activity.this," void ",Toast.LENGTH_SHORT).show();
        tts.speak(String.valueOf(dday), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
