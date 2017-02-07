package com.fist.quickjob.quickjobhire.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.quickjob.quickjobhire.R.id.tencv;

public class EditJobActivity extends AppCompatActivity {
    EditText edtencv,edddc, edmotacv, edtenct, edquymo, eddiachi, ednganhnghe, edmotact, edtuoi, edyeucaukhac, edhannophoso, edsoluong, edphucloi;
    Spinner noilamviec, chucdanh, hocvan, tennganhnghe, mucluong;
    LinearLayout lin1, lin2, lin3, lin4, lin5, lin6;
    String dotuoi = "", gioitinh = "", pp = "", oo = "", kqq = "", macv;
    TextView m, n, b, m1, n11, b1;
    RadioGroup group;
    RadioButton nam, nu, all;
    ImageView logo;
    ImageButton addnn, addkn, n1, n2, n3, k1, k2, k3;
    int key = 0, key2 = 0, key3 = 0, key4 = 0, key5 = 0, key6 = 0;
    private Calendar calendar;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    Button nhaplai, create;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ArrayAdapter adaptermucLuong, adapternganhnghe, adapterhocvan, adapterdiadiem, adapterchucdanh;
    SessionManager session;
    SharedPreferences.Editor edit;
    String uid, tencongty, diachi, motact, quymo, nganhnghe, tencongviec, diadiem, luong, dt, hannophoso, ngoaingu, tuoi, gt, khac, motacv, kn, pl, chucDanh, nganhNghe, soluong;
    int statuss = 0, stt = 0,stt2=0;
    String namejop = "", yck = "", namecompany = "", mtcv = "", moct = "", qm = "", dc = "", nn = "", map = "", cd = "", hv = "", ml = "", jop = "", hnhs = "", soluongtuyen = "", phucloi = "",logo1="",matd="";
    ProgressDialog pDialog;
    String[] arrnganh,arrhv,arrsalary,arrsex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_createjop);
        arrhv= getResources().getStringArray(R.array.spHocVan);
        arrsalary= getResources().getStringArray(R.array.mucluong);
        arrnganh= getResources().getStringArray(R.array.nganhNghe);
        addView();
        getData();
        events();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void events() {
        edtenct.setText(tencongty + "");
        ednganhnghe.setText(nganhnghe + "");
        edquymo.setText(quymo + "");
        edmotact.setText(motact + "");
        eddiachi.setText(diachi + "");
        edtencv.setText(tencongviec);
        //int pos = adapternganhnghe.getPosition(nganhNghe);
        tennganhnghe.setSelection(Integer.parseInt(nganhNghe));
      //  int pos1 = adapterchucdanh.getPosition(chucDanh);
        if(chucDanh.equals(""))
        {

        }else{
            chucdanh.setSelection(Integer.parseInt(chucDanh));
        }
         //   int pos2 = adaptermucLuong.getPosition(luong);
        mucluong.setSelection(Integer.parseInt(luong));
      //  int pos3 = adapterdiadiem.getPosition(diadiem);
      //  noilamviec.setSelection(Integer.parseInt(diadiem));
        edddc.setText(diadiem);
        edmotacv.setText(motacv+"");
        edsoluong.setText(soluong);
        edhannophoso.setText(hannophoso);
        edtuoi.setText(tuoi);
      //  int pos4 = adapterhocvan.getPosition(hv);
        if(hv.equals(""))
        {

        }else{
            hocvan.setSelection(Integer.parseInt(hv));
        }

        edyeucaukhac.setText(khac);
        edphucloi.setText(pl);
        edtuoi.setText(dt);

        if (gt.equals("1")) {
            nam.setChecked(true);
            gioitinh = "1";
        } else if (gt.equals("2")){
            nu.setChecked(true);
            gioitinh = "2";
        }else{
            all.setChecked(true);
            gioitinh = "0";
        }
        String[] arr = ngoaingu.split(" ");
        int a = 1;
        for (int i = 0; i < arr.length; i++) {
            if (a == 1) {
                lin1.setVisibility(View.VISIBLE);
                m1.setText(arr[i] + "");
                key = 1;
                a++;
                //   txtNN.setVisibility(View.GONE);
            } else if (a == 2) {
                lin2.setVisibility(View.VISIBLE);
                n11.setText(arr[i] + "");
                key2 = 1;
                a++;
            } else if (a == 3) {
                lin3.setVisibility(View.VISIBLE);
                b1.setText(arr[i] + "");
                key3 = 1;
            }
        }
        String[] arrkn = kn.split(" ");
        int ss = 1;
        for (int e = 0; e < arrkn.length; e++) {
            if (ss == 1) {
                lin4.setVisibility(View.VISIBLE);
                m.setText(arrkn[e] + "");
                key4 = 1;
                ss++;
                //     txtKN.setVisibility(View.GONE);
            } else if (ss == 2) {
                lin5.setVisibility(View.VISIBLE);
                n.setText(arrkn[e] + "");
                key5 = 1;
                ss++;
            } else if (ss == 3) {
                lin6.setVisibility(View.VISIBLE);
                b.setText(arrkn[e] + "");
                key6 = 1;
            }
        }

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
        edhannophoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                fromDatePickerDialog = new DatePickerDialog(EditJobActivity.this, new DatePickerDialog.OnDateSetListener() {

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
                AlertDialog.Builder builder = new AlertDialog.Builder(EditJobActivity.this);
                LayoutInflater inflater = (LayoutInflater) EditJobActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(EditJobActivity.this);
                LayoutInflater inflater = (LayoutInflater) EditJobActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.dialog_createlangues, null);
                ListView lv = (ListView) view.findViewById(R.id.lvnn);
                final String[] ngoaingu = getResources().getStringArray(R.array.spNgoaiNgu);
                ArrayAdapter ad = ArrayAdapter.createFromResource(EditJobActivity.this, R.array.spNgoaiNgu, android.R.layout.simple_spinner_item);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(EditJobActivity.this);
                LayoutInflater inflater = (LayoutInflater) EditJobActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                        tuoi = a + "~" + b;
                        edtuoi.setText(tuoi.toString() + " Tuổi");
                    }
                });
                builder.create().show();
            }
        });
        nhaplai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stt2=1;
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(AppConfig.URL_DeleteJOB,uid,macv );

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namejop = edtencv.getText().toString();
                yck = edyeucaukhac.getText().toString();
                namecompany = edtenct.getText().toString();
                mtcv = edmotacv.getText().toString();
                moct = edmotact.getText().toString();
                qm = edquymo.getText().toString();
                dc = eddiachi.getText().toString();
                nn = ednganhnghe.getText().toString();
                String andress= edddc.getText().toString();
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
                if (namejop == "" || namecompany == "" || qm == "" || dc == "" || nn == "" || map == "" || tuoi == "" || gioitinh == "" || jop == "" || hnhs == "" || soluongtuyen == "") {
                    Toast.makeText(EditJobActivity.this,"Lỗi!!Hãy nhập đầy đủ thông tin rồi thử lại", Toast.LENGTH_SHORT).show();
                    System.out.println(namejop+ namecompany+ qm+ dc+ nn+ map+ ml+ hv+ tuoi+ gioitinh+ jop+ hnhs+ soluongtuyen);
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
                    if (stt == 2) {
                        if (statuss == 1) {
                            uploadImage();
                        } else {
                            AsyncDataClass asyncRequestObject = new AsyncDataClass();
                            asyncRequestObject.execute(AppConfig.URL_EDITJOB, namejop, cd, ml, map, mtcv, tuoi, hv, gioitinh, pp, oo, yck, namecompany, qm, dc, nn, moct, uid, jop, hnhs, soluongtuyen, phucloi,macv,matd);
                        }

                    } else {
                        if (statuss == 0) {
                            Toast.makeText(EditJobActivity.this, R.string.st_err_anh, Toast.LENGTH_SHORT).show();

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

    private void getData() {
        Intent s = getIntent();
        macv = s.getStringExtra("macv");
        session = new SessionManager(EditJobActivity.this);
        SharedPreferences pref=getSharedPreferences("JobFindPref", MODE_PRIVATE);
        edit=pref.edit();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        uid=user.get(SessionManager.KEY_ID);
        tencongty = s.getStringExtra("tencongty");
        diachi = s.getStringExtra("diachi");
        motact = s.getStringExtra("motact");
        quymo = s.getStringExtra("quymo");
        nganhnghe = s.getStringExtra("nganhnghe");
        tencongviec = s.getStringExtra("tencongviec");
        diadiem = s.getStringExtra("diadiem");
        luong = s.getIntExtra("mucluong",2)+"";
        dt = s.getStringExtra("dotuoi");
        hannophoso = s.getStringExtra("ngayup");
        ngoaingu = s.getStringExtra("ngoaingu");
        tuoi = s.getStringExtra("dotuoi");
        gt = s.getStringExtra("gioitinh")+"";
        hv = s.getStringExtra("yeucaubangcap")+"";
        khac = s.getStringExtra("khac");
        motacv = s.getStringExtra("motacv");
        kn = s.getStringExtra("kn");
        pl = s.getStringExtra("phucloi");
        nganhNghe = s.getStringExtra("nganhNghe");
        chucDanh = s.getStringExtra("chucdanh");
        soluong = s.getStringExtra("soluong");
        logo1 = s.getStringExtra("img");
        stt=2;
        if(logo1=="")
        {}else{
            new LoadImage().execute(logo1);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == EditJobActivity.this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(EditJobActivity.this.getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                logo.setImageBitmap(bitmap);
                statuss = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(EditJobActivity.this, getString(R.string.st_uploading), getString(R.string.st_plsWait), false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(EditJobActivity.this, s, Toast.LENGTH_SHORT).show();
                        AsyncDataClass asyncRequestObject = new AsyncDataClass();
                        asyncRequestObject.execute(AppConfig.URL_EDITJOB, namejop, cd, ml, map, mtcv, tuoi, hv, gioitinh, pp, oo, yck, namecompany, qm, dc, nn, moct, uid, jop, hnhs, soluongtuyen, phucloi,macv,matd);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(EditJobActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = uid;

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(EditJobActivity.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
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
                if(stt2==0) {
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
                    nameValuePairs.add(new BasicNameValuePair("macv", params[22]));
                    nameValuePairs.add(new BasicNameValuePair("matd", params[23]));
                }else{
                    nameValuePairs.add(new BasicNameValuePair("id", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("macv", params[2]));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

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
            if (result.equals("") || result == null) {
                Toast.makeText(EditJobActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if (jsonResult == 0) {
            //    Toast.makeText(EditJobActivity.this, R.string.st_errNamePass, Toast.LENGTH_SHORT).show();
                return;
            }
            if (jsonResult==1) {
                if(stt2==1) {
                    Toast.makeText(EditJobActivity.this, R.string.st_xacXoaCV, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditJobActivity.this, R.string.st_edit_success, Toast.LENGTH_SHORT).show();
//namejop, cd, ml, map, mtcv, tuoi, hv, gioitinh, pp, oo, yck, namecompany, qm, dc, nn, moct, uid, jop, hnhs, soluongtuyen, phucloi,macv,matd
                    Intent intent=new Intent();
                    intent.putExtra("tencongty",tencongty);
                    intent.putExtra("diachi",dc);
                    intent.putExtra("motact",moct);
                    intent.putExtra("quymo",qm);
                    intent.putExtra("nganhnghe",nn);
                    intent.putExtra("tencongviec",namejop);
                    intent.putExtra("diadiem",map);
                    intent.putExtra("mucluong",ml);
                  //  intent.putExtra("dotuoi",dt);
                    intent.putExtra("ngayup",hnhs);
                    intent.putExtra("ngoaingu",ngoaingu);
                    intent.putExtra("dotuoi",tuoi);
                    intent.putExtra("gioitinh",gioitinh);
                    intent.putExtra("yeucaubangcap",hv);
                    intent.putExtra("khac",yck);
                    intent.putExtra("motacv",mtcv);
                    intent.putExtra("kn",kn);
                    intent.putExtra("phucloi",phucloi);
                    intent.putExtra("nganhNghe",nganhNghe);
                    intent.putExtra("chucdanh",chucDanh);
                    intent.putExtra("soluong",soluongtuyen);
                    intent.putExtra("img",logo1);
                    setResult(333,intent);
                }
                finish();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditJobActivity.this);
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
                Toast.makeText(EditJobActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            setResult(222,intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void addView() {
        calendar = Calendar.getInstance();
        edphucloi = (EditText) findViewById(R.id.phucloi);
        edddc = (EditText) findViewById(R.id.eddc);
        logo = (ImageView) findViewById(R.id.logo);
        logo.setImageResource(R.drawable.profile);
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
        edsoluong = (EditText) findViewById(R.id.soluong);
        n1 = (ImageButton) findViewById(R.id.n1);
        n2 = (ImageButton) findViewById(R.id.n2);
        n3 = (ImageButton) findViewById(R.id.n3);
        k1 = (ImageButton) findViewById(R.id.k1);
        k2 = (ImageButton) findViewById(R.id.k2);
        k3 = (ImageButton) findViewById(R.id.k3);
        edyeucaukhac = (EditText) findViewById(R.id.yeucaukhac);
        edtencv = (EditText) findViewById(tencv);
        edtenct = (EditText) findViewById(R.id.tencongty);
        edmotacv = (EditText) findViewById(R.id.motacv);
        edquymo = (EditText) findViewById(R.id.quymo);
        eddiachi = (EditText) findViewById(R.id.diachi);
        ednganhnghe = (EditText) findViewById(R.id.nganhnghe);
        edmotact = (EditText) findViewById(R.id.motact);
      //  noilamviec = (Spinner) findViewById(R.id.diadiem);
        mucluong = (Spinner) findViewById(R.id.mucluong);
        tennganhnghe = (Spinner) findViewById(R.id.nganhnghe1);
        edtuoi = (EditText) findViewById(R.id.tuoiyc);
        group = (RadioGroup) findViewById(R.id.group);
        nam = (RadioButton) findViewById(R.id.nam);
        nu = (RadioButton) findViewById(R.id.nu);
        all = (RadioButton) findViewById(R.id.all);
        addnn = (ImageButton) findViewById(R.id.themnn);
        addkn = (ImageButton) findViewById(R.id.themkn);
        adapterdiadiem = ArrayAdapter.createFromResource(EditJobActivity.this, R.array.diadiem, android.R.layout.simple_spinner_item);
        adapterdiadiem.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
    //    noilamviec.setAdapter(adapterdiadiem);
        hocvan = (Spinner) findViewById(R.id.hocvan);
        adapterhocvan = ArrayAdapter.createFromResource(EditJobActivity.this, R.array.spHocVan, android.R.layout.simple_spinner_item);
        adapterhocvan.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        hocvan.setAdapter(adapterhocvan);
        chucdanh = (Spinner) findViewById(R.id.chucdanh);
        adapterchucdanh = ArrayAdapter.createFromResource(EditJobActivity.this, R.array.spChucDanh, android.R.layout.simple_spinner_item);
        adapterchucdanh.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        chucdanh.setAdapter(adapterchucdanh);
        nhaplai = (Button) findViewById(R.id.again);
        nhaplai.setVisibility(View.VISIBLE);
        create = (Button) findViewById(R.id.create);
        create.setText(R.string.st_btnEdit);
        adapternganhnghe = ArrayAdapter.createFromResource(EditJobActivity.this, R.array.nganhNghe, android.R.layout.simple_spinner_item);
        adapternganhnghe.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        tennganhnghe.setAdapter(adapternganhnghe);
        adaptermucLuong = ArrayAdapter.createFromResource(EditJobActivity.this, R.array.mucluong, android.R.layout.simple_spinner_item);
        adaptermucLuong.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mucluong.setAdapter(adaptermucLuong);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent();
        setResult(222,intent);
        finish();
    }
}