package com.rokomari_poc.noteme.WorkUpdate.DetailsWorkUpdate;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.WorkUpdate.ModelWorkUpdate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsWorkUpdateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ApiInterfaceDetailsWorkUpdate apiInterfaceDetailsWorkUpdate;
    private ModelWorkUpdate modelWorkUpdate;
    private TextView tvName,tvDate,tvDetails;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_work_update);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting the work update id starts
        final Intent intent=getIntent();
        id=intent.getStringExtra("work_update_id");
        //getting the work update id ends

        tvName=findViewById(R.id.textview_name_details_work_update);
        tvDate=findViewById(R.id.textview_date_details_work_update);
        tvDetails=findViewById(R.id.textview_details_details_work_update);

        String urlString=""+id;
        apiInterfaceDetailsWorkUpdate=ApiClientDetailWorkUpdate.getApiClient()
        .create(ApiInterfaceDetailsWorkUpdate.class);

        Call<ModelWorkUpdate> call=apiInterfaceDetailsWorkUpdate.getDetailsWorkUpdate(urlString);

        call.enqueue(new Callback<ModelWorkUpdate>() {
            @Override
            public void onResponse(Call<ModelWorkUpdate> call, Response<ModelWorkUpdate> response) {
                tvName.setText(""+modelWorkUpdate.getName());
                tvDate.setText(""+modelWorkUpdate.getDate());
                tvDetails.setText(""+modelWorkUpdate.getDetails());
            }

            @Override
            public void onFailure(Call<ModelWorkUpdate> call, Throwable t) {

            }
        });
    }
}
