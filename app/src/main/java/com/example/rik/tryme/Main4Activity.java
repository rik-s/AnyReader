package com.example.rik.tryme;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by rik on 26-04-2018.
 */

public class Main4Activity extends Activity implements TextToSpeech.OnInitListener {

    int wida;
    TextView txtvv;
    StringBuilder ss = new StringBuilder();
    ArrayList<Integer> head =new ArrayList<Integer>();
    ArrayList<Integer> para =new ArrayList<Integer>();
    ArrayList<Integer> pnt =new ArrayList<Integer>();
    ArrayList<Integer> lasta =new ArrayList<Integer>();
    private TextRecognizer detector;
    int max;
    TextToSpeech tts;
    String txt = "/storage/emulated/0/bluetooth/txt.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4_main);
        txtvv = (TextView)findViewById(R.id.txtv);



        detector = new TextRecognizer.Builder(getApplicationContext()).build();

        tts = new TextToSpeech(Main4Activity.this, Main4Activity.this);
        CheckMax();

//        Toast.makeText(Main4Activity.this," Maximum width of block : "+ String.valueOf(max), Toast.LENGTH_SHORT).show();

//        MakeThem();

        Toast.makeText(Main4Activity.this, " Done making ", Toast.LENGTH_SHORT).show();

//        guess();

        Toast.makeText(Main4Activity.this, " Done predicting ", Toast.LENGTH_SHORT).show();




    }

    protected void CheckMax() {

//            launchMediaScanIntent();
        try {
//                Bitmap bitmap = decodeBitmapUri(this, imageUri);
            Bitmap bitmap = BitmapFactory.decodeFile(txt);

            if (detector.isOperational() && bitmap != null) {
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> textBlocks = detector.detect(frame);

                max = 0;
                for (int index = 0; index < textBlocks.size(); index++) {
                    //extract scanned text blocks here
                    TextBlock tBlock = textBlocks.valueAt(index);

                    Point[] abc = tBlock.getCornerPoints();
                    int wid = (abc[1].x) - (abc[0].x);
                    if ( wid >= max ){  max = wid;}


                }


                Toast.makeText(Main4Activity.this,"Maximum width of block : "+String.valueOf(max), Toast.LENGTH_LONG).show();

                if (textBlocks.size() == 0) {
                    Toast.makeText(Main4Activity.this, "Scan Failed: Found nothing to scan", Toast.LENGTH_SHORT).show();
                } else {

//                    MakeThem();
                    CheckMax1();

                }
            } else {
                Toast.makeText(Main4Activity.this,  "Could not set up the detector ! ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                    .show();
//            Log.e(LOG_TAG, e.toString());
        }



    }

//    protected void MakeThem() {
//
//        try {
////                Bitmap bitmap = decodeBitmapUri(this, imageUri);
//            Bitmap bitmap = BitmapFactory.decodeFile(txt);
//            if (detector.isOperational() && bitmap != null) {
//                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
//                SparseArray<TextBlock> textBlocks = detector.detect(frame);
//                String blocks = "";
//                String lines = "";
//                String words = "";
//                String zest1 = "";
//
//                for (int index = 0; index < textBlocks.size(); index++) {
//                    //extract scanned text blocks here
//                    TextBlock tBlock = textBlocks.valueAt(index);
//
//                    Point [] abc = tBlock.getCornerPoints();
//
//                    wida = (abc[1].x) - (abc[0].x);
//                    if ( wida < ( max * 0.7 ) )
//                    {
//                        head.add(abc[0].y);
//                    }
//                    if ( ( wida >= ( max * 0.7 ) ) && ( wida < ( max * 0.95 )) )
//                    {
//                        pnt.add(abc[0].y);
//                    }
//                    if  ( wida >= ( max * 0.95 ) )
//                    {
//                        para.add(abc[0].y);
//                    }
//
//
//                }
//
//
//                lasta.addAll(head);
//                lasta.addAll(pnt);
//                lasta.addAll(para);
//                Collections.sort(lasta);
//                Collections.sort(head);
//                Collections.sort(pnt);
//                Collections.sort(para);
//
//                Toast.makeText(Main4Activity.this,String.valueOf(lasta.get(0))
//                        +"\n"+String.valueOf(lasta.get(1))
//                        +"\n"+String.valueOf(lasta.get(2))
//                        +"\n"+String.valueOf(lasta.get(3)), Toast.LENGTH_SHORT).show();
//
//
//                if (textBlocks.size() == 0) {
//                    Toast.makeText(Main4Activity.this, "Scan Failed: Found nothing to scan", Toast.LENGTH_SHORT).show();
//                } else {
//
////                    guess(0);
//                    CheckMax1();
//                }
//            } else {
//                Toast.makeText(Main4Activity.this,  "Could not set up the detector ! ", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
//                    .show();
////            Log.e(LOG_TAG, e.toString());
//        }
//
//
//
//    }

    protected void CheckMax1() {

//            launchMediaScanIntent();
        try {
//                Bitmap bitmap = decodeBitmapUri(this, imageUri);
            Bitmap bitmap = BitmapFactory.decodeFile(txt);

            if (detector.isOperational() && bitmap != null) {
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> textBlocks = detector.detect(frame);

//                max = 0;
                for (int index = 0; index < textBlocks.size(); index++) {
                    //extract scanned text blocks here
                    TextBlock tBlock = textBlocks.valueAt(index);

//                    ss.append(tBlock.getValue());
//                    Toast.makeText(Main3Activity.this,tBlock.getValue(), Toast.LENGTH_LONG).show();
                    if(index == 0) { }
                    else {ss.append(" Under which, we have "+"\n");}
                    Point[] abc = tBlock.getCornerPoints();
                    int wid = (abc[1].x) - (abc[0].x);
                    if ( wid >= (max*0.85) )
                    {
                        ss.append(" Part of the Paragraph "+"\n");

                        ss.append(tBlock.getValue());
                    }
                    else if ( wid >= (max*0.65) && wid <(max*0.85) )
                    {

                        ss.append(" Part of the Point "+"\n");
                        ss.append(tBlock.getValue());

                    }
                    else
                    {
                        ss.append(" Heading "+"\n");
                        ss.append(tBlock.getValue());

                    }


                }


//                Toast.makeText(Main3Activity.this,"Maximum width of block : "+String.valueOf(max), Toast.LENGTH_LONG).show();

                if (textBlocks.size() == 0) {
                    Toast.makeText(Main4Activity.this, "Scan Failed: Found nothing to scan", Toast.LENGTH_SHORT).show();
                } else {

//                        tt.setText(blocks);
                    txtvv.setText(ss.toString());
                    Toast.makeText(Main4Activity.this,ss.toString(), Toast.LENGTH_LONG).show();


                }
            } else {
                Toast.makeText(Main4Activity.this,  "Could not set up the detector ! ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                    .show();
//            Log.e(LOG_TAG, e.toString());
        }



    }


//    protected void guess(int index) {
//
//        int top = lasta.size();
//        try {
////                Bitmap bitmap = decodeBitmapUri(this, imageUri);
//            Bitmap bitmap = BitmapFactory.decodeFile("/storage/emulated/0/patna2.jpg");
//            if (detector.isOperational() && bitmap != null) {
//                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
//                SparseArray<TextBlock> textBlocks = detector.detect(frame);
//                String blocks = "";
//                String lines = "";
//                String words = "";
//                String zest1 = "";
//
//                TextBlock tBlock = textBlocks.valueAt(index);
////                TextBlock tBlock1 = textBlocks.;
//
//                ss.append(" Reading out "+" \n "+" heading "+" \n ");
//                ss.append(" \n "+tBlock.getValue()+" \n ");
//
//                int tag1,tag2;
//                tag1 = head.get(index);
//                if(1 == head.size()) {   tag2=1000;}
//                else {    tag2 = head.get(index + 1);}
//
//
//                for (int i = (tag1 + 1) ; i < tag2 ; i++) {
//
//                    //extract scanned text blocks here
//
//                    if ( (lasta.contains(i)) && (pnt.contains(i)) )
//                    {
//                        ss.append("\n"+" under "+" which "+"\n"+" there is a, "+"\n");
//                        TextBlock tBlock1 = textBlocks.valueAt(lasta.indexOf(i));
//
//                        ss.append("\n"+" point "+"\n");
//
//                        ss.append("\n"+tBlock1.getValue()+"\n");
//                    }
//                    if ( (lasta.contains(i)) && (para.contains(i)) )
//                    {
//                        ss.append("\n"+" under "+" which "+"\n"+" there is a, "+"\n");
//                        TextBlock tBlock2 = textBlocks.valueAt(lasta.indexOf(i));
//
//                        ss.append("\n"+" paragraph "+"\n");
//                        ss.append("\n"+tBlock2.getValue()+"\n");
//
//
//                    }
//
//
//
////                    TextBlock tBlock = textBlocks.valueAt(index);
//
//                }
//
//
////                Toast.makeText(MainActivity.this,String.valueOf(max), Toast.LENGTH_SHORT).show();
//
//                if (textBlocks.size() == 0) {
//                    Toast.makeText(Main4Activity.this, "Scan Failed: Found nothing to scan", Toast.LENGTH_SHORT).show();
//                } else {
//
////                    tt.setText(ss.toString());
//                }
//            } else {
//                Toast.makeText(Main4Activity.this,  "Could not set up the detector ! ", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
//                    .show();
////            Log.e(LOG_TAG, e.toString());
//        }
//
//
//
//    }

    void del() {

        File file = new File(txt);
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
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        speakOut();

        return true;
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
                Toast.makeText(Main4Activity.this," else ",Toast.LENGTH_SHORT).show();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

//        String text = txtText.getText().toString();
        Toast.makeText(Main4Activity.this," void ",Toast.LENGTH_SHORT).show();
        tts.speak(String.valueOf(ss), TextToSpeech.QUEUE_FLUSH, null);
    }

}
