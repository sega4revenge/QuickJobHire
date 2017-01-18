package com.fist.quickjob.quickjobhire.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.fragment.CompanyDetailFragment;
import com.fist.quickjob.quickjobhire.fragment.JobAboutFragment;

import java.util.ArrayList;
import java.util.List;

public class JobDetailActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private NetworkImageView logo;
    private static final int REQUEST_CALL = 0;
    private ListView lv;

    public static final String TAG = "JobDetailActivity";
    public String macv;
    int status = 0,luong=0,hv=0,gt=0;
    private String id,sdt, tencv, tenct, diadiem, motact, ngayup, number, dotuoi, ngoaingu, chucdanh, khac, motacv, kn, img,quymo,location,detail,matd,diachi,nganhnghe,nganhNghe,soluong,phucloi,gioitinh,tuoi;
    String mucluong="",hannop="",yeucaubc="";


    private CollapsingToolbarLayout collapsingToolbar;
    int type;
    private int mPreviousVisibleItem;

    private com.github.clans.fab.FloatingActionButton fabMenu;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_j);
//        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.floatbutton);
        status = 1;
        actionGetIntent();
        init();
        setData();
      ;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(tenct);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(JobDetailActivity.this, EditJobActivity.class);
                s.putExtra("macv", macv);
                s.putExtra("matd", matd);
                s.putExtra("tencongty", tenct);
                s.putExtra("diachi",diachi);
                s.putExtra("nganhnghe", nganhnghe);
                s.putExtra("quymo", quymo);
                s.putExtra("motact", motact);
                s.putExtra("nganhNghe",nganhNghe);
                s.putExtra("chucdanh", chucdanh);
                s.putExtra("soluong", soluong);
                s.putExtra("phucloi",phucloi);
                s.putExtra("tencongviec", tencv);
                s.putExtra("diadiem",diadiem);
                s.putExtra("mucluong",mucluong);
                s.putExtra("ngayup", hannop);
                s.putExtra("yeucaubangcap",yeucaubc);
                s.putExtra("dotuoi", tuoi);
                s.putExtra("ngoaingu", ngoaingu);
                s.putExtra("gioitinh", gioitinh);
                s.putExtra("khac", khac);
                s.putExtra("motacv", motacv);
                s.putExtra("kn", kn);
                s.putExtra("img", img);
                startActivity(s);
            }
        });



    }





    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new JobAboutFragment(), getResources().getString(R.string.st_about_job));
        adapter.addFragment(new CompanyDetailFragment(), getResources().getString(R.string.st_about_company));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setData() {
        id = MainActivity.uid;
    }

    private void init() {

        fabMenu = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabMenu);


//        logo = (NetworkImageView) findViewById(R.id.backdrop);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }

    private void actionGetIntent() {
        Intent i = getIntent();
     /*   type= i.getIntExtra("type",1);
        tencv = i.getStringExtra("tencongviec");
        tenct = i.getStringExtra("tencongty");
        diadiem = i.getStringExtra("diadiem");
        luong=Integer.parseInt(i.getStringExtra("mucluong"));
        ngayup = i.getStringExtra("ngayup");
        if(i.getStringExtra("yeucaubangcap").equals(""))
        {

        }else{
            hv=Integer.parseInt(i.getStringExtra("yeucaubangcap"));
        }

        dotuoi = i.getStringExtra("dotuoi");
        ngoaingu = i.getStringExtra("ngoaingu");
        if(i.getStringExtra("gioitinh").equals(""))
        {

        }else{
            gt=Integer.parseInt(i.getStringExtra("gioitinh"));
        }

        khac = i.getStringExtra("khac");
        motacv = i.getStringExtra("motacv");
        kn = i.getStringExtra("kn");
        img = i.getStringExtra("img");
        macv = i.getStringExtra("macv");
        sdt = i.getStringExtra("sdt");
        quymo = i.getStringExtra("quymo");
     //   jobcompany = i.getStringExtra("nganhnghe");
        detail = i.getStringExtra("nn");
        location = i.getStringExtra("diachi");
        matd=i.getStringExtra("matd");
        motact=i.getStringExtra("motact");
        number=i.getStringExtra("soluong");
        chucdanh=i.getStringExtra("chucdanh");
        */

        macv=i.getStringExtra("macv");
        matd=i.getStringExtra("matd");
       tenct=i.getStringExtra("tencongty");
        diachi=i.getStringExtra("diachi");
        nganhnghe=i.getStringExtra("nganhnghe");
        quymo=i.getStringExtra("quymo");
        motact=i.getStringExtra("motact");
        nganhNghe=i.getStringExtra("nganhNghe");
        chucdanh=i.getStringExtra("chucdanh");
        soluong=i.getStringExtra("soluong");
        phucloi=i.getStringExtra("phucloi");
        tencv=i.getStringExtra("tencongviec");
        diadiem=i.getStringExtra("diadiem");
        mucluong=i.getStringExtra("mucluong");
        hannop=i.getStringExtra("ngayup");
        yeucaubc=i.getStringExtra("yeucaubangcap");
         tuoi=i.getStringExtra("dotuoi");
        ngoaingu=i.getStringExtra("ngoaingu");
        gioitinh=i.getStringExtra("gioitinh");
        khac=i.getStringExtra("khac");
        motacv=i.getStringExtra("motacv");
        kn=i.getStringExtra("kn");
       img=i.getStringExtra("img");
    }

    private void takePicture() {
        String phone = sdt;
        String number = phone + "";
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
                Toast.makeText(JobDetailActivity.this, R.string.st_pemissonCamera, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Intent s= getIntent();
//        matd= s.getStringExtra("matd");
//        AsyncDataClass asyncRequestObject = new AsyncDataClass();
//        asyncRequestObject.execute(AppConfig.URL_VIEW, matd,"");

    }




    //    private void loadBackdrop() {
    //      logo.setImageUrl(hinhanh, imageLoader);
    //   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(type==1 || type==3)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_detail_job, menu);
        }
        return true;
    }










    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
