package com.rokomari_poc.noteme.LogIn;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rokomari_poc.noteme.Home.HomeActivity;
import com.rokomari_poc.noteme.Register.RegisterActivity;
import com.rokomari_poc.noteme.Register.RegisterPostResponseApi;

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

public class LogInMyPostRequest {

    private String responsePost = "";
    private LogInPostResponseApi logInPostResponseApi;
    private String response_status;
    private Context context;

    String logInResponse;

    //private PostActivity postActivity=new PostActivity();

    public LogInMyPostRequest(Context context) {
        this.context = context;
    }


    public void postData(String email, String password) {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "" + email);
        params.put("password", "" + password);

        JSONObject parameter = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, parameter.toString());
        Request request = new Request.Builder()
                .url("https://notes-web.herokuapp.com/login")
                .post(body)
                .header("token","68e109f0f40ca72a15e05cc22786f8e6")
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Log.e("response", call.request().body().toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

//                if (RegisterActivity.pDialog.isShowing())
//                    RegisterActivity.pDialog.dismiss();

                responsePost = response.body().string();
                Log.e("response", responsePost);

                Gson gson = new Gson();
                Type type = new TypeToken<LogInPostResponseApi>() {
                }.getType();


                logInPostResponseApi = gson.fromJson(responsePost, type);
                response_status = String.format("" + logInPostResponseApi.getStatus());


                Log.d("RESPONSE POST: ", response_status);

                if(response_status.equals("logged In"))
                {
                   // Toast.makeText(context,response_status,Toast.LENGTH_SHORT).show();
                    logInResponse="logged In";
                    Intent intent = new Intent(context, HomeActivity.class);

                    context.startActivity(intent);
                }

                else
                {
                   // logInResponse="log in failed";
                    //Toast.makeText(c,"Incorrect email or password",Toast.LENGTH_SHORT).show();
                }


            }

        });


    }


}
