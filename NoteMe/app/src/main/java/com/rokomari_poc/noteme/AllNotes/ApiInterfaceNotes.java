package com.rokomari_poc.noteme.AllNotes;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;


public interface ApiInterfaceNotes {

    @Headers("token:68e109f0f40ca72a15e05cc22786f8e6")
    @GET("/notes")
    Call<List<ModelNotes>> getNotes();
}
