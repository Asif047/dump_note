package com.rokomari_poc.noteme.LogIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AlertBox.ShowAlert;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.Register.RegisterActivity;
import com.rokomari_poc.noteme.Utils.NetworkUtils.MyNetworkCheck;

public class LogInActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etEmail,etPassword;
    private Button btnLogIn;

    String emailPattern;

    static SweetAlertDialog pDialog;
    private ShowAlert showAlert;

    private MyNetworkCheck myNetworkCheck = new MyNetworkCheck();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail=findViewById(R.id.edittext_email);
        etPassword=findViewById(R.id.edittext_password);
        btnLogIn=findViewById(R.id.button_sign_in);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);



        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);


        etEmail=findViewById(R.id.edittext_email);
        etPassword=findViewById(R.id.edittext_password);
        btnLogIn=findViewById(R.id.button_sign_in);

        showAlert=new ShowAlert(this);



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

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }


}
