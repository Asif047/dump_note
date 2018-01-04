package com.rokomari_poc.noteme.LogIn;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AlertBox.ShowAlert;
import com.rokomari_poc.noteme.Home.HomeActivity;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.Register.RegisterActivity;
import com.rokomari_poc.noteme.Utils.NetworkUtils.MyNetworkCheck;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogInActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etEmail,etPassword;
    private TextView tvRegister;
    private Button btnLogIn;
    private String responsePost = "",accountId="",user_email="",first_name="";

    String emailPattern;
    private String response_status;

    static SweetAlertDialog pDialog;
    private ShowAlert showAlert;

    private MyNetworkCheck myNetworkCheck = new MyNetworkCheck();
    private LogInMyPostRequest logInMyPostRequest;
    private LogInPostResponseApi logInPostResponseApi;

    String logInResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail=findViewById(R.id.edittext_email);
        etPassword=findViewById(R.id.edittext_password);
        btnLogIn=findViewById(R.id.button_sign_in);
        tvRegister=findViewById(R.id.textview_register);

        logInPostResponseApi=new LogInPostResponseApi();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        etEmail=findViewById(R.id.edittext_email);
        etPassword=findViewById(R.id.edittext_password);
        btnLogIn=findViewById(R.id.button_sign_in);

        showAlert=new ShowAlert(this);

        logInMyPostRequest=new LogInMyPostRequest(this);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogInActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etEmail.getText().toString().isEmpty())
                    etEmail.setError("Please enter the Email");
                if (etPassword.getText().toString().isEmpty())
                    etPassword.setError("Please enter the Password");
                emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if (etEmail.getText().toString().matches(emailPattern))
                {
                    if (!myNetworkCheck.isConnected(LogInActivity.this))
                        showAlert.showWarningNetLogInActivity();
                    else
                    {
                        postData(etEmail.getText().toString(),etPassword.getText().toString());
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }



    public void postData(String email, String password) {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "" + email);
        params.put("password", "" + password);

        JSONObject parameter = new JSONObject(params);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, parameter.toString());
        Request request = new Request.Builder()
                .url("http://192.168.11.205:5001/login")
                .post(body)
                .header("token","68e109f0f40ca72a15e05cc22786f8e6")
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Log.e("response", call.request().body().toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                 String email="",first_name="";

//                if (RegisterActivity.pDialog.isShowing())
//                    RegisterActivity.pDialog.dismiss();

                responsePost = response.body().string();
                accountId=response.headers().get("jwt");
               // Log.e("response", responsePost);
                //Log.e("response", accountId);

                Gson gson = new Gson();
                Type type = new TypeToken<LogInPostResponseApi>() {
                }.getType();


                logInPostResponseApi = gson.fromJson(responsePost, type);
                response_status = String.format("" + logInPostResponseApi.getStatus());

                user_email=logInPostResponseApi.getEmail();
                first_name=logInPostResponseApi.getFirst_name();

                Log.d("RESPONSE POST: ", response_status);

                if(response_status.equals("logged_in"))
                {
                    SharedPreferences.Editor editor = getSharedPreferences("Profile_PREF", MODE_PRIVATE).edit();
                    editor.putString("email", user_email);
                    editor.putString("first_name", first_name);
                    editor.putString("account_id", accountId);
                    editor.apply();

                    // Toast.makeText(context,response_status,Toast.LENGTH_SHORT).show();
                    logInResponse="logged In";
                    Intent intent = new Intent(LogInActivity.this, HomeActivity.class);

                    startActivity(intent);
                }

                else
                {
                    // logInResponse="log in failed";
                    //Toast.makeText(getApplicationContext(),"Incorrect email or password",Toast.LENGTH_SHORT).show();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //show the response body
                            Toast.makeText(LogInActivity.this,"Log in failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

        });


    }


    //back button operation starts

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    //back button operation ends

}
