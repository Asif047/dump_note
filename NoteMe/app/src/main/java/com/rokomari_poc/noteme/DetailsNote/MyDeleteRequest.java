package com.rokomari_poc.noteme.DetailsNote;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rokomari_poc.noteme.PostResponseApi;
import com.rokomari_poc.noteme.ShowResponseActivity;

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

public class MyDeleteRequest {

    private String responseDelete="";
    private String response_status="";
    private Context context;
    private DeleteResponseApi deleteResponseApi;

    public MyDeleteRequest(Context context) {
        this.context = context;
    }

    public void deleteData(int id,String account_id)
    {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", id);


        JSONObject parameter = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, parameter.toString());
        Request request = new Request.Builder()
                .url("https://notes-web.herokuapp.com/notes/delete")
                .delete(body)
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



                responseDelete = response.body().string();
                Log.e("response", responseDelete);

                Gson gson = new Gson();
                Type type = new TypeToken<DeleteResponseApi>() {
                }.getType();


                deleteResponseApi = gson.fromJson(responseDelete, type);
                Log.e("####response",deleteResponseApi.getStatus());
                response_status=deleteResponseApi.getStatus().toString();

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


    }

}
