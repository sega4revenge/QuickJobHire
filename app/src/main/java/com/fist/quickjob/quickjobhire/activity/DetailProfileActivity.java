package com.fist.quickjob.quickjobhire.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.config.AppConfig;
import com.fist.quickjob.quickjobhire.fragment.FragmentFistTab;
import com.fist.quickjob.quickjobhire.other.CircleTransform;
import com.fist.quickjob.quickjobhire.pref.SessionManager;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DetailProfileActivity extends AppCompatActivity {

    private TextView txtname,txtgioitinh,txttuoi,txtmail,txtsdt,txtkn,txtquequan,txtdiachi,txtkynang,txtluong,txthocvan,txtngonngu,txtkhac;
    private ImageView logo;
    private Button btok,btno,btsave,btcall,btsend;
    private String name,gioitinh,tuoi,mail,sdt,kn,quequan,diachi,kynang,luong,hocvan,ngonngu,khac,img,mahs, macv;
    private Date today=new Date(System.currentTimeMillis());int key;
    private SimpleDateFormat timeFormat= new SimpleDateFormat("dd/MM/yyyy");
    private static final int REQUEST_CALL = 0;
    public static final String TAG = "DetailProfileActivity";
    SessionManager session;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail_profile);


        addView();
        getData();
        addData();

        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(AppConfig.URL_CHAPNHANHOS, mahs, macv, "0");
                Toast.makeText(DetailProfileActivity.this, R.string.st_hsDaChon, Toast.LENGTH_SHORT).show();

            }
        });
        btno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(AppConfig.URL_CHAPNHANHOS, mahs, macv, "1");
                Toast.makeText(DetailProfileActivity.this, R.string.st_tuChoiHS, Toast.LENGTH_SHORT).show();
            }
        });
        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = MainActivity.uid;
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(AppConfig.URL_LUUHS, mahs, uid);
            }
        });
        btcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(DetailProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestCameraPermission();
                } else {
                    takePicture();
                }
            }
        });
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TO = mail;
                session = new SessionManager(getApplicationContext());
                SharedPreferences pref=getSharedPreferences("JobFindPref", MODE_PRIVATE);
                edit=pref.edit();

                // get user data from session
                HashMap<String, String> user = session.getUserDetails();
                String email = user.get(SessionManager.KEY_EMAIL);
                email=email;
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailProfileActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void getData() {
        Intent i = getIntent();
        key = i.getIntExtra("key", 1);
        if(key==0)
        {
            btok.setVisibility(View.GONE);
            btno.setVisibility(View.GONE);
            btsave.setVisibility(View.VISIBLE);
        }else if(key==2){
            LinearLayout ds = (LinearLayout) findViewById(R.id.line);
            ds.setVisibility(View.GONE);
        }

        name = i.getStringExtra("ten");
        gioitinh = i.getStringExtra("gioitinh");
        tuoi = i.getStringExtra("ngaysinh");
        mail = i.getStringExtra("email");
        sdt = i.getStringExtra("sdt");
        kn = i.getStringExtra("kinhnghiem");
        quequan = i.getStringExtra("quequan");
        diachi = i.getStringExtra("diachi");
        kynang= i.getStringExtra("kynang");
        luong = i.getStringExtra("mucluong");
        ngonngu =i.getStringExtra("ngoaingu");
        img = i.getStringExtra("img");
        mahs =i.getStringExtra("mahs");
        macv = FragmentFistTab.macv2;
    }

    private void takePicture() {
        String phone = sdt;
        String number = phone + "";
       // Toast.makeText(DetailProfileActivity.this, number, Toast.LENGTH_SHORT).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        try {
            startActivity(callIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), R.string.st_loiKXD, Toast.LENGTH_SHORT).show();
        }
    }

    private void requestCameraPermission() {


        // Camera permission has not been granted yet. Request it directly.
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                REQUEST_CALL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            Log.i(TAG, "Received response for Camera permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Camera permission has been granted, preview can be displayed

                takePicture();

            } else {
                //Permission not granted
                Toast.makeText(DetailProfileActivity.this, R.string.st_pemissonCamera, Toast.LENGTH_SHORT).show();
            }

        }
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

                nameValuePairs.add(new BasicNameValuePair("mahs", params[1]));
                if(key==0)
                {
                    nameValuePairs.add(new BasicNameValuePair("id", params[2]));
                }else {
                    nameValuePairs.add(new BasicNameValuePair("macv", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("status", params[3]));
                }
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
                Toast.makeText(DetailProfileActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject resultObject = null;
            int returnedResult = 0;

            try {
                resultObject = new JSONObject(result);
                returnedResult = resultObject.getInt("success");
                if(returnedResult==1)
                {
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(DetailProfileActivity.this, result, Toast.LENGTH_SHORT).show();

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);

    }


    private void addData() {
        txtname.setText(name+"");
        String[] sex = getResources().getStringArray(R.array.spsex);
        String[] luong2 = getResources().getStringArray(R.array.mucluong);
        if(Integer.parseInt(gioitinh)==1)
        {

            txtgioitinh.setText(sex[1]);
        }else{
            txtgioitinh.setText(sex[0]);
        }

        String s=timeFormat.format(today.getTime());
        String ss = tuoi+"";
        String[] dd =ss.split("/");
        String[] d =s.split("/");
        int nam1 = Integer.parseInt(d[2]);
        int nam2 = Integer.parseInt(dd[0]);
        int tuois =nam1-nam2;
        txttuoi.setText(tuois+"");
        txtmail.setText(mail+"");
        txtsdt.setText(sdt+"");
        txtkn.setText(kn+"");
        txtquequan.setText(quequan+"");
        txtdiachi.setText(diachi+"");
        txtkynang.setText(kynang+"");
        txtluong.setText(luong2[Integer.parseInt(luong)]);
        txtngonngu.setText(ngonngu+"");
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
     //   logo.setImageUrl(img, imageLoader);
        Glide.with(DetailProfileActivity.this).load(img)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(DetailProfileActivity.this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(logo);
    }

    private void addView() {
        txtname = (TextView) findViewById(R.id.name);
        txtgioitinh = (TextView) findViewById(R.id.gioitinh);
        txttuoi = (TextView) findViewById(R.id.tuoi);
        txtmail = (TextView) findViewById(R.id.txtEmail);
        txtsdt = (TextView) findViewById(R.id.txtPhone);
        txtkn = (TextView) findViewById(R.id.txtKinhNghiem);
        txtquequan = (TextView) findViewById(R.id.txtQueQuan);
        txtdiachi = (TextView) findViewById(R.id.txtDiaChi);
        txtkynang = (TextView) findViewById(R.id.txtKyNang);
        txtluong = (TextView) findViewById(R.id.txtMucLuong);
        txthocvan = (TextView) findViewById(R.id.txtHocVan);
        txtngonngu = (TextView) findViewById(R.id.txtNgonNgu);
        txthocvan = (TextView) findViewById(R.id.txtHocVan);
        txtkhac = (TextView) findViewById(R.id.txtThongTinBoSung);
        logo = (ImageView) findViewById(R.id.thumbnail);
        btcall = (Button) findViewById(R.id.btnCall);
        btsend = (Button) findViewById(R.id.btsend);
        btok = (Button) findViewById(R.id.btnok);
        btno = (Button) findViewById(R.id.btnno);
        btsave = (Button) findViewById(R.id.btnsave);
        btsend = (Button) findViewById(R.id.btnSend);
    }


}
