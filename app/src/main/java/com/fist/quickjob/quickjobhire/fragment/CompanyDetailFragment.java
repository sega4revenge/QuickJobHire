package com.fist.quickjob.quickjobhire.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.activity.JobDetailActivity;
import com.fist.quickjob.quickjobhire.activity.MainActivity;
import com.fist.quickjob.quickjobhire.adapter.CongViecAdapter;
import com.fist.quickjob.quickjobhire.config.AppConfig;
import com.fist.quickjob.quickjobhire.model.CongViec;
import com.fist.quickjob.quickjobhire.other.CircleTransform;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CompanyDetailFragment extends Fragment {

    private View view;
    private ImageView logocompany;
    private TextView txtnameconpany,txtandress,txtcreer,txtinfor;
    private String namecompany="",andress="",career="",infor="",uid="",logo="",macv="";
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView image;
    AsyncDataClass asyncRequestObject;
    JSONArray mang;
    ListView mRecyclerView;
    ProgressBar progressBar;
    private List<CongViec> celebrities = new ArrayList<>();
    private CongViecAdapter adapter = null;
    String id ="";
    public CompanyDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_two, container, false);
        actionGetIntent();
        init();
        id=MainActivity.uid;
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_XUATHS1, id);

        return view;
    }

    private void init() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRecyclerView = (ListView) view.findViewById(R.id.resjob);
        mRecyclerView.setScrollContainer(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        logocompany= (ImageView) view.findViewById(R.id.logocompany);
        txtnameconpany = (TextView) view.findViewById(R.id.namecompany);
        txtandress = (TextView) view.findViewById(R.id.andress);
        txtcreer = (TextView) view.findViewById(R.id.career);
        txtinfor = (TextView) view.findViewById(R.id.information);
        txtnameconpany.setText(namecompany);
        txtandress.setText(andress);
        txtcreer.setText(career);
        txtinfor.setText(infor);
        Glide.with(getActivity()).load(logo)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(getActivity()))
                .diskCacheStrategy( DiskCacheStrategy.ALL )
                .into(logocompany);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent s = new Intent(getActivity(), JobDetailActivity.class);
                s.putExtra("tencongty", celebrities.get(i).tecongty);
                s.putExtra("tencongviec", celebrities.get(i).tencongviec);
                s.putExtra("diadiem", celebrities.get(i).diadiem);
                s.putExtra("mucluong", celebrities.get(i).luong);
                s.putExtra("ngayup", celebrities.get(i).dateup);
                s.putExtra("yeucaubangcap", celebrities.get(i).bangcap);
                s.putExtra("dotuoi", celebrities.get(i).dotuoi);
                s.putExtra("ngoaingu", celebrities.get(i).ngoaingu);
                s.putExtra("gioitinh", celebrities.get(i).gioitinh);
                s.putExtra("khac", celebrities.get(i).khac);
                s.putExtra("motacv", celebrities.get(i).motacv);
                s.putExtra("kn", celebrities.get(i).kn);
                s.putExtra("macv", celebrities.get(i).macv);
                s.putExtra("img", celebrities.get(i).url);
                s.putExtra("sdt", celebrities.get(i).sdt);
                s.putExtra("motact", celebrities.get(i).motact);
                s.putExtra("type", 3);
                startActivity(s);
            }
        });
    }

    private void actionGetIntent() {
        Intent i = getActivity().getIntent();
        namecompany =i.getStringExtra("tencongty");
        andress=i.getStringExtra("diadiem");
        career=i.getStringExtra("nganhnghe");
        infor=i.getStringExtra("motact");
        logo=i.getStringExtra("img");
        macv=i.getStringExtra("macv");
        uid= MainActivity.uid;

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
                nameValuePairs.add(new BasicNameValuePair("macv", params[1]));
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
                Toast.makeText(getActivity(),R.string.st_errServer, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                return;
            }
            try {
                mang = new JSONArray(result);
                if(mang.length()>0) {
                        for (int i = 0; i < mang.length(); i++) {
                            JSONObject ob = mang.getJSONObject(i);
                            celebrities.add(new CongViec(ob.getString("macv"), ob.getString("tencv"), ob.getString("tenct"), ob.getString("diachi"), ob.getString("nganhnghe"), ob.getString("motact"), ob.getString("quymo"), ob.getString("nn"), ob.getString("chucdanh"), ob.getString("soluong"), ob.getString("mucluong"), ob.getString("diadiem"), ob.getString("hannophoso"), ob.getString("motacv"), ob.getString("bangcap"), ob.getString("dotuoi"), ob.getString("ngoaingu"), ob.getString("gioitinh"), ob.getString("khac"), ob.getString("kynang"), ob.getString("img"), ob.getString("phucloi"), "5", ""));
                        }
                adapter = new CongViecAdapter(getActivity(), android.R.layout.simple_list_item_1, celebrities, 5, "", 3);
                 mRecyclerView.setAdapter(adapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( asyncRequestObject!= null) {
            if (!asyncRequestObject.isCancelled()) {
                asyncRequestObject.cancel(true);
            }
        }
    }
}
