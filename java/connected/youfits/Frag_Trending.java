package connected.youfits;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import connected.youfit.R;

/**
 * Created by admin on 10/12/2016.
 */
public class Frag_Trending extends android.support.v4.app.Fragment implements View.OnClickListener{
    public Context context;
    Activity activity;
    ListView list;
    ArrayList<SingleRow> listItems;
    public HomeAdapter listAdapter;

    public Frag_Trending(Activity con) {
        activity=con;



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frag_trending,null);
        list=(ListView)v.findViewById(R.id.list);
        listItems=new ArrayList<SingleRow>();
        listAdapter=new HomeAdapter(activity,listItems);

        FloatingActionButton fbtn=(FloatingActionButton)v.findViewById(R.id.fbtn);
        fbtn.setOnClickListener(this);
        try {
            String []cond={"3"};

            StringBuffer buff=new Home().getTask(cond);
            json_parsing(buff.toString());
        } catch (Exception e) {
            e.printStackTrace();

        }
        list.setAdapter(listAdapter);

        return v;
    }





    public int json_parsing(String path) throws JSONException {
        String[]name;
        String[]dp;
        String[]qn;
        String[] date;
        String[] ans;
        String []votes;
        JSONArray jsonArray = new JSONArray(path);


        try {
            int size=jsonArray.length();
            /* parsing JSON and filling views */
            name=new String[size];
            dp=new String[size];
            qn=new String[size];
            date=new String[size];
            ans=new String[size];
            votes=new String[size];

            // ---print out the content of the json feed---
            for ( int i = 0; i <jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                /*dynamic text */
                name[i]=jsonObject.getString("var_UserName");
                qn[i]=jsonObject.getString("var_qn");
                ans[i]=jsonObject.getString("var_Ans");
                dp[i]=jsonObject.getString("ans_user_dp");
                votes[i]=jsonObject.getString("num_upvotes");
                date[i]=jsonObject.getString("dt_date_time");



            }

            // img=new Home().getTaskImg(image);
            // dp_img=new Home().getTaskImg(dp);


            ////////////////////////////////////////////////////////DO WHILE LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOP

            for (int i=0; i<name.length;i++){
                Log.i("green", "inside loop for obj init");
                SingleRow temp=new SingleRow(name[i],qn[i],ans[i],dp[i],votes[i],date[i]);
                listItems.add(0,temp);
            }

            Log.i("green", "4");


            listAdapter.notifyDataSetChanged();
            Log.i("green", "5");

           // Log.i("green",image[0]+","+image[1]);


        } catch (Exception e) {
            Log.i("green", " EXCEPTION======= "+ e.getMessage()+"");
        }

        return jsonArray.length();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fbtn:
                Toast.makeText(context,"Clicked write question",Toast.LENGTH_SHORT).show();
                break;



        }

    }
}
