package com.rokomari_poc.noteme.WorkUpdate.PostWorkUpdate;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.rokomari_poc.noteme.MyPostRequest;
import com.rokomari_poc.noteme.R;

public class PostWorkUpdateActivity extends AppCompatActivity {

    private EditText etDetails;
    private Button btnSendUpdate;
    private MyPostRequestWorkUpdate myPostRequestWorkUpdate;
    private Toolbar toolbar;

    private String date_time,status,category,phone="",url="",mail="",response_msg;
    private String account_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_work_update);

        //getting account id starts
        SharedPreferences prefs=getSharedPreferences("Profile_PREF",MODE_PRIVATE);
        String restoredAccount=prefs.getString("account_id",null);

        if(restoredAccount!=null)
        {
            account_id=prefs.getString("account_id","No account defined");
        }
        //getting account id ends

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myPostRequestWorkUpdate=new MyPostRequestWorkUpdate(this);
        etDetails=findViewById(R.id.edittext_details_work_update);
        btnSendUpdate=findViewById(R.id.button_send_update);
        myPostRequestWorkUpdate=new MyPostRequestWorkUpdate(this);

        status="2";
        category="6";
        //to get time starts
        java.util.Calendar c = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date_time = sd.format(c.getTime());
        //to get time ends


        btnSendUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPostRequestWorkUpdate.postData(""+date_time,""+status,"Work Update",""+etDetails.getText(),""+url,""+mail,""+phone,""+category,account_id);
            }
        });

    }
}
