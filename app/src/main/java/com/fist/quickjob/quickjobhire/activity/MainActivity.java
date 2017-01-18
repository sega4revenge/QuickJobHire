package com.fist.quickjob.quickjobhire.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.config.AppConfig;
import com.fist.quickjob.quickjobhire.firebase.MyFirebaseInstanceIDService;
import com.fist.quickjob.quickjobhire.fragment.ListJobFragment;
import com.fist.quickjob.quickjobhire.fragment.ProfileManager;
import com.fist.quickjob.quickjobhire.fragment.SearchProfileFragment;
import com.fist.quickjob.quickjobhire.pref.SessionManager;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.iid.FirebaseInstanceId;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.fist.quickjob.quickjobhire.pref.SessionManager.KEY_LOGO;


public class MainActivity extends AppCompatActivity   {
    double lat,lng;
    static String logopref="";
    public static String uid, email1;
    SessionManager session;
    private static final String TAG_SEARCH = "search";
    AsyncDataClass asyncRequestObject;
    private String[] activityTitles;
    private String emailpref, namepref, logo="",ten="",dc="",mt="",nn="",phone="",quymo="";
    private static final String TAG = "MyFirebaseIIDService";
    SharedPreferences.Editor edit;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar mToolbar;
    CoordinatorLayout coordinatorLayout;
    FloatingActionMenu fabMenu;
    String[] arrnn,arrdd,arrsalary;
    String location="", job="";
    private ArrayAdapter adapternn,adapterdd,adaptersalary,adapterbc;
    private com.github.clans.fab.FloatingActionButton fabCall, fabSave,fablocation;
    private Toolbar toolbar;
    private Spinner nganhnghe,mucluong,diadiem,spbc;
    int status=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        SharedPreferences pref = getSharedPreferences("JobFindPref", MODE_PRIVATE);
        edit = pref.edit();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        emailpref = user.get(SessionManager.KEY_EMAIL);
        namepref = user.get(SessionManager.KEY_NAME);
        logopref = user.get(KEY_LOGO);
        uid = user.get(SessionManager.KEY_ID);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        MyFirebaseInstanceIDService s = new MyFirebaseInstanceIDService();
        s.sendRegistrationToServer(refreshedToken, uid);
        initToolbar();
        init();
        events();
        initViewPagerAndTabs();
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_INFORMATION,uid);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tabCall = tabLayout.getTabAt(position);
                switch (position) {
                    case 0:
                        setToolbarTitle(0);
                        showFab();
                        tabCall.setIcon(R.drawable.icon_menus);
                        break;
                    case 1:
                        backtop();
                        hideFab();
                        setToolbarTitle(1);
                        tabCall.setIcon(R.drawable.icons_menu2);
                        TabLayout.Tab tabCall2 = tabLayout.getTabAt(0);
                        tabCall2.setIcon(R.drawable.icon_menus);
                        break;
                    case 2:
                        setToolbarTitle(2);
                        showFab();
                        tabCall.setIcon(R.drawable.icon_menu3);
                        TabLayout.Tab tabCall3 = tabLayout.getTabAt(0);
                        tabCall3.setIcon(R.drawable.icon_menus);
                        break;
                    case 3:
                        setToolbarTitle(3);
                        hideFab();
                        tabCall.setIcon(R.drawable.icon_menu4);
                        TabLayout.Tab tabCall4 = tabLayout.getTabAt(0);
                        tabCall4.setIcon(R.drawable.icon_menus);
                        break;
                    default:
                        tabCall.setIcon(R.drawable.ic_home_white_24dp);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

//                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mToolbar.getLayoutParams();
//                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
//                if (behavior != null) {
//                    behavior.onNestedFling(coordinatorLayout, mToolbar, null, 0, 10000, true);
//                }
            }
        });
        setupTabIcons();

    }
//
//    private void hideFab() {
//        if (fabMenu.getVisibility() == View.VISIBLE) {
//            fabMenu.setVisibility(View.GONE);
//        }
//    }
//    private void showFab() {
//        if (fabMenu.getVisibility() == View.GONE) {
//            fabMenu.setVisibility(View.VISIBLE);
//        }
//    }

    private void hideFab() {
        if (fabMenu.getVisibility() == View.VISIBLE) {
            fabMenu.setVisibility(View.GONE);
        }
    }
    private void showFab() {
        if (fabMenu.getVisibility() == View.GONE) {
            fabMenu.setVisibility(View.VISIBLE);
        }
    }
    private void setToolbarTitle(int pos) {
        getSupportActionBar().setTitle(activityTitles[pos]);
    }
    private void backtop() {
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appBarLayout);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedFling(coordinatorLayout, appbar, null, 0, -1000, true);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_library_books_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_work_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_account_circle_black_24dp);
    }
    private void initToolbar() {


        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initViewPagerAndTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new SearchProfileFragment(),"");
        pagerAdapter.addFragment(new ProfileManager(), "");
        pagerAdapter.addFragment(new ListJobFragment(), "");
        pagerAdapter.addFragment(new SettingActivity(), "");
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    static class PagerAdapter extends FragmentPagerAdapter  {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {

            case R.id.mn_notification:
               // startActivity(new Intent(MainActivity.this,CreateJobActivity.class));
                break;


        }
        return super.onOptionsItemSelected(item);
    }
    private void events() {

        fabCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
           AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
           LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           final View view1 = inflater.inflate(R.layout.createjob_steptwo, null);
                final EditText num = (EditText) view1.findViewById(R.id.ednum);
                final EditText limit = (EditText) view1.findViewById(R.id.limit);
                final EditText khac = (EditText) view1.findViewById(R.id.yeucaukhac);
                limit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"321", Toast.LENGTH_SHORT).show();
                                       Calendar newCalendar = Calendar.getInstance();
                                        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                Calendar newDate = Calendar.getInstance();
                                                newDate.set(year, monthOfYear, dayOfMonth);
                                                Locale.setDefault(new Locale("vi_VN"));
                                                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                                limit.setText(dateFormatter.format(newDate.getTime()));
                                            }

                                        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                                        fromDatePickerDialog.show();
                    }
                });
            final EditText namecompany = (EditText) view1.findViewById(R.id.tenct);
                namecompany.setText(ten);
           arrnn = getResources().getStringArray(R.array.nganhNghe);
           arrdd = getResources().getStringArray(R.array.diadiem);
           arrsalary = getResources().getStringArray(R.array.mucluong);
           adapternn = new ArrayAdapter<String>(MainActivity.this, R.layout.customspniner, arrnn);
           adapternn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           adaptersalary = new ArrayAdapter<String>(MainActivity.this, R.layout.customspniner, arrsalary);
           adaptersalary.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           final Spinner spnganh = (Spinner) view1.findViewById(R.id.nganhnghe);
           spnganh.setAdapter(adapternn);
           final Spinner salary = (Spinner) view1.findViewById(R.id.mucluong);
           salary.setAdapter(adaptersalary);
                spbc = (Spinner) view1.findViewById(R.id.spbc);
                adapterbc = new ArrayAdapter<String>(MainActivity.this, R.layout.customspniner,getResources().getStringArray(R.array.spChucDanh));
                adapterbc.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spbc.setAdapter(adapterbc);
                builder.setTitle(getString(R.string.st_createfast));
                builder.setView(view1);
                String positiveText = getString(android.R.string.ok);
                builder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int pos = spnganh.getSelectedItemPosition();
                                int pos2 = salary.getSelectedItemPosition();
                                int pos3 = spbc.getSelectedItemPosition();
                                EditText namejob = (EditText) view1.findViewById(R.id.tencv);
                                EditText address = (EditText) view1.findViewById(R.id.eddc);
                                EditText detail = (EditText) view1.findViewById(R.id.motacv);
                                String limitapply =limit.getText()+"";
                                String number =num.getText()+"";
                                String name =namecompany.getText()+"";
                                String job =namejob.getText()+"";
                                String addr =address.getText()+"";
                                String deta =detail.getText()+"";
                                String difference =khac.getText()+"";
                                if(name=="" || job=="" || addr=="" || number=="" || limitapply==""){
                                    Toast.makeText(MainActivity.this,R.string.st_err_taocv+"", Toast.LENGTH_SHORT).show();
                                }else{
                                    getLocationFromAddress(MainActivity.this,addr);
                                    status=1;
                                    asyncRequestObject = new AsyncDataClass();
                                    asyncRequestObject.execute(AppConfig.URL_CREATEJOB,uid,job,name,addr,deta,pos+"",pos2+"",lat+"",lng+"",number,limitapply,pos3+"",difference);
                                }
                            }
                        });

                String negativeText = getString(android.R.string.cancel);
                builder.setNegativeButton(negativeText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // negative button logic
                            }
                        });

                AlertDialog dialog = builder.create();
                // display dialog
                dialog.show();
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this,CreateJobActivity.class);
                i.putExtra("tenct",ten);
                i.putExtra("quymo",quymo);
                i.putExtra("diachi",dc);
                i.putExtra("nganhnghe",ten);
                i.putExtra("mota",mt);
                i.putExtra("logo",logo);
                startActivity(i);
            }
        });

    }

    public void getLocationFromAddress(Context context,String strAddress) {
        Geocoder coder = new Geocoder(context);
        if (strAddress != null && !strAddress.isEmpty()) {
            try {
                List<Address> addressList = coder.getFromLocationName(strAddress, 5);
                if (addressList != null && addressList.size() > 0) {
                    lat = addressList.get(0).getLatitude();
                    lng = addressList.get(0).getLongitude();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } // end catch
        } // end if

    }
    @Override
    protected void onResume() {
        super.onResume();
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_INFORMATION,uid);

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
                if(status!=0)
                {
                    nameValuePairs.add(new BasicNameValuePair("namejob", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("tencongty", params[3]));
                    nameValuePairs.add(new BasicNameValuePair("diadiem", params[4]));
                    nameValuePairs.add(new BasicNameValuePair("motacongviec", params[5]));
                    nameValuePairs.add(new BasicNameValuePair("nganhnghe", params[6]));
                    nameValuePairs.add(new BasicNameValuePair("mucluong", params[7]));
                    nameValuePairs.add(new BasicNameValuePair("latitude", params[8]));
                    nameValuePairs.add(new BasicNameValuePair("longitude", params[9]));
                    nameValuePairs.add(new BasicNameValuePair("numberapply", params[10]));
                    nameValuePairs.add(new BasicNameValuePair("limitapply", params[11]));
                    nameValuePairs.add(new BasicNameValuePair("bangcap", params[12]));
                    nameValuePairs.add(new BasicNameValuePair("yeucaukhac", params[13]));
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
                Toast.makeText( MainActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            if(status==0) {
                try {
                    JSONArray mang = new JSONArray(result);
                    if (mang.length() > 0) {
                        JSONObject obs = mang.getJSONObject(0);
                        ten = obs.getString("tencongty");
                        dc = obs.getString("diachi");
                        mt = obs.getString("mota");
                        phone = obs.getString("sdt");
                        quymo = obs.getString("quymo");
                        nn = obs.getString("nganhnghe");
                        logo = obs.getString("img");
                        edit.putString("logo", logo);
                        edit.putString("email", ten);
                        edit.commit();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                if(result.equals("1"))
                {
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.st_create_success) +"", Toast.LENGTH_SHORT).show();
                }
                status=0;
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
    private void init() {

        fabMenu = (FloatingActionMenu) findViewById(R.id.fabMenu);
        fabCall = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabCall);
        fabSave = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabSave);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}