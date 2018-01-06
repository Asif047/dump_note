package com.rokomari_poc.noteme.WorkUpdate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AlertBox.ShowAlert;
import com.rokomari_poc.noteme.DetailsNote.DetailsNoteActivity;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.Utils.NetworkUtils.MyNetworkCheck;
import com.rokomari_poc.noteme.WorkUpdate.DetailsWorkUpdate.DetailsWorkUpdateActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import okhttp3.OkHttpClient;

public class ShowWorkUpdateActivity extends AppCompatActivity
        implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{

    private int mYear, mMonth, mDay;
    private String mDate,date_time;
    private TextView mDateText;

    SweetAlertDialog pDialog;
    private String BASE_URL="http://192.168.11.205:5001/";
    private String path;
    OkHttpClient client;
    String response;

    private FloatingActionButton btnRefresh;
    private ModelWorkUpdate[] modelWorkUpdates;
    private ApiCallWorkUpdate apiCallWorkUpdate;

    List<ModelWorkUpdate> data=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapterWorkUpdate recyclerAdapterWorkUpdate;
    private RecyclerView.LayoutManager layoutManager;
    private MyNetworkCheck myNetworkCheck;
    private SweetAlertDialog sDialogWarning;
    private ShowAlert showAlert;

    private String account_id="";
    private String email=null;
    private String timestamp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_work_update);


        //getting account id starts
        SharedPreferences prefs=getSharedPreferences("Profile_PREF",MODE_PRIVATE);
        String restoredAccount=prefs.getString("account_id",null);
        if(restoredAccount!=null)
        {
            account_id=prefs.getString("account_id","No account defined");
        }
        //getting account id ends

        myNetworkCheck=new MyNetworkCheck();

        mDateText=findViewById(R.id.set_date);

        //to get time starts
        java.util.Calendar c = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("dd/MM/yyyy");
        date_time = sd.format(c.getTime());
        mDateText.setText(date_time);
        //to get time ends

        apiCallWorkUpdate=new ApiCallWorkUpdate();
        pDialog=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sDialogWarning=new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE);
        showAlert=new ShowAlert(this);

        recyclerView=findViewById(R.id.recyclerview_show_work_update);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        btnRefresh=findViewById(R.id.button_refresh);

        if(!myNetworkCheck.isConnected(ShowWorkUpdateActivity.this))
            showAlert.showWarningNetShowWorkUpdateActivity();
        else
        {
            try
            {
                path=BASE_URL+"work_update";
                new GetDataFromServer().execute();
            }
            catch (Exception e)
            {

            }
        }

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetDataFromServer().execute();
            }
        });


        Intent intent = new Intent(this, DetailsWorkUpdateActivity.class);
        intent.putExtra("timestamp", mDate);

    }

    private class GetDataFromServer extends AsyncTask<Void,Void,Void>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try
            {
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#aaaa"));
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

            recyclerAdapterWorkUpdate=new RecyclerAdapterWorkUpdate(ShowWorkUpdateActivity.this,data);
            recyclerView.setAdapter(recyclerAdapterWorkUpdate);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                client=new OkHttpClient();
                response=apiCallWorkUpdate.GET(client,path,account_id,mDate);
                Log.e("##JSON: ",response);
                Gson gson=new Gson();
                Type type = new TypeToken<Collection<ModelWorkUpdate>>() {
                }.getType();

                Collection<ModelWorkUpdate> enums=gson.fromJson(response,type);
                modelWorkUpdates=enums.toArray(new ModelWorkUpdate[enums.size()]);

                if(data.isEmpty())
                {
                    for (int i=0;i<enums.size();i++)
                    {
                      data.add(modelWorkUpdates[i]);
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

    public void setDate(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        monthOfYear ++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = year + "-" + monthOfYear + "-" + dayOfMonth+" 23:59:59";
        mDateText.setText(year + "-" + monthOfYear + "-" + dayOfMonth);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }
}
