package com.fist.quickjob.quickjobhire.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.activity.DetailProfileActivity;
import com.fist.quickjob.quickjobhire.adapter.ListViewAdapterThird;
import com.fist.quickjob.quickjobhire.config.AppConfig;
import com.fist.quickjob.quickjobhire.model.Profile;

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

public class FragmentSecondTab extends Fragment {
    private View rootView;
    private ListView lv;
    private ListViewAdapterThird adapter;
    private ArrayList<Profile> celebrities = new ArrayList<Profile>();
    static String macv2;TextView tv;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int status=0;
    JSONArray mang;
    AsyncDataClass asyncRequestObject;
//    private final String serverUrl = "http://192.168.1.41/android/hoso_ungtuyen.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_fragment__second_tab, container, false);
        Intent i = getActivity().getIntent();
        macv2 = i.getStringExtra("macv");
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        lv= (ListView) rootView.findViewById(R.id.lvtabsecond);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject ob =mang.getJSONObject(i);
                    Intent is = new Intent(getActivity(), DetailProfileActivity.class);
                    is.putExtra("ten", ob.getString("hoten"));
                    is.putExtra("gioitinh", ob.getString("gioitinh2"));
                    is.putExtra("ngaysinh", ob.getString("ngaysinh"));
                    is.putExtra("email", ob.getString("email"));
                    is.putExtra("sdt", ob.getString("sdt"));
                    is.putExtra("kinhnghiem",ob.getString("namkn"));
                    is.putExtra("tencongty", ob.getString("tencongty"));
                    is.putExtra("chucdanh", ob.getString("chucdanh"));
                    is.putExtra("motacv", ob.getString("mota"));
                    is.putExtra("quequan", ob.getString("quequan"));
                    is.putExtra("diachi", ob.getString("diachi"));
                    is.putExtra("mucluong", ob.getString("mucluong"));
                    is.putExtra("kynang", ob.getString("kynang"));
                    is.putExtra("ngoaingu", ob.getString("ngoaingu"));
                    is.putExtra("img", ob.getString("img"));
                    is.putExtra("mahs", ob.getString("mahs"));
                    is.putExtra("key", 0);
                    startActivity(is);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        tv = (TextView) rootView.findViewById(R.id.txts);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }

        });
        return rootView;
    }
    private  void refreshContent() {
        updatelv();
    }
    @Override
    public void onResume() {
        updatelv();
        super.onResume();
    }
     private void updatelv(){
         if(adapter!= null)
         {
             adapter.clear();
             adapter.notifyDataSetChanged();
         }
         asyncRequestObject = new AsyncDataClass();
         asyncRequestObject.execute(AppConfig.URL_UNGTUYEN, macv2);
         mSwipeRefreshLayout.setRefreshing(false);
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
                nameValuePairs.add(new BasicNameValuePair("status", "1"));
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
                Toast.makeText(getActivity(), R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                 mang = new JSONArray(result);
                //   Toast.makeText(getActivity(),mang.length()+"",Toast.LENGTH_SHORT).show();
                if(mang.length()>0) {
                    lv.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.GONE);
                    for (int i = 0; i < mang.length(); i++) {
                        JSONObject ob = mang.getJSONObject(i);
                        celebrities.add(new Profile(ob.getString("nganhnghe"), ob.getString("vitri"), ob.getString("mucluong"), ob.getString("diadiem"), ob.getString("createdate"), ob.getString("mahs"), ob.getString("hoten"), ob.getString("gioitinh2"), ob.getString("ngaysinh"), ob.getString("email"), ob.getString("sdt"), ob.getString("diachi"), ob.getString("quequan"), ob.getString("tentruong"), ob.getString("chuyennganh"), ob.getString("xeploai"), ob.getString("thanhtuu"), ob.getString("namkn"), ob.getString("tencongty"), ob.getString("chucdanh"), ob.getString("mota"), ob.getString("ngoaingu"), ob.getString("kynang"), ob.getString("tencv"), "1", ob.getString("img")));
                    }
                    adapter = new ListViewAdapterThird(getActivity(), android.R.layout.simple_list_item_1, celebrities,0);
                    lv.setAdapter(adapter);

                    tv.setVisibility(View.GONE);
                }else{
                    lv.setVisibility(View.GONE);
                    tv.setVisibility(View.VISIBLE);
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
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
    public void onDestroy() {
        super.onDestroy();
        if ( asyncRequestObject!= null) {
            if (!asyncRequestObject.isCancelled()) {
                asyncRequestObject.cancel(true);
            }
        }
    }
}
