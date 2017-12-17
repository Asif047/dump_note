package com.rokomari_poc.noteme.DetailsNote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.rokomari_poc.noteme.AllNotes.ModelNotes;
import com.rokomari_poc.noteme.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsNoteActivity extends AppCompatActivity {


    private ModelNotes modelNotes;
    private ApiInterfaceDetailsNote apiInterfaceDetailsNote;
    private TextView tvHours,tvDate,tvStatus,tvTitle,tvDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);

        tvHours=findViewById(R.id.textview_hours);
        tvDate=findViewById(R.id.textview_date);
        tvStatus=findViewById(R.id.textview_status);
        tvTitle=findViewById(R.id.textview_title);
        tvDetails=findViewById(R.id.textview_details);

        String urlString="/notes/1";

        apiInterfaceDetailsNote=ApiClientDetailNote.getApiClient().create(ApiInterfaceDetailsNote.class);
        Call<ModelNotes> call=apiInterfaceDetailsNote.getDetailsNote(urlString);

        call.enqueue(new Callback<ModelNotes>() {
            @Override
            public void onResponse(Call<ModelNotes> call, Response<ModelNotes> response) {
                modelNotes=response.body();

                tvDate.setText(""+modelNotes.getTimestamp());
                tvStatus.setText(""+modelNotes.getStatus());
                tvTitle.setText(""+modelNotes.getTitle());
                tvDetails.setText(""+modelNotes.getDetail());
            }

            @Override
            public void onFailure(Call<ModelNotes> call, Throwable t) {

            }
        });

    }
}
