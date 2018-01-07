package com.rokomari_poc.noteme.DetailsNote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AlertBox.ShowAlert;
import com.rokomari_poc.noteme.AlertBox.ShowAlertDetails;
import com.rokomari_poc.noteme.AllNotes.ApiCallAllNotes;
import com.rokomari_poc.noteme.AllNotes.ModelNotes;
import com.rokomari_poc.noteme.AllNotes.RecyclerAdapterNotes;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.Utils.NetworkUtils.MyNetworkCheck;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.OkHttpClient;

public class DetailsNoteActivity extends AppCompatActivity {

    SweetAlertDialog pDialog;
    private String BASE_URL="http://192.168.11.205:5001/";
    private String path;
    OkHttpClient client;
    String response;
    private ModelNotes modelNotes;
    private ApiCallDeatilsNote apiCallDeatilsNote=new ApiCallDeatilsNote();
    List<ModelNotes> data=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapterNotes recyclerAdapterNotes;
    private RecyclerView.LayoutManager layoutManager;
    private MyNetworkCheck myNetworkCheck=new MyNetworkCheck();
    private SweetAlertDialog sDialogWarning;
    private ShowAlert showAlert;
    private ShowAlertDetails showAlertDetails;

    private String account_id=null;
    private String note_id="";

    private TextView tvHours,tvDate,tvStatus,tvTitle,tvDetails;
    private ImageView ivPhone,ivUrl,ivMail;

    private String phone="";
    private String email="";
    private String url="";

    private MyDeleteRequest myDeleteRequest;

    private Toolbar toolbar;
    private String status="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDeleteRequest=new MyDeleteRequest(this);

        tvDate=findViewById(R.id.textview_date);
        tvStatus=findViewById(R.id.textview_status);
        tvTitle=findViewById(R.id.textview_title);
        tvDetails=findViewById(R.id.textview_details);

        ivPhone=findViewById(R.id.imageview_phone);
        ivUrl=findViewById(R.id.imageview_web);
        ivMail=findViewById(R.id.imageview_email);

        showAlertDetails=new ShowAlertDetails(this);


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

        //getting account id starts
        SharedPreferences prefs=getSharedPreferences("Profile_PREF",MODE_PRIVATE);
        String restoredAccount=prefs.getString("account_id",null);

        if(restoredAccount!=null)
        {
            account_id=prefs.getString("account_id","No account defined");
        }
        //getting account id ends

        pDialog=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sDialogWarning=new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE);
        showAlert=new ShowAlert(this);

//        recyclerView=(RecyclerView)findViewById(R.id.recyclerview_all_notes);
//        layoutManager=new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);

        //getting the note id starts
        final Intent intent=getIntent();
        note_id=intent.getStringExtra("note_id");
        //getting the note id ends


        if(!myNetworkCheck.isConnected(DetailsNoteActivity.this))
            showAlert.showWarningNetDetailsNotesActivity();
        else
        {
            try
            {
                path=BASE_URL+"notes/"+note_id;
                new GetDataFromServer().execute();
            }
            catch (Exception e)
            {

            }
        }
    }

    public void deleteClick(MenuItem item) {

        int id = Integer.parseInt(modelNotes.getId().toString());

        myDeleteRequest.deleteData(id,account_id);

    }


    private class GetDataFromServer extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try
            {
                pDialog.getProgressHelper().setBarColor(Color.parseColor("26A65B"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();
            }
            catch (Exception e)
            {

            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(pDialog.isShowing())
                pDialog.dismiss();

//            recyclerAdapterNotes=new RecyclerAdapterNotes(DetailsNoteActivity.this,data);
//            recyclerView.setAdapter(recyclerAdapterNotes);

            status=modelNotes.getStatus();
            if(status.equals("1"))
                tvStatus.setText("Undone");
            if(status.equals("2"))
                tvStatus.setText("Done");
            if(status.equals("3"))
                tvStatus.setText("Completed");
            if(status.equals("4"))
                tvStatus.setText("Abandoned");

            tvDate.setText(""+modelNotes.getTimestamp());
            tvTitle.setText(""+modelNotes.getTitle());
            tvDetails.setText(""+modelNotes.getDetail());

            phone=""+modelNotes.getPhone();
            email=""+modelNotes.getMail();
            url=""+modelNotes.getUrl();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                client=new OkHttpClient();
                response=apiCallDeatilsNote.GET(client,path,account_id);
                Log.e("##Json:",response);
                Gson gson=new Gson();
                Type type=new TypeToken<Collection<ModelNotes>>()
                {

                }.getType();
//                Collection<ModelNotes> enums=gson.fromJson(response,type);
                modelNotes = gson.fromJson(response,ModelNotes.class);


//                if(data.isEmpty())
//                {
//                    for(int i=0;i<enums.size();i++)
//                    {
//                        data.add(modelNotes);
//                    }
//                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_details_note,menu);
        return true;
    }
}
