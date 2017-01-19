package com.fist.quickjob.quickjobhire.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.config.AppConfig;
import com.fist.quickjob.quickjobhire.pref.SessionManager;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.quickjob.quickjobhire.R.id.img;
import static com.example.quickjob.quickjobhire.R.id.motact;
import static com.example.quickjob.quickjobhire.R.id.motacv;
import static com.example.quickjob.quickjobhire.R.id.nganhnghe;
import static com.example.quickjob.quickjobhire.R.id.soluong;
import static com.example.quickjob.quickjobhire.R.id.tencv;

/**
 * Created by VinhNguyen on 8/22/2016.
 */
public class CreateJobActivity extends AppCompatActivity {
    private static final int PLACE_PICKER_REQUEST = 3;
    private EditText eddc, edtencv,edmotacv,edtenct,edquymo,eddiachi,ednganhnghe,edmotact,edtuoi,edyeucaukhac,edhannophoso,edsoluong,edphucloi;
    private Spinner noilamviec,chucdanh,hocvan,tennganhnghe,mucluong;
    private LinearLayout lin1,lin2,lin3,lin4,lin5,lin6;
    private String dotuoi="",gioitinh="",pp="",oo="", uid, KEY_IMAGE = "image", KEY_NAME = "name", namejop ="", yck = "",namecompany = "", mtcv = "",moct = "",qm = "", dc = "",nn = "",map = "",cd = "",hv = "",ml = "",jop = "",hnhs = "",soluongtuyen = "",phucloi = "";
    private TextView m,n,b,m1,n11,b1;
    private RadioGroup group;
    private RadioButton nam,nu,all;
    private ImageView logo,imgloca;
    private ImageButton addnn,addkn,n1,n2,n3,k1,k2,k3;
    private int key=0,key2=0,key3=0,key4=0,key5=0,key6=0;
    private Calendar calendar;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private Button nhaplai,create;
    private String tencongty="",quymo="",diachi="",nganhnghe2="",mota="",logo1="";
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private int statuss=0,stt=0;
    String image="";
    ProgressDialog pDialog ;
    SessionManager session;
    SharedPreferences.Editor edit;
    double lng,lat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_createjop);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        imgloca=(ImageView) findViewById(R.id.imgloca);
        getData();
        events();
    }

    private void events() {
        imgloca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationPlacesIntent();
            }
        });
        edhannophoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                fromDatePickerDialog = new DatePickerDialog(CreateJobActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        edhannophoso.setText(dateFormatter.format(newDate.getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.show();
            }
        });
        addkn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateJobActivity.this);
                LayoutInflater inflater = (LayoutInflater) CreateJobActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view1 = inflater.inflate(R.layout.dialog_createskill, null);
                builder.setTitle(R.string.st_kinang);
                builder.setView(view1);
                builder.setPositiveButton(R.string.st_thoat, null);
                builder.setNegativeButton(R.string.st_chon, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText e = (EditText) view1.findViewById(R.id.edkynang);

                        String k = e.getText().toString();
                        if (k == "") {
                        } else {
                            if (key4 == 0) {
                                lin4.setVisibility(View.VISIBLE);
                                m.setText(k + "");
                                key4 = 1;
                            } else if (key5 == 0) {
                                lin5.setVisibility(View.VISIBLE);
                                n.setText(k + "");
                                key5 = 1;
                            } else {
                                lin6.setVisibility(View.VISIBLE);
                                b.setText(k + "");
                                key6 = 1;
                            }
                        }
                    }
                });

                builder.create().show();
            }
        });
        addnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CreateJobActivity.this);
                LayoutInflater inflater = (LayoutInflater) CreateJobActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.dialog_createlangues, null);
                ListView lv = (ListView) view.findViewById(R.id.lvnn);
                final String[] ngoaingu = getResources().getStringArray(R.array.spNgoaiNgu);
                ArrayAdapter ad = ArrayAdapter.createFromResource(CreateJobActivity.this, R.array.spNgoaiNgu, android.R.layout.simple_spinner_item);
                lv.setAdapter(ad);
                builder.setTitle(R.string.st_ngoaingu);
                builder.setView(view);
                builder.setPositiveButton(R.string.st_thoat, null);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (key == 0) {
                            lin1.setVisibility(View.VISIBLE);
                            m1.setText(ngoaingu[position] + "");
                            key = 1;
                        } else if (key2 == 0) {
                            lin2.setVisibility(View.VISIBLE);
                            n11.setText(ngoaingu[position] + "");
                            key2 = 1;
                        } else {
                            lin3.setVisibility(View.VISIBLE);
                            b1.setText(ngoaingu[position] + "");
                            key3 = 1;
                        }
                    }
                });
                builder.create().show();
            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idnam = nam.getId();
                int idnu = nu.getId();
                int idall = all.getId();
                if (checkedId == idnam) {
                    gioitinh = "1";
                } else if (checkedId == idnu) {
                    gioitinh = "2";
                } else {
                    gioitinh = "0";
                }
            }
        });


        edtuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateJobActivity.this);
                LayoutInflater inflater = (LayoutInflater) CreateJobActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.dialog_mucluong, null);
                builder.setTitle(R.string.st_doTuoiYC);
                builder.setView(view);
                builder.setPositiveButton(R.string.st_thoat, null);
                builder.setNegativeButton(R.string.st_chon, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText to = (EditText) view.findViewById(R.id.to);
                        EditText from = (EditText) view.findViewById(R.id.from);
                        String a = to.getText().toString();
                        String b = from.getText().toString();
                        dotuoi = a + "~" + b;
                        edtuoi.setText(dotuoi.toString() + " Tuổi");
                    }
                });
                builder.create().show();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (requestCode == PLACE_PICKER_REQUEST) {
//                    if (resultCode == RESULT_OK) {
//                        Place place = PlacePicker.getPlace(CreateJobActivity.this, data);
//                        if (place != null) {
//                        LatLng latLng = place.getLatLng();
//                        lng = latLng.latitude;
//                        lat = latLng.longitude;
//
//                        }
//                    }
//                }
                namejop = edtencv.getText().toString();
                yck = edyeucaukhac.getText().toString();
                namecompany = edtenct.getText().toString();
                mtcv = edmotacv.getText().toString();
                moct = edmotact.getText().toString();
                qm = edquymo.getText().toString();
                dc = eddiachi.getText().toString();
                nn = ednganhnghe.getText().toString();
                String andress= eddc.getText().toString();
                map = andress;
               // map = String.valueOf(noilamviec.getSelectedItem());
             //   cd = String.valueOf(chucdanh.getSelectedItem());
                 cd = chucdanh.getSelectedItemPosition()+"";
              //  hv = String.valueOf(hocvan.getSelectedItem());
                 hv = hocvan.getSelectedItemPosition()+"";
              //  ml = String.valueOf(mucluong.getSelectedItem());
                 ml = mucluong.getSelectedItemPosition()+"";
              //  jop = String.valueOf(tennganhnghe.getSelectedItem());
                 jop = tennganhnghe.getSelectedItemPosition()+"";
                hnhs = edhannophoso.getText().toString();
                soluongtuyen = edsoluong.getText().toString();
                phucloi = edphucloi.getText().toString();
                if (namejop == "" || namecompany == "" || qm == "" || dc == "" || nn == "" || map == "" || dotuoi == "" || gioitinh == ""  || hnhs == "" || soluongtuyen == "") {
                    Toast.makeText(CreateJobActivity.this,"Lỗi!!Hãy nhập đầy đủ thông tin rồi thử lại", Toast.LENGTH_SHORT).show();
                } else {
                    if (key != 0) {
                        pp = m1.getText().toString();
                    }
                    if (key2 != 0) {
                        pp = pp + "/" + n11.getText().toString();
                    }
                    if (key3 != 0) {
                        pp = pp + "/" + b1.getText().toString();
                    }
                    if (key4 != 0) {
                        oo = m.getText().toString();
                    }
                    if (key5 != 0) {
                        oo = oo + "/" + n.getText().toString();
                    }
                    if (key6 != 0) {
                        oo = oo + "/" + b.getText().toString();
                    }

                    getLocationFromAddress(CreateJobActivity.this,map);

                    if (stt == 2) {
                        if (statuss == 1) {
                            uploadImage();
                        } else {
                            AsyncDataClass asyncRequestObject = new AsyncDataClass();
                            asyncRequestObject.execute(AppConfig.URL_DANGTIN, namejop, cd+"", ml+"", map, mtcv, dotuoi, hv+"", gioitinh, pp, oo, yck, namecompany, qm, dc, nn, moct, uid, jop+"", hnhs, soluongtuyen, phucloi,lat+"",lng+"");
                        }

                    } else {
                        if (statuss == 0) {
                            Toast.makeText(CreateJobActivity.this, R.string.st_err_anh, Toast.LENGTH_SHORT).show();

                        } else {
                            uploadImage();

                        }
                    }
                }
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    public void getLocationFromAddress(Context context,String strAddress) {
        Geocoder coder = new Geocoder(context);
        if (map != null && !map.isEmpty()) {
            try {
                List<Address> addressList = coder.getFromLocationName(map, 5);
                if (addressList != null && addressList.size() > 0) {
                     lat = addressList.get(0).getLatitude();
                     lng = addressList.get(0).getLongitude();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } // end catch
        } // end if

    }
    private void getData() {
        session = new SessionManager(CreateJobActivity.this);
        SharedPreferences pref=getSharedPreferences("JobFindPref", MODE_PRIVATE);
        edit=pref.edit();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        uid=user.get(SessionManager.KEY_ID);
        Intent get = getIntent();
        int status = get.getIntExtra("status", 1);
        if(status==1) {
            tencongty = get.getStringExtra("tenct");
            quymo = get.getStringExtra("quymo");
            diachi = get.getStringExtra("diachi");
            nganhnghe2 = get.getStringExtra("nganhnghe");
            mota = get.getStringExtra("mota");
            logo1=get.getStringExtra("logo");
            stt=2;
            if(logo1=="")
            {}else{
                new LoadImage().execute(logo1);
            }
        }else{
        }
        addView();
        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = 0;
                lin1.setVisibility(View.GONE);
            }
        });
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key2 = 0;
                lin2.setVisibility(View.GONE);
            }
        });
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key3 = 0;
                lin3.setVisibility(View.GONE);
            }
        });
        k1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key4 = 0;
                lin4.setVisibility(View.GONE);
            }
        });
        k2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key5 = 0;
                lin5.setVisibility(View.GONE);
            }
        });
        k3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key6 = 0;
                lin6.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == CreateJobActivity.this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(CreateJobActivity.this.getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                logo.setImageBitmap(bitmap);
                statuss=1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(CreateJobActivity.this,getString(R.string.st_uploading),getString(R.string.st_plsWait),false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        image=s;
                        AsyncDataClass asyncRequestObject = new AsyncDataClass();
                        asyncRequestObject.execute(AppConfig.URL_DANGTIN, namejop, cd, ml, map, mtcv, dotuoi, hv, gioitinh, pp, oo, yck, namecompany, qm, dc, nn, moct, uid, jop, hnhs, soluongtuyen, phucloi,lat+"",lng+"");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = uid;

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();
                Log.i("CreateJob:",image+"//==//"+name);
                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(CreateJobActivity.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
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
                nameValuePairs.add(new BasicNameValuePair("namejop", params[1]));
                nameValuePairs.add(new BasicNameValuePair("chucdanh", params[2]));
                nameValuePairs.add(new BasicNameValuePair("mucluong", params[3]));
                nameValuePairs.add(new BasicNameValuePair("diadiem", params[4]));
                nameValuePairs.add(new BasicNameValuePair("motacongviec", params[5]));
                nameValuePairs.add(new BasicNameValuePair("tuoiyeucau", params[6]));
                nameValuePairs.add(new BasicNameValuePair("hocvan", params[7]));
                nameValuePairs.add(new BasicNameValuePair("gioitinh", params[8]));
                nameValuePairs.add(new BasicNameValuePair("ngoaingu", params[9]));
                nameValuePairs.add(new BasicNameValuePair("kynang", params[10]));
                nameValuePairs.add(new BasicNameValuePair("yeucaukhac", params[11]));
                nameValuePairs.add(new BasicNameValuePair("tencongty", params[12]));
                nameValuePairs.add(new BasicNameValuePair("quymo", params[13]));
                nameValuePairs.add(new BasicNameValuePair("diachi", params[14]));
                nameValuePairs.add(new BasicNameValuePair("nganhnghe", params[15]));
                nameValuePairs.add(new BasicNameValuePair("gioithieucongty", params[16]));
                nameValuePairs.add(new BasicNameValuePair("uid", params[17]));
                nameValuePairs.add(new BasicNameValuePair("tennn", params[18]));
                nameValuePairs.add(new BasicNameValuePair("hannophoso", params[19]));
                nameValuePairs.add(new BasicNameValuePair("soluong", params[20]));
                nameValuePairs.add(new BasicNameValuePair("phucloi", params[21]));
                nameValuePairs.add(new BasicNameValuePair("latitude", params[22]));
                nameValuePairs.add(new BasicNameValuePair("longitude", params[23]));
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
                Toast.makeText(CreateJobActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if(jsonResult == 0){
                Toast.makeText(CreateJobActivity.this, R.string.st_errNamePass, Toast.LENGTH_SHORT).show();
                return;
            }
            if(jsonResult == 1){
                Intent s = new Intent(CreateJobActivity.this, JobDetailActivity.class);
                s.putExtra("macv", "");
                s.putExtra("matd", "");
                s.putExtra("tencongty", namecompany);
                s.putExtra("diachi",dc);
                s.putExtra("nganhnghe", nn);
                s.putExtra("quymo", qm);
                s.putExtra("motact", moct);
                s.putExtra("nganhNghe",jop);
                s.putExtra("chucdanh", cd);
                s.putExtra("soluong", soluongtuyen);
                s.putExtra("phucloi",phucloi);
                s.putExtra("tencongviec", namejop);
                s.putExtra("diadiem",map);
                s.putExtra("mucluong",ml);
                s.putExtra("ngayup", hnhs);
                s.putExtra("yeucaubangcap",hv);
                s.putExtra("dotuoi", dotuoi);
                s.putExtra("ngoaingu", pp);
                s.putExtra("gioitinh", gioitinh);
                s.putExtra("khac", yck);
                s.putExtra("motacv", mtcv);
                s.putExtra("kn", oo);
                s.putExtra("img", img);
                startActivity(s);
                finish();
//                 Intent intent=new Intent();
//                intent.putExtra("url",image);
//                setResult(2,intent);
//                finish();
                 /*   Intent intent = new Intent(dangky2.this, dangnhap.class);
                    intent.putExtra("USERNAME", email1);
                    intent.putExtra("name", name);
                    intent.putExtra("namecompany", namecompany1);
                    intent.putExtra("phone", phone1);
                    intent.putExtra("MESSAGE", "You have been successfully Registered");
                    intent.putExtra("PASS", pass1);
                    intent.putExtra("KEY", 0);
                    startActivity(intent); */

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
    private int returnParsedJsonObject(String result){

        JSONObject resultObject = null;
        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);
            returnedResult = resultObject.getInt("success");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateJobActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                logo.setImageBitmap(image);
                pDialog.dismiss();
            }else{
                pDialog.dismiss();
            }
        }
    }

//    public static Bitmap getBitmapFromURL(String src) {
//        try {
//            Log.e("src", src);
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            Log.e("Bitmap", "returned");
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("Exception", e.getMessage());
//            return null;
//        }
//    }

    public void addView(){
        calendar = Calendar.getInstance();
        edphucloi = (EditText) findViewById(R.id.phucloi);
        logo = (ImageView) findViewById(R.id.logo);
        logo.setImageResource(R.drawable.profile);
        eddc= (EditText) findViewById(R.id.eddc) ;
        m1 = (TextView) findViewById(R.id.nn1);
        n11 = (TextView) findViewById(R.id.nn2);
        b1 = (TextView) findViewById(R.id.nn3);
        m = (TextView) findViewById(R.id.kn1);
        n = (TextView) findViewById(R.id.kn2);
        b = (TextView) findViewById(R.id.kn3);
        lin1 = (LinearLayout) findViewById(R.id.ngoaingu1);
        lin2 = (LinearLayout) findViewById(R.id.ngoaingu2);
        lin3 = (LinearLayout) findViewById(R.id.ngoaingu3);
        lin4 = (LinearLayout) findViewById(R.id.kynang1);
        lin5 = (LinearLayout) findViewById(R.id.kynang2);
        lin6 = (LinearLayout) findViewById(R.id.kynang3);
        edhannophoso = (EditText) findViewById(R.id.hannophoso);
        edsoluong = (EditText) findViewById(soluong);
        n1 = (ImageButton) findViewById(R.id.n1);
        n2 = (ImageButton) findViewById(R.id.n2);
        n3 = (ImageButton) findViewById(R.id.n3);
        k1 = (ImageButton) findViewById(R.id.k1);
        k2 = (ImageButton) findViewById(R.id.k2);
        k3 = (ImageButton) findViewById(R.id.k3);
        edyeucaukhac = (EditText) findViewById(R.id.yeucaukhac);
        edtencv = (EditText) findViewById(tencv);
        edtenct = (EditText) findViewById(R.id.tencongty);
        edmotacv = (EditText) findViewById(motacv);
        edquymo = (EditText) findViewById(R.id.quymo);
        eddiachi = (EditText) findViewById(R.id.diachi);
        ednganhnghe = (EditText) findViewById(nganhnghe);
        edmotact = (EditText) findViewById(motact);
        noilamviec = (Spinner) findViewById(R.id.diadiem);
        mucluong = (Spinner) findViewById(R.id.mucluong);
        tennganhnghe = (Spinner) findViewById(R.id.nganhnghe1);
        edtuoi = (EditText) findViewById(R.id.tuoiyc);
        group = (RadioGroup) findViewById(R.id.group);
        nam = (RadioButton) findViewById(R.id.nam);
        nu = (RadioButton) findViewById(R.id.nu);
        all = (RadioButton) findViewById(R.id.all);
        addnn = (ImageButton) findViewById(R.id.themnn);
        addkn = (ImageButton) findViewById(R.id.themkn);

        ArrayAdapter adapter= ArrayAdapter.createFromResource(CreateJobActivity.this,R.array.diadiem,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        noilamviec.setAdapter(adapter);

        hocvan = (Spinner) findViewById(R.id.hocvan);
        ArrayAdapter adapter3= ArrayAdapter.createFromResource(CreateJobActivity.this,R.array.spHocVan,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        hocvan.setAdapter(adapter3);
        chucdanh = (Spinner) findViewById(R.id.chucdanh);
        ArrayAdapter ad= ArrayAdapter.createFromResource(CreateJobActivity.this,R.array.spChucDanh,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        chucdanh.setAdapter(ad);
        nhaplai = (Button) findViewById(R.id.again);
        create = (Button) findViewById(R.id.create);
        ArrayAdapter nganhNghe= ArrayAdapter.createFromResource(CreateJobActivity.this, R.array.nganhNghe,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        tennganhnghe.setAdapter(nganhNghe);
        ArrayAdapter mucLuong= ArrayAdapter.createFromResource(CreateJobActivity.this,R.array.mucluong,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mucluong.setAdapter(mucLuong);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        edtenct.setText(tencongty+"");
        ednganhnghe.setText(nganhnghe2+"");
        edquymo.setText(quymo+"");
        edmotact.setText(mota+"");
        eddiachi.setText(diachi+"");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    private void locationPlacesIntent() {

        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(CreateJobActivity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

}