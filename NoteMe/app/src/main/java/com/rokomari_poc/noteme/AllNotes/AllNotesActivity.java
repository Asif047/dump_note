package com.rokomari_poc.noteme.AllNotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rokomari_poc.noteme.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllNotesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterNotes recyclerAdapterNotes;
    private List<ModelNotes> modelNotes;
    private ApiInterfaceNotes apiInterfaceNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview_all_notes);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterfaceNotes=ApiClientNotes.getApiClient().create(ApiInterfaceNotes.class);
        Call<List<ModelNotes>> call=apiInterfaceNotes.getNotes();

        call.enqueue(new Callback<List<ModelNotes>>() {
            @Override
            public void onResponse(Call<List<ModelNotes>> call, Response<List<ModelNotes>> response) {
                modelNotes=response.body();
                recyclerAdapterNotes=new RecyclerAdapterNotes(modelNotes);
                recyclerView.setAdapter(recyclerAdapterNotes);
            }

            @Override
            public void onFailure(Call<List<ModelNotes>> call, Throwable t) {

            }
        });
    }
}
