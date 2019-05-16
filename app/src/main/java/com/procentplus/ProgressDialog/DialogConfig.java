package com.procentplus.ProgressDialog;


import android.app.ProgressDialog;
import android.content.Context;

public class DialogConfig {

    public ProgressDialog progressDialog;

    public DialogConfig(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
    }

    public void showDialog() {
        progressDialog.show();
    }

    public void dismissDialog() {
        progressDialog.dismiss();
    }
}
