package com.rokomari_poc.noteme.AllNotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AlertBox.ShowAlert;
import com.rokomari_poc.noteme.Home.HomeActivity;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.Utils.NetworkUtils.MyNetworkCheck;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.OkHttpClient;

public class AllNotesActivity extends AppCompatActivity {

    SweetAlertDialog pDialog;
    private String BASE_URL="http://192.168.11.205:5001/";
    private String path;
    OkHttpClient client;
    String response;
    private ModelNotes[] modelNotes;
    private ApiCallAllNotes apiCallAllNotes=new ApiCallAllNotes();
    List<ModelNotes> data=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapterNotes recyclerAdapterNotes;
    private RecyclerView.LayoutManager layoutManager;
    private MyNetworkCheck myNetworkCheck=new MyNetworkCheck();
    private SweetAlertDialog sDialogWarning;
    private ShowAlert showAlert;

    private String account_id=null;
    private String email=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);


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

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview_all_notes);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if(!myNetworkCheck.isConnected(AllNotesActivity.this))
            showAlert.showWarningNetAllNotesActivity();
        else
        {
            try {
                path=BASE_URL+"notes";
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
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#26A65B"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();
            }
            catch (Exception e)
            {

            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(pDialog.isShowing())
                pDialog.dismiss();

            recyclerAdapterNotes=new RecyclerAdapterNotes(AllNotesActivity.this,data);
            recyclerView.setAdapter(recyclerAdapterNotes);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                client=new OkHttpClient();
                response=apiCallAllNotes.GET(client,path,account_id);
                Log.e("##Json:",response);
                Gson gson=new Gson();
                Type type=new TypeToken<Collection<ModelNotes>>()
                {

                }.getType();

                Collection<ModelNotes> enums=gson.fromJson(response,type);
                modelNotes=enums.toArray(new ModelNotes[enums.size()]);

                if(data.isEmpty())
                {
                    for(int i=0;i<enums.size();i++)
                    {
                        data.add(modelNotes[i]);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

    }


    //back button operation starts

    @Override
    public void onBackPressed() {

                        //finish();
                        Intent intent = new Intent(AllNotesActivity.this, HomeActivity.class);
                        startActivity(intent);


    }


    //back button operation ends


}
