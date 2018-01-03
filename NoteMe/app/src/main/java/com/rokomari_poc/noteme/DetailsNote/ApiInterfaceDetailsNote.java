package com.rokomari_poc.noteme.DetailsNote;


import com.rokomari_poc.noteme.AllNotes.ModelNotes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ApiInterfaceDetailsNote {

    @Headers("token:68e109f0f40ca72a15e05cc22786f8e6")
    @GET()
    Call<ModelNotes>getDetailsNote(@Url String urlString);

}
