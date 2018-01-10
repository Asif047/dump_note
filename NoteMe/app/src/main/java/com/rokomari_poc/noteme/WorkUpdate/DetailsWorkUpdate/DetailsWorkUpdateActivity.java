package com.rokomari_poc.noteme.WorkUpdate.DetailsWorkUpdate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AlertBox.ShowAlert;
import com.rokomari_poc.noteme.AllNotes.AllNotesActivity;
import com.rokomari_poc.noteme.DetailsNote.DetailsNoteActivity;
import com.rokomari_poc.noteme.Home.HomeActivity;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.Utils.NetworkUtils.MyNetworkCheck;
import com.rokomari_poc.noteme.WorkUpdate.ModelWorkUpdate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.OkHttpClient;

public class DetailsWorkUpdateActivity extends AppCompatActivity {


    SweetAlertDialog pDialog;
    private String BASE_URL="https://notes-web.herokuapp.com/";
    private String path;

    OkHttpClient client;
    String response;

    private ModelWorkUpdate modelWorkUpdate;
    private ApiCallDetailsWorkUpdate apiCallDetailsWorkUpdate=new ApiCallDetailsWorkUpdate();

    List<ModelWorkUpdate> modelWorkUpdates=new ArrayList<>();
    private MyNetworkCheck myNetworkCheck=new MyNetworkCheck();
    private SweetAlertDialog sDialogWarning;
    private ShowAlert showAlert;

    private String account_id="",timestamp="";
    private String email="";

    private TextView tvName,tvDate,tvDetails;
    private String work_update_id="";
    private String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_work_update);

        //getting account id starts
        SharedPreferences prefs=getSharedPreferences("Profile_PREF",MODE_PRIVATE);
        String restoredAccount=prefs.getString("account_id",null);

        if(restoredAccount!=null)
        {
            account_id=prefs.getString("account_id","No account defined");
        }

        //getting account id ends

        tvName=findViewById(R.id.textview_name_details_work_update);
        tvDate=findViewById(R.id.textview_date_details_work_update);
        tvDetails=findViewById(R.id.textview_details_details_work_update);

        pDialog=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sDialogWarning=new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE);
        showAlert=new ShowAlert(this);


        //getting the note id starts
        final Intent intent=getIntent();
        work_update_id=intent.getStringExtra("work_update_id");
        name=intent.getStringExtra("first_name");
        timestamp=intent.getStringExtra("timestamp");
        //getting the note id ends

        if(!myNetworkCheck.isConnected(DetailsWorkUpdateActivity.this))
            showAlert.showWarningNetDetailsWorkUpdateActivity();
        else
        {
            try
            {
                path=BASE_URL+"work_update/"+work_update_id;
                new GetDataFromServer().execute();
            }
            catch (Exception e)
            {

            }
        }
    }


    private class GetDataFromServer extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try
            {
//                pDialog.getProgressHelper().setBarColor(Color.parseColor("#26A65B"));
//                pDialog.setTitleText("Loading");
//                pDialog.setCancelable(false);
//                pDialog.show();
            }
            catch (Exception e)
            {

            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            tvName.setText(""+name);
            tvDate.setText(""+modelWorkUpdate.getTimestamp());
            tvDetails.setText(""+modelWorkUpdate.getDetail());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                client=new OkHttpClient();
                response=apiCallDetailsWorkUpdate.GET(client,path,account_id);
                Log.e("#JSON:",response);
                Gson gson=new Gson();
                Type type=new TypeToken<Collection<ModelWorkUpdate>>()
                {

                }.getType();

                modelWorkUpdate=gson.fromJson(response,ModelWorkUpdate.class);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
            //mo = gson.fromJson(response,ModelNotes.class);
        }
    }



    //back button operation starts

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(DetailsWorkUpdateActivity.this, HomeActivity.class);
        startActivity(intent);


    }


    //back button operation ends


}
