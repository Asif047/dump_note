package com.rokomari_poc.noteme.Register;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rokomari_poc.noteme.LogIn.LogInActivity;

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

public class RegisterMyPostRequest {

    private String responsePost = "";
    private RegisterPostResponseApi registerPostResponseApi;
    private String response_status;
    private Context context;

    //private PostActivity postActivity=new PostActivity();

    public RegisterMyPostRequest(Context context) {
        this.context = context;
    }


    public void postData(String fname, String lname, String mobile, String email, String password) {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> params = new HashMap<String, String>();
        params.put("first_name", "" + fname);
        params.put("last_name", "" + lname);
        params.put("email", "" + email);
        params.put("mobile", "" + mobile);
        params.put("password", "" + password);

        JSONObject parameter = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, parameter.toString());
        Request request = new Request.Builder()
                .url("https://notes-web.herokuapp.com/register")
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

                if (RegisterActivity.pDialog.isShowing())
                    RegisterActivity.pDialog.dismiss();

                responsePost = response.body().string();
                Log.e("response", responsePost);

                Gson gson = new Gson();
                Type type = new TypeToken<RegisterPostResponseApi>() {
                }.getType();


                registerPostResponseApi = gson.fromJson(responsePost, type);
                response_status = String.format("" + registerPostResponseApi.getStatus());


                Log.d("RESPONSE POST: ", response_status);

                if(response_status.equals("registered"))
                {
                    Intent intent = new Intent(context, LogInActivity.class);

                    context.startActivity(intent);
                }


            }

        });

    }

}
