package com.rokomari_poc.noteme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AllNotes.AllNotesActivity;

public class ShowResponseActivity extends AppCompatActivity {

    String response_status;
    private SweetAlertDialog sDialogError, sDialogSuccess, sDialogDuplicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_response);

        sDialogError = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);//initializing sweet alert dialog
        sDialogSuccess = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        sDialogDuplicate = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);

        Intent intent = getIntent();
        response_status = intent.getStringExtra("response_status");

        //Toast.makeText(this, response_message, Toast.LENGTH_LONG).show();
        Log.e("####RESPONSE_MSG:","hello");

//        buildDialog(ShowResponseActivity.this).show();
        if (response_status.trim().equals("saved"))
            showSuccess();
        else if(response_status.trim().equals("Deleted"))
            showSuccessDelete();
        else
            showInvalid();
    }


    void showInvalid() {
        sDialogError.setCancelable(false);
        sDialogError.setTitleText("Invalid")
                .setContentText("Something went wrong!")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
//                        Intent intent=new Intent(ShowResponseActivity.this,PostActivity.class);
//                        startActivity(intent);
                    }
                })
                .show();
    }

    void showSuccess() {
        sDialogSuccess.setCancelable(false);
        sDialogSuccess.setTitleText("Done")
                .setContentText("Good job")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                        Intent intent=new Intent(ShowResponseActivity.this,AllNotesActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    void showSuccessDelete() {
        sDialogSuccess.setCancelable(false);
        sDialogSuccess.setTitleText("Deleted")
                .setContentText("Good job")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                        Intent intent=new Intent(ShowResponseActivity.this,AllNotesActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }


    void showDuplicate() {
        sDialogDuplicate.setCancelable(false);
        sDialogDuplicate.setTitleText("Duplicate")
                .setContentText("Don't submit the same Order Id more than once")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
//                        Intent intent=new Intent(ShowResponseActivity.this,PostActivity.class);
//                        startActivity(intent);
                    }
                })
                .show();
    }


}
