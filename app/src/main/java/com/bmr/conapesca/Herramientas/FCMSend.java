package com.bmr.conapesca.Herramientas;

import android.content.Context;
import android.os.StrictMode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCMSend {
    private static String BASE_URL = "https://fcm.googleapis.com/fcm/send";
    private static String SERVER_KEY = "key=AAAA4Fy3jh0:APA91bGjDveYP0zYrili0gBBh5TVr4IyOPgpO9x9NJ5caEIhNqisPA4V7CA6HQbfZuw090RHSlnEqntZd2kPikvkCBQrpYEW8djAaSIfR374Xc7KO7MgmX5bcis86Smi3ZlVfJDoeqGy";

    public static void pushNotification(Context context,String token,String title,String message){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("to",token);
            JSONObject notification = new JSONObject();
            notification.put("title",title);
            notification.put("body",message);
            jsonObject.put("notification",notification);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("FCM :"+response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String ,String> getHeaders() throws AuthFailureError{
                    Map<String,String> params = new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("Authorization",SERVER_KEY);
                    return  params;
                }
            };
            queue.add(jsonObjectRequest);
        }catch (Exception E){

        }
    }
}
