package connected.youfits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import connected.youfit.R;

/**
 * Created by admin on 1/24/2016.
 */
public class Home extends ActionBarActivity {

    StringBuffer buffer;
    HttpResponse response;

    HttpClient httpclient;
    InputStream inputStream;

    byte[] data;
    Bitmap[] bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // LL = (LinearLayout) findViewById(R.id.lin1);
        /*http post----------------*/
        Log.i("green", "call fn");

        //getTask(2);


    }


    private class ImgDownload extends AsyncTask<String, String, Bitmap[]> {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "GreenestTest");
        String path = folder.getAbsolutePath();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        protected Bitmap[] doInBackground(String... args) {
            Log.i("green", "inside async 1 downloading image 1." + args[0]);

            for (int i = 0; i < args.length; i++) {//
                if (args[i].equalsIgnoreCase("http://192.168.1.35:80/connected.php")) {
                    Log.d("green", "null");

                    bitmap[i] = null;
                } else {
                    ////check whether its in the folder///////////////////////////////////
                    File found = new File("/sdcard/GreenestTest/", args[i].substring(58).toString().trim());

                    if (found.exists()) {
                        try {
                            Log.d("green", "file exists------------------------/*/**//*/**/*/*");

                            bitmap[i] = BitmapFactory.decodeStream(new FileInputStream(found));
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }


                    } else {
                        Log.d("green", "not exist");

                        try {
                            bitmap[i] = BitmapFactory.decodeStream((InputStream) new URL(args[i]).getContent());
                            Log.d("green", "image downloaded");

                            createDirectoryAndSaveFile(bitmap[i], args[i].substring(58));


                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }


                    }
                }


            }

            Log.i("green", "ending async 1 downloading image");


            return bitmap;
        }

        protected void onPostExecute(Bitmap image[]) {
            Log.i("green", "onPost");

        }

        private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {


            File file = new File(new File("/sdcard/GreenestTest/"), fileName);

                try {
                    Log.i("green", "saved file");

                    FileOutputStream out = new FileOutputStream(file);
                    imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }



        }
    }

        private class Asyn extends AsyncTask<String, Void, Void> {

            List<NameValuePair> nameValuePairs;

            @Override
            protected Void doInBackground(String... params) {

                try {
                    HttpClient httpclient = new DefaultHttpClient();
//
                    HttpPost httppost = new HttpPost("http://192.168.1.35:1080/connected.php");
                    Log.i("green", "url" + params[0].toString());
                    nameValuePairs = new ArrayList<NameValuePair>(3);
                    nameValuePairs.add(new BasicNameValuePair("condition", params[0].toString()));
                    Log.i("green", "passing cond value (in Questions.java");



                    ///////////////////// need an if else to give inputs for php
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    // Execute HTTP Post Request
                    response = httpclient.execute(httppost);
                    Log.i("green", "resp");
                    inputStream = response.getEntity().getContent();
                    Log.i("green", "getcontent");
                    data = new byte[256];
                    buffer = new StringBuffer();
                    int len = 0;
                    while (-1 != (len = inputStream.read(data))) {
                        buffer.append(new String(data, 0, len));
                    }
                    ////////////////////////////////////////////          json_parsing(buffer.toString());
                    Log.i("green", "buffer output :" + buffer.toString());
                } catch (Exception e) {
                    // Toast.makeText(getApplicationContext(), "error in http" + e.toString(), Toast.LENGTH_LONG).show();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_LONG).show();


            }


        }


        public Bitmap[] getTaskImg(String img[]) throws ExecutionException, InterruptedException {
            bitmap = new Bitmap[img.length];


            new ImgDownload().execute(img).get();

            return bitmap;


        }


        public StringBuffer getTask(String a[]) throws ExecutionException, InterruptedException {
            Log.i("green", "enterd gettask for soln...############################." + a[0]);

            new Asyn().execute(a).get();
            return buffer;


        }


    }

