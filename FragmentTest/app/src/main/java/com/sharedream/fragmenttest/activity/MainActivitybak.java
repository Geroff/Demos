package com.sharedream.fragmenttest.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.sharedream.fragmenttest.R;
import com.sharedream.fragmenttest.fragment.RightFragment;

/**
 * Created by as on 2016/4/16.
 */
public class MainActivitybak extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RightFragment fragment = (RightFragment)getFragmentManager().findFragmentById(R.id.rightfragment);
        fragment.showMsg();
    }
    public void showDialog(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("This is dialog");
        dialog.setMessage("Alert...");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
