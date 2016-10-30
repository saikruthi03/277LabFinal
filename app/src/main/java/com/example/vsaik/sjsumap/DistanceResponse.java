package com.example.vsaik.sjsumap;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by vsaik on 10/25/2016.
 */
public class DistanceResponse extends AsyncTask<String, Void, String> {


    Context appContext;
    Result result;
    List<String> finalresult = new ArrayList<String>();
    public DistanceResponse(Context appContext) {
        this.appContext = appContext;
        result= (Result) appContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String distanceResult) {
        super.onPostExecute(distanceResult);
        if (distanceResult != null) {
            //String res[] = distanceResult.split(",");
           // if (res[2].equals("driving")) {
           //     finalresult.add(distanceResult);
           //     result.setResult(finalresult);
          //  }else if(res[2].equals("walking")){
                finalresult.add(distanceResult);
                Log.d("Final result",finalresult.size()+"");
                result.setResult(finalresult);
           // }

    }
        else
            Toast.makeText(appContext, "Server Busy!!Please try again later", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            String query = url.getQuery();
            Map<String, String> map = getQueryMap(query);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode=con.getResponseCode();
            if(statuscode==HttpURLConnection.HTTP_OK)
            {
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb=new StringBuilder();
                String line=br.readLine();
                while(line!=null)
                {
                    sb.append(line);
                    line=br.readLine();
                }
                String json=sb.toString();
                Log.d("JSON",json);
                JSONObject root=new JSONObject(json);
                JSONArray array_rows=root.getJSONArray("rows");
                Log.d("JSON","array_rows:"+array_rows);
                JSONObject object_rows=array_rows.getJSONObject(0);
                Log.d("JSON","object_rows:"+object_rows);
                JSONArray array_elements=object_rows.getJSONArray("elements");
                Log.d("JSON","array_elements:"+array_elements);
                JSONObject  object_elements=array_elements.getJSONObject(0);
                Log.d("JSON","object_elements:"+object_elements);
                JSONObject object_duration=object_elements.getJSONObject("duration");
                JSONObject object_distance=object_elements.getJSONObject("distance");

                Log.d("JSON","object_duration:"+object_duration);
                String first = object_duration.getString("value")+","+object_distance.getString("value")+","+map.get("mode");
                return first;

            }
        } catch (MalformedURLException e) {
            Log.d("error", "error1");
        } catch (IOException e) {
            Log.d("error", "error2");
        } catch (JSONException e) {
            Log.d("error","error3");
        }catch(Exception e){

        }


        return null;
    }

    public static Map<String, String> getQueryMap(String query)
    {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String key = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(key, value);
        }
        return map;
    }

}


interface Result{
    public void setResult(List<String> distanceResult);
}