package com.rokomari_poc.noteme.AlertBox;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.DetailsNote.DetailsNoteActivity;
import com.rokomari_poc.noteme.R;

public class ShowAlertDetails {


    private SweetAlertDialog sweetAlertDialog;
    private Context context;

    public ShowAlertDetails(Context context) {
        this.context = context;
    }

    public void showPhoneAlert(final String phone)
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.setTitleText(phone)
                .setCustomImage(R.drawable.ic_phone_black)
                .setConfirmText("CALL")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                     //  ((Activity)context).finish();
                      // Intent intent = new Intent(context, DetailsNoteActivity.class);
//                        context.startActivity(intent);

                        String phone_number = phone;

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone_number));
                        if (ActivityCompat.checkSelfPermission(((Activity)context), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling

                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", ((Activity)context).getPackageName(), null);
                            intent.setData(uri);

                            ((Activity)context).startActivity(intent);
                            return;
                        }

                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ((Activity)context).startActivity(callIntent);
                    }
                })
//                .setCancelText("CANCEL")
//                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
////                        Intent intent=new Intent(((Activity)context),DetailsNoteActivity.class);
////                        ((Activity)context).startActivity(intent);
//                    }
//                })
                .show();
    }

    public void showEmailAlert(final String email)
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.setTitleText(email)
                .setCustomImage(R.drawable.ic_email_black)
                .setConfirmText("EMAIL")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                       // ((Activity)context).finish();
//                        Intent intent = new Intent(context, StartActivity.class);
//                        context.startActivity(intent);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto",""+email, null));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                        intent.putExtra(Intent.EXTRA_TEXT, "message");
                        ((Activity)context).startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                    }
                })
//                .setCancelText("CANCEL")
//                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
////                        Intent intent=new Intent(((Activity)context),DetailsNoteActivity.class);
////                        ((Activity)context).startActivity(intent);
//                    }
//                })
                .show();
    }

    public void showUrlAlert(final String url)
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog.setCancelable(true);

        sweetAlertDialog.setTitleText(url)
                .setCustomImage(R.drawable.ic_url_black)
                .setConfirmText("VISIT")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                       // ((Activity)context).finish();
//                        Intent intent = new Intent(context, PostActivity.class);
//                        context.startActivity(intent);
                        Uri uri = Uri.parse("https://"+url); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        ((Activity)context).startActivity(intent);

                    }
                })
//                .setCancelText("CANCEL")
//                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
////                        Intent intent=new Intent(((Activity)context),DetailsNoteActivity.class);
////                        ((Activity)context).startActivity(intent);
//                    }
//                })
                .show();

    }

    public void showWarningGPSpostActivity()
    {
        sweetAlertDialog=new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("No GPS Connection")
                .setContentText("Please turn on the GPS connection and then press OK")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity)context).finish();
//                        Intent intent = new Intent(context,PostActivity.class);
//                        context.startActivity(intent);
                    }
                })
                .show();
    }


    public void showWarningNetOrderActivity()
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
//                        Intent intent = new Intent(context, OrderActivity.class);
//                        context.startActivity(intent);
                    }
                })
                .show();
    }



    public void showWarningNetDetailActivity()
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
                    }
                })
                .show();
    }


    public void showWarningNetHistoryActivity()
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
//                        Intent intent = new Intent(context, DeliveredHistoryActivity.class);
//                        context.startActivity(intent);
                    }
                })
                .show();
    }


}
