package com.fist.quickjob.quickjobhire.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.config.AppConfig;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by SONTHO on 15/08/2016.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    private Button btsend;
    private EditText edmail;
    private View focusView;
    private String msg,email;
    private int rand, ss;
    private Boolean wantToCloseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btsend = (Button) findViewById(R.id.btsend);
        edmail = (EditText) findViewById(R.id.edmail);

        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edmail.setError(null);
                email =edmail.getText().toString();
                boolean cancel = false;
                focusView = null;
                if (TextUtils.isEmpty(email)) {
                    edmail.setError(getString(R.string.error_field_required));
                    focusView = edmail;
                    cancel = true;
                } else if (!isEmailValid(email)) {
                    edmail.setError(getString(R.string.error_invalid_email));
                    focusView = edmail;
                    cancel = true;
                }else{
                    AsyncDataClass asyncRequestObject = new AsyncDataClass();
                    asyncRequestObject.execute(AppConfig.URL_FORGOTPASSWORD, email);
                }
                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                }

            }
        });
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@gmail.com");
    }


    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);

            String jsonResult = "";
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", params[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);
            if(result.equals("") || result == null){
                Toast.makeText(ForgotPasswordActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if(jsonResult==1)
            {
                rand = rand(1000,99999);
                //SendMailActivity sm = new SendMailActivity(ForgotPasswordActivity.this, email,"QUICKJOB QUÊN MẬT KHẨU","MÃ XÁC NHẬN CỦA BẠN LÀ:"+rand);
            //    sm.execute();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view1 = inflater.inflate(R.layout.dialog_createskill, null);
                builder.setTitle(R.string.st_quenmk_ten);
                builder.setMessage(R.string.st_nhapMaXN);
                builder.setView(view1);
                builder.setCancelable(false);
                builder.setNeutralButton(R.string.st_sendagain, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton(R.string.st_thoat, null);
                builder.setNegativeButton(R.string.st_chon, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rand = rand(1000, 99999);
                     //   SendMailActivity sm = new SendMailActivity(ForgotPasswordActivity.this, email, "QUICKJOB QUÊN MẬT KHẨU", "MÃ XÁC NHẬN CỦA BẠN LÀ:" + rand);
                    //    sm.execute();
                    }
                });
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wantToCloseDialog = false;
                        EditText e = (EditText) view1.findViewById(R.id.edkynang);
                        String k = e.getText().toString();
                        try {
                            ss = Integer.parseInt(k);
                        } catch (Exception x) {
                            Toast.makeText(ForgotPasswordActivity.this, R.string.st_maKHL, Toast.LENGTH_SHORT).show();
                        }
                        if (ss == rand) {
                            wantToCloseDialog=true;
                            Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordSecondActivity.class);
                            intent.putExtra("email", email);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, R.string.st_maXN_khongdung, Toast.LENGTH_SHORT).show();
                        }
                        if (wantToCloseDialog)
                            dialog.dismiss();
                    }
                });
            }else{
            }
            Toast.makeText(ForgotPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();

        }
    }
    public  int rand(int min, int max) {
        try {
            Random rn = new Random();
            int range = max - min + 1;
            int randomNum = min + rn.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    private int returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);
            returnedResult = resultObject.getInt("success");
            msg = resultObject.getString("error_msg");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            while ((rLine = br.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return answer;
    }
}
