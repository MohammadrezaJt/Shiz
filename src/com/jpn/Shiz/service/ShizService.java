package com.jpn.Shiz.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.jpn.Shiz.R;
import com.jpn.Shiz.Shiz;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by saeed on 1/16/16.
 */
public abstract class ShizService extends AsyncTask<String, Void, Void> {

    private String TAG;
    private Activity activity;
    private ProgressDialog progressDialog;

    public ShizService(Activity activity, String TAG) {
        this.activity = activity;
        this.TAG = TAG;
    }

    private boolean isOnline() {
        final ConnectivityManager connMgr = (ConnectivityManager) Shiz.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isAvailable() && wifi.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else if (null != mobile && mobile.isAvailable() && mobile.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onPreExecute() {
        Log.e("ShizService", "onPreExecute");
        if (isOnline()) {
            progressDialog = new ProgressDialog(activity, R.style.MyTheme);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            Toast.makeText(Shiz.getContext(), "Internet connection error!", Toast.LENGTH_LONG).show();
        }
        super.onPreExecute();
    }

    @Override
    protected void onCancelled() {
        Log.e("ShizService", "onCancelled");
        super.onCancelled();
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.e("ShizService", "doInBackground");
        JsonResponse.setText(null, TAG);
        /************ Make Post Call To Web Server ***********/
        if (isOnline()) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json; charset=utf-8");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                JsonResponse.setText(result.toString(), TAG);
            } catch (Exception e) {
                JsonResponse.setText(null, TAG);
                Log.e("Error of here", e.getMessage());
            }
        } else {
            Log.e("ShizService", "Internet connection error!");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.e("ShizService", "onPostExecute");
        if (JsonResponse.getText(TAG) != null) {
            runProccess();
        }
        if (isOnline()) {
            progressDialog.dismiss();
        }
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        Log.i("ShizService", "onProgressUpdate");
        super.onProgressUpdate(values);
    }

    public abstract void runProccess();
}
