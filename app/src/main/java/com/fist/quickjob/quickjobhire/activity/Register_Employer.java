package com.fist.quickjob.quickjobhire.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Scene;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.config.AppConfig;
import com.fist.quickjob.quickjobhire.pref.SessionManager;

import org.apache.http.HttpEntity;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Register_Employer extends AppCompatActivity {

    private String type, pass1, repass1, email1, namecompany1, phone1,uid;
    private FrameLayout mFrtContent;
    private Scene mSceneSignUp;
    private Scene mSceneLogging;
    private Scene mSceneMain;
    private int mTvSighUpWidth, mTvSighUpHeight;
    private int mDuration;
    View viewobject;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__employer);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        Intent i = getIntent();
        int key = i.getIntExtra("KEY", 10);
        type = key + "";
        Button bt = (Button)findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edname = (EditText) findViewById(R.id.namecompany);
                EditText edmail = (EditText) findViewById(R.id.edmail);
                EditText edpass = (EditText) findViewById(R.id.edpass);
                EditText edrepass = (EditText) findViewById(R.id.edrepass);
                EditText sdt = (EditText) findViewById(R.id.phone);

                edname.setError(null);
                edmail.setError(null);
                edpass.setError(null);
                sdt.setError(null);
                edrepass.setError(null);

                email1 = edmail.getText().toString();
                pass1 = edpass.getText().toString();
                repass1 = edrepass.getText().toString();
                namecompany1 = edname.getText().toString();
                phone1 = sdt.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(pass1)) {
                    edpass.setError(getString(R.string.error_field_required));
                    focusView = edpass;
                    cancel = true;
                } else if (!isPasswordValid(pass1)) {
                    edpass.setError(getString(R.string.error_invalid_password));
                    focusView = edpass;
                    cancel = true;
                }
                // Kiểm tra mật khẩu nhập lại
                if (TextUtils.isEmpty(repass1)) {
                    edrepass.setError(getString(R.string.error_field_required));
                    focusView = edrepass;
                    cancel = true;
                } else if (!isPasswordValid(repass1)) {
                    edrepass.setError(getString(R.string.error_invalid_password));
                    focusView = edrepass;
                    cancel = true;
                }

                //Kiểm tra tên công ty
                if (TextUtils.isEmpty(namecompany1)) {
                    edname.setError(getString(R.string.error_field_required));
                    focusView = edname;
                    cancel = true;
                } else if (!isCompanyValid(namecompany1)) {
                    edname.setError(getString(R.string.error_invalid_tenCY));
                    focusView = edname;
                    cancel = true;
                }

                //Kiểm tra số điện thoại
                if (TextUtils.isEmpty(phone1)) {
                    sdt.setError(getString(R.string.error_field_required));
                    focusView = sdt;
                    cancel = true;
                } else if (!isValidPhoneNumber(phone1)) {
                    sdt.setError(getString(R.string.error_invalid_phone));
                    focusView = sdt;
                    cancel = true;
                }

                // Kiểm tra email
                if (TextUtils.isEmpty(email1)) {
                    edmail.setError(getString(R.string.error_field_required));
                    focusView = edmail;
                    cancel = true;
                } else if (!isValidEmail(email1)) {
                    edmail.setError(getString(R.string.error_invalid_email));
                    focusView = edmail;
                    cancel = true;
                }
                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                }
                if (pass1.equals(repass1) && cancel == false) {
                    //   Toast.makeText(Register_Employer.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    AsyncDataClass asyncRequestObject = new AsyncDataClass();
                    asyncRequestObject.execute(AppConfig.URL_DANGKY_NTD, email1, pass1, type, namecompany1, phone1);
                } else if (pass1.equals(repass1) == false) {
                    Toast.makeText(Register_Employer.this, R.string.st_loiMK, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





    private boolean isValidEmail(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    private boolean isNameValid(String ten) {
        return ten.length() > 2;
    }

    private boolean isCompanyValid(String namecompany) {
        return namecompany.length() > 1;
    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
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

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", params[1]));
            nameValuePairs.add(new BasicNameValuePair("pass", params[2]));
            nameValuePairs.add(new BasicNameValuePair("type", params[3]));
            nameValuePairs.add(new BasicNameValuePair("namecompany", params[4]));
            nameValuePairs.add(new BasicNameValuePair("phone", params[5]));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            try {
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                jsonResult = EntityUtils.toString(entity);
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
            if (result.equals("") || result == null) {
                Toast.makeText(Register_Employer.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if (jsonResult == 0) {
                Toast.makeText(Register_Employer.this, R.string.st_saiTKMK, Toast.LENGTH_SHORT).show();
                return;
            }
            if (jsonResult == 1) {
                session.createLoginSession(namecompany1, email1, "", pass1, uid);
                Intent intent = new Intent(Register_Employer.this, MainActivity.class);
                intent.putExtra("USERNAME", email1);
                intent.putExtra("name", namecompany1);
                intent.putExtra("uid", uid);
                intent.putExtra("mtype", type);
                intent.putExtra("logo", "");
                startActivity(intent);
                finish();
//
//                        Intent intent = new Intent(Register_Employer.this, LoginActivity.class);
//                        intent.putExtra("USERNAME", email1);
//                        intent.putExtra("pass", pass1);
//                        intent.putExtra("phone", phone1);
//                        startActivity(intent);
            }
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

    private int returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);
            returnedResult = resultObject.getInt("success");
            uid =resultObject.getString("uid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }

}
