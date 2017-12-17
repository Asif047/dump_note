package com.rokomari_poc.noteme.AllNotes;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceNotes {
    @GET("/notes")
    Call<List<ModelNotes>> getNotes();
}
