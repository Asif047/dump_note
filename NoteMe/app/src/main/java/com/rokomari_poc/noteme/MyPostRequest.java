package com.rokomari_poc.noteme;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyPostRequest {


    private String responsePost = "";
    private String response_status="";
    private Context context;
    private PostResponseApi postResponseApi;

    //private PostActivity postActivity=new PostActivity();

    public MyPostRequest(Context context) {
        this.context = context;
    }

    public String postData(String timestamp, String status, String title, String detail, String url, String mail,
                         String phone, String category,String account_id) {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> params = new HashMap<String, String>();
        params.put("timestamp", "" + timestamp);
        params.put("status", "" + status);
        params.put("title", "" + title);
        params.put("detail", "" + detail);
        params.put("url", "" + url);
        params.put("mail", "" + mail);
        params.put("phone", "" + phone);
        params.put("category", "" + category);

        JSONObject parameter = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, parameter.toString());
        Request request = new Request.Builder()
                .url("http://192.168.11.205:5001/notes/new")
                .post(body)
                .header("token","68e109f0f40ca72a15e05cc22786f8e6")
                .header("jwt",account_id)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Log.e("response", call.request().body().toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {



                responsePost = response.body().string();
                Log.e("response", responsePost);

                Gson gson = new Gson();
                Type type = new TypeToken<PostResponseApi>() {
                }.getType();


                postResponseApi = gson.fromJson(responsePost, type);
                Log.e("####response",postResponseApi.getStatus());
                response_status=postResponseApi.getStatus().toString();

//                responseFromPost_code = String.format("" + postResponseApis.getCode());
//                responseFromPost_http_verb = postResponseApis.getHttp_verb();
//                responseFromPost_message = postResponseApis.getMessage();

                Log.e("RESPONSE POST: ", ""+response_status);

               // Toast.makeText(context,response_status,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, ShowResponseActivity.class);
                intent.putExtra("response_status", response_status);
                context.startActivity(intent);

            }

        });

        return response_status;

    }


}
