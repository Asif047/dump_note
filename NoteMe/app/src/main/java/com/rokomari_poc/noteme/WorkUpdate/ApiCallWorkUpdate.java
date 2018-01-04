package com.rokomari_poc.noteme.WorkUpdate;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiCallWorkUpdate{

    public String GET(OkHttpClient client,String url) throws IOException
    {
        Request request=new Request.Builder()
                .url(url)
                .header("accessKey","")
                .build();

        Response response=client.newCall(request).execute();
        return response.body().string();
    }

}
