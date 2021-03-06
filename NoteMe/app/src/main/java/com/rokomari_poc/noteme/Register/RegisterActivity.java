package com.rokomari_poc.noteme.Register;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AlertBox.ShowAlert;
import com.rokomari_poc.noteme.LogIn.LogInActivity;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.Utils.NetworkUtils.MyNetworkCheck;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFname,etLname,etPhone,etEmail,etPassword;
    private TextView tvLogIn;
    private Button btnRegister;
    static SweetAlertDialog pDialog;
    private ShowAlert showAlert;
    String emailPattern;

    private MyNetworkCheck myNetworkCheck = new MyNetworkCheck();
    private RegisterMyPostRequest registerMyPostRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerMyPostRequest=new RegisterMyPostRequest(this);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        etFname=findViewById(R.id.edittext_first_name);
        etLname=findViewById(R.id.edittext_last_name);
        etPhone=findViewById(R.id.edittext_phone);
        etEmail=findViewById(R.id.edittext_email);
        etPassword=findViewById(R.id.edittext_password);
        btnRegister=findViewById(R.id.button_register);

        tvLogIn=findViewById(R.id.textview_log_in);

        showAlert=new ShowAlert(this);

        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etFname.getText().toString().isEmpty())
                    etFname.setError("Please enter the First Name");
                if (etLname.getText().toString().isEmpty())
                    etLname.setError("Please enter the Last Name");
                if (etPhone.getText().toString().isEmpty())
                    etPhone.setError("Please enter the Mobile number");
                if (etEmail.getText().toString().isEmpty())
                    etEmail.setError("Please enter the Email");
                if (etPassword.getText().toString().isEmpty())
                    etPassword.setError("Please enter the Password");

                emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (etEmail.getText().toString().matches(emailPattern))
                {
                    if (!myNetworkCheck.isConnected(RegisterActivity.this))
                        showAlert.showWarningNetRegisterActivity();
                    else
                    {

                        registerMyPostRequest.postData(etFname.getText().toString(),
                                etLname.getText().toString(),etPhone.getText().toString(),
                                etEmail.getText().toString(),etPassword.getText().toString());
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
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
