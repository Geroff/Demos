package com.example.geroff.mediademo;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Geroff on 2016/4/24.
 */
public class MyAsynTask extends AsyncTask<Void, Integer, Boolean> {
    TextView tvInfo = null;


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        tvInfo.setText("finish");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        tvInfo.setText("current progress-->" + values[0].toString());
    }

    public MyAsynTask(TextView tv) {
        tvInfo = tv;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        int progress = 0;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            publishProgress(progress);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progress += 10;
            if (progress == 110) {
                break;
            }
        }
        return true;
    }

    @Override
    protected void onPreExecute() {
        tvInfo.setText("ready to go");
    }

}
