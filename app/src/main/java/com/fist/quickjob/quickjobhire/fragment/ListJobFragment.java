package com.fist.quickjob.quickjobhire.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.activity.CreateJobActivity;
import com.fist.quickjob.quickjobhire.activity.JobDetailActivity;
import com.fist.quickjob.quickjobhire.adapter.CongViecAdapter;
import com.fist.quickjob.quickjobhire.config.AppConfig;
import com.fist.quickjob.quickjobhire.model.CongViec;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListJobFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListJobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListJobFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private LinearLayout lin2;int hoso;
    private ListView lv;
    private CongViecAdapter adapter;
    private ArrayList<CongViec> celebrities = new ArrayList<CongViec>();
    private String id;
    private TextView ss;int status=0;
    private AlertDialog progressDialog;
    private String dc="",logo1="",nn="",mt="",qm="",tenct="",pl="";
    SessionManager session;
    SharedPreferences.Editor edit;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AsyncDataClass asyncRequestObject;
    private OnFragmentInteractionListener mListener;
    JSONArray mang;
    ProgressBar progressBar;
    public ListJobFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListJobFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListJobFragment newInstance(String param1, String param2) {
        ListJobFragment fragment = new ListJobFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.list_job_layout, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.pbLoader);
        ss = (TextView) rootView.findViewById(R.id.lin);
        lin2 = (LinearLayout) rootView.findViewById(R.id.lin2);
        session = new SessionManager(getActivity());
        SharedPreferences pref=getActivity().getSharedPreferences("JobFindPref", MODE_PRIVATE);
        edit=pref.edit();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        id=user.get(SessionManager.KEY_ID);
        lv =(ListView) rootView.findViewById(R.id.listhoso);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject obs = mang.getJSONObject(i);
                    Intent s = new Intent(getActivity(), JobDetailActivity.class);
                    s.putExtra("macv", obs.getString("macv"));
                    s.putExtra("matd", obs.getString("matd"));
                    s.putExtra("tencongty", obs.getString("tenct"));
                    s.putExtra("diachi", obs.getString("diachi"));
                    s.putExtra("nganhnghe", obs.getString("nganhnghe"));
                    s.putExtra("quymo", obs.getString("quymo"));
                    s.putExtra("motact", obs.getString("motact"));
                    s.putExtra("nganhNghe", obs.getString("nn"));
                    s.putExtra("chucdanh", obs.getString("chucdanh"));
                    s.putExtra("soluong", obs.getString("soluong"));
                    s.putExtra("phucloi", obs.getString("phucloi"));
                    s.putExtra("tencongviec", obs.getString("tencv"));
                    s.putExtra("diadiem", obs.getString("diadiem"));
                    s.putExtra("mucluong", obs.getString("mucluong"));
                    s.putExtra("ngayup", obs.getString("hannophoso"));
                    s.putExtra("yeucaubangcap", obs.getString("bangcap"));
                    s.putExtra("dotuoi", obs.getString("dotuoi"));
                    s.putExtra("ngoaingu", obs.getString("ngoaingu"));
                    s.putExtra("gioitinh", obs.getString("gioitinh"));
                    s.putExtra("khac", obs.getString("khac"));
                    s.putExtra("motacv", obs.getString("motacv"));
                    s.putExtra("kn", obs.getString("kynang"));
                    s.putExtra("img", obs.getString("img"));
                    startActivity(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }
    @Override
    public void onResume() {
        if(adapter!= null)
        {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_XUATHS1, id);
        super.onResume();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            String jsonResult = "";
            try {
                nameValuePairs.add(new BasicNameValuePair("id", params[1]));
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
                progressBar.setVisibility(View.GONE);
                return;
            }
            try {
                 mang = new JSONArray(result);
                if(mang.length()>0) {
                    status=1;
                    JSONObject obs = mang.getJSONObject(0);
                    dc = obs.getString("diachi");
                    nn= obs.getString("nganhnghe");
                    mt = obs.getString("motact");
                    qm = obs.getString("quymo");
                    logo1 = obs.getString("img");
                    tenct =obs.getString("tenct");
                    int stt=0;
                    stt=obs.getInt("status");
                    if(stt==0) {
                        for (int i = 0; i < mang.length(); i++) {
                            JSONObject ob = mang.getJSONObject(i);
                            celebrities.add(new CongViec(ob.getString("macv"), ob.getString("tencv"), ob.getString("tenct"), obs.getString("diachi"), obs.getString("nganhnghe"), obs.getString("motact"), obs.getString("quymo"), obs.getString("nn"), obs.getString("chucdanh"), obs.getString("soluong"), ob.getString("mucluong"), ob.getString("diadiem"), ob.getString("hannophoso"), ob.getString("motacv"), ob.getString("bangcap"), ob.getString("dotuoi"), ob.getString("ngoaingu"), ob.getString("gioitinh"), ob.getString("khac"), ob.getString("kynang"), ob.getString("img"), ob.getString("phucloi"), "5", ""));
                        }
                        adapter = new CongViecAdapter(getActivity(), android.R.layout.simple_list_item_1, celebrities, 5, "", 3);
                        lv.setAdapter(adapter);
                        ss.setVisibility(View.GONE);
                        lin2.setVisibility(View.VISIBLE);
                    }else{
                        ss.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
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
    private void createCV() {
        Intent i = new Intent(getActivity(), CreateJobActivity.class);
        i.putExtra("uniqueid", id);
        i.putExtra("tenct", tenct);
        i.putExtra("quymo", qm);
        i.putExtra("diachi", dc);
        i.putExtra("nganhnghe", nn);
        i.putExtra("mota", mt);
        i.putExtra("logo", logo1);
        i.putExtra("status", status);
        startActivity(i);
    }
}
