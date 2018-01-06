package com.rokomari_poc.noteme.WorkUpdate;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiCallWorkUpdate{

    public String GET(OkHttpClient client,String url,String account_id,String timestamp) throws IOException
    {
        Request request=new Request.Builder()
                .url(url)
                .header("token","68e109f0f40ca72a15e05cc22786f8e6")
                .header("jwt",account_id)
                .header("timestamp",timestamp)
                .build();

        Response response=client.newCall(request).execute();
        return response.body().string();
    }

}
