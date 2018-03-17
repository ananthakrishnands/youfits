package connected.youfits;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import connected.youfit.R;

/**
 * Created by admin on 10/14/2016.
 */
public class Fun extends ActionBarActivity {
    TextView tv1;
    String s="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fun);
        tv1 = (TextView) findViewById(R.id.tv1);
        String[] arr = {"2"};
        try {
            StringBuffer buff = new Home().getTask(arr);
            Log.i("hack",buff.toString());
            json_parsing(buff.toString());
            Log.i("hack", "aftr");

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("hack", "**"+e.getMessage());

        }
    }


    public void json_parsing(String path) throws JSONException {

        JSONArray jsonArray = new JSONArray(path);

        Log.i("hack","nside");

        try {

            /* parsing JSON and filling views */


            // ---print out the content of the json feed---
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                /*dynamic text */
                Log.i("hack","nside************************************");

                s = jsonObject.getString("model_id")
                        + " " + jsonObject.getString("brand_id")
                        + " " + jsonObject.getString("ModelName").toString()
                        + " " + jsonObject.getString("bodytype").toString()
                        + " " + jsonObject.getString("mileage").toString()
                        + " " + jsonObject.getString("engine").toString()
                        + " " + jsonObject.getString("transmission").toString()
                +"\n";

                tv1.append(s);

            }


            // img=new Home().getTaskImg(image);
            // dp_img=new Home().getTaskImg(dp);


            ////////////////////////////////////////////////////////DO WHILE LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOP


        } catch (Exception e) {

        }
    }
}
