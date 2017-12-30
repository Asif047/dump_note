package com.rokomari_poc.noteme.DetailsNote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rokomari_poc.noteme.AlertBox.ShowAlertDetails;
import com.rokomari_poc.noteme.AllNotes.ModelNotes;
import com.rokomari_poc.noteme.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsNoteActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private ModelNotes modelNotes;
    private ApiInterfaceDetailsNote apiInterfaceDetailsNote;
    private TextView tvHours,tvDate,tvStatus,tvTitle,tvDetails;
    private ImageView ivPhone,ivUrl,ivMail;

    private ShowAlertDetails showAlertDetails;
    private String phone="";
    private String email="";
    private String url="";

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);


        //getting the note id starts
        final Intent intent=getIntent();
        id=intent.getStringExtra("note_id");
        //getting the note id ends

        showAlertDetails=new ShowAlertDetails(this);

        tvHours=findViewById(R.id.textview_hours);
        tvDate=findViewById(R.id.textview_date);
        tvStatus=findViewById(R.id.textview_status);
        tvTitle=findViewById(R.id.textview_title);
        tvDetails=findViewById(R.id.textview_details);

        ivPhone=findViewById(R.id.imageview_phone);
        ivUrl=findViewById(R.id.imageview_web);
        ivMail=findViewById(R.id.imageview_email);


        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDetails.showPhoneAlert(phone);
            }
        });

        ivMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDetails.showEmailAlert(email);
            }
        });
        ivUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDetails.showUrlAlert(url);
            }
        });

        String urlString="/notes/"+id;

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

                phone=""+modelNotes.getPhone();
                email=""+modelNotes.getMail();
                url=""+modelNotes.getUrl();

            }

            @Override
            public void onFailure(Call<ModelNotes> call, Throwable t) {

            }
        });

    }
}
