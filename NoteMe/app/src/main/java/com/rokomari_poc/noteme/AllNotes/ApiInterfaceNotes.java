package com.rokomari_poc.noteme.AllNotes;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;


public interface ApiInterfaceNotes {

    @Headers("token:123456789")
    @GET("/API_CRUD_BASE-master/details/displayAll.php")
    Call<List<ModelNotes>> getNotes();
}
