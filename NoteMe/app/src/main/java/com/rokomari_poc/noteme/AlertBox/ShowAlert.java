package com.rokomari_poc.noteme.AlertBox;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AllNotes.AllNotesActivity;
import com.rokomari_poc.noteme.DetailsNote.DetailsNoteActivity;
import com.rokomari_poc.noteme.Home.HomeActivity;
import com.rokomari_poc.noteme.LogIn.LogInActivity;
import com.rokomari_poc.noteme.Register.RegisterActivity;

public class ShowAlert {
    private SweetAlertDialog sweetAlertDialog;
    private Context context;

    public ShowAlert(Context context) {
        this.context = context;
    }

    public void showWarningNetRegisterActivity()
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("No Internet Connection")
                .setContentText("Please turn on the internet connection and then press OK")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity)context).finish();
                        Intent intent = new Intent(context, RegisterActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }

    public void showWarningNetLogInActivity()
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("No Internet Connection")
                .setContentText("Please turn on the internet connection and then press OK")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity)context).finish();
                        Intent intent = new Intent(context, LogInActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }


    public void showWarningNetHomeActivity()
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("No Internet Connection")
                .setContentText("Please turn on the internet connection and then press OK")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity)context).finish();
                        Intent intent = new Intent(context, HomeActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }

    public void showWarningNetAllNotesActivity()
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("No Internet Connection")
                .setContentText("Please turn on the internet connection and then press OK")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity)context).finish();
                        Intent intent = new Intent(context, AllNotesActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }

    public void showWarningNetDetailsNotesActivity()
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("No Internet Connection")
                .setContentText("Please turn on the internet connection and then press OK")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity)context).finish();
                        Intent intent = new Intent(context, DetailsNoteActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }

    public void showWarningNetShowWorkUpdateActivity()
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("No Internet Connection")
                .setContentText("Please turn on the internet connection and then press OK")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity)context).finish();
                        Intent intent = new Intent(context, HomeActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }




    public void showWarningNetDetailsWorkUpdateActivity()
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("No Internet Connection")
                .setContentText("Please turn on the internet connection and then press OK")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity)context).finish();
                        Intent intent = new Intent(context, DetailsNoteActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }


}
