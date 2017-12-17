package com.rokomari_poc.noteme.DetailsNote;


import com.rokomari_poc.noteme.AllNotes.ModelNotes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterfaceDetailsNote {

   @GET()
    Call<ModelNotes>getDetailsNote(@Url String urlString);

}
