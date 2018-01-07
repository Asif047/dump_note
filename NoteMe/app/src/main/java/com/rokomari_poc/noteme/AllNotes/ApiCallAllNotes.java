package com.rokomari_poc.noteme.AllNotes;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiCallAllNotes {

    public String GET(OkHttpClient client,String url,String account_id)throws IOException
    {
        Request request=new Request.Builder()
                .url(url)
                .header("token","68e109f0f40ca72a15e05cc22786f8e6")
                .header("jwt",account_id)
                .build();
        Response response=client.newCall(request).execute();
        return response.body().string();
    }
}
