package com.jpn.Shiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.jpn.Shiz.service.JsonResponse;
import com.jpn.Shiz.service.ShizService;

public class SignIn extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        submitTasks();
    }

    private void submitTasks() {
        String address = "http://46.225.119.213:86/Service1.asmx?op=Authentication&&UserName=مدیر&password=Alisadafkimia";
        final String serviceTAG = "test";
        ShizService shizService = new ShizService(this, serviceTAG) {
            @Override
            public void runProccess() {
                Boolean signInResult = new Gson().fromJson(JsonResponse.getText(serviceTAG), Boolean.class);
                Log.e("SignIn", "SignInResult : " + JsonResponse.getText(serviceTAG));
                if (signInResult != null && signInResult) {
                    Intent i = new Intent(SignIn.this, Home.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Shiz.getContext(), "wrong username password", Toast.LENGTH_SHORT).show();
                }
            }
        };
        shizService.execute(address);
    }
}
