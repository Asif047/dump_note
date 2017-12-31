package com.rokomari_poc.noteme.AllNotes;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AlertBox.ShowAlert;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.Utils.NetworkUtils.MyNetworkCheck;

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

    private Toolbar toolbar;

    static SweetAlertDialog pDialog;
    private ShowAlert showAlert;
    private MyNetworkCheck myNetworkCheck = new MyNetworkCheck();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        showAlert=new ShowAlert(this);


        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview_all_notes);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterfaceNotes=ApiClientNotes.getApiClient().create(ApiInterfaceNotes.class);
        Call<List<ModelNotes>> call=apiInterfaceNotes.getNotes();

        if (!myNetworkCheck.isConnected(AllNotesActivity.this))
            showAlert.showWarningNetAllNotesActivity();
        else
        {
            call.enqueue(new Callback<List<ModelNotes>>() {
                @Override
                public void onResponse(Call<List<ModelNotes>> call, Response<List<ModelNotes>> response) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();

                    modelNotes=response.body();
                    //Log.e("####RESPONSE:",modelNotes.get(0).getCategory());
                    recyclerAdapterNotes=new RecyclerAdapterNotes(AllNotesActivity.this,modelNotes);
                    recyclerView.setAdapter(recyclerAdapterNotes);
                }

                @Override
                public void onFailure(Call<List<ModelNotes>> call, Throwable t) {

                    Log.e("####RESPONSE:","Failure");
                }
            });
        }


    }
}
