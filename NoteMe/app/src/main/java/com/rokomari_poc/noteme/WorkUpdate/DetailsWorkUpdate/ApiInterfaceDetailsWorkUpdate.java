package com.rokomari_poc.noteme.WorkUpdate.DetailsWorkUpdate;


import com.rokomari_poc.noteme.AllNotes.ModelNotes;
import com.rokomari_poc.noteme.WorkUpdate.ModelWorkUpdate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ApiInterfaceDetailsWorkUpdate {

    @Headers("")
    @GET()
    Call<ModelWorkUpdate>getDetailsWorkUpdate(@Url String urlString);
}
