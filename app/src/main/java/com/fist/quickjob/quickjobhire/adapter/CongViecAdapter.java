package com.fist.quickjob.quickjobhire.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.model.CongViec;
import com.fist.quickjob.quickjobhire.other.CircleTransform;

import java.util.List;

/**
 * Created by SONTHO on 15/08/2016.
 */
public class CongViecAdapter extends ArrayAdapter<CongViec>
{
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Activity mcontext;
    List<CongViec> myArray;
    String uid;
    int type;
    String serverUrl = "";
    String sa="";
    int mtype;
    String[] arrsalary;
  //  ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CongViecAdapter(Activity context, int layoutId, List<CongViec> objects, int type, String uid, int mtype){
        super(context, layoutId, objects);
        this.mcontext=context;
        this.myArray=objects;
        this.uid=uid;
        this.type=type;
        this.mtype=mtype;
        arrsalary=mcontext.getResources().getStringArray(R.array.mucluong);
    }



    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder=null;

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.customitem,null);
        viewHolder = new ViewHolder();
        viewHolder.lin = (LinearLayout) rowView.findViewById(R.id.linit);
        viewHolder.tencongty = (TextView) rowView.findViewById(R.id.tenct);
        viewHolder.trangthai = (TextView) rowView.findViewById(R.id.trangthai);
        viewHolder.tencongviec = (TextView) rowView.findViewById(R.id.tencongviec);
        viewHolder.diadiem = (TextView) rowView.findViewById(R.id.diachi);
        viewHolder.mucluong=(TextView) rowView.findViewById(R.id.luong);
        viewHolder.ngayup=(TextView) rowView.findViewById(R.id.dateup1);
        viewHolder.thumbNail = (ImageView) rowView.findViewById(R.id.thumbnail);
        viewHolder.tencongty.setText(myArray.get(position).tecongty+"");
        int status = Integer.parseInt(myArray.get(position).trangthai);
        if(status==0)
        {
            viewHolder.trangthai.setText(R.string.st_stt_dangcho);
        }else if(status==1){
            viewHolder.trangthai.setText(R.string.st_stt_dachapnhan);
        }else if(status==2){
            viewHolder.trangthai.setText(R.string.st_stt_datuchoi);
        }else {}
        viewHolder.tencongty.setText(myArray.get(position).tecongty);
        viewHolder.tencongviec.setText(myArray.get(position).tencongviec);
        viewHolder.diadiem.setText(myArray.get(position).diadiem);
        viewHolder.mucluong.setText(arrsalary[Integer.parseInt(myArray.get(position).luong)]);
        viewHolder.ngayup.setText(myArray.get(position).dateup);
        Glide.with(mcontext).load(myArray.get(position).url)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(mcontext))
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .into(viewHolder.thumbNail);

        /*viewHolder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(mcontext, EditJobActivity.class);
                    i.putExtra("tencongty", myArray.get(position).tecongty);
                    i.putExtra("diachi", myArray.get(position).diachict);
                    i.putExtra("nganhnghe", myArray.get(position).nganhnghe);
                    i.putExtra("quymo", myArray.get(position).quymo);
                    i.putExtra("motact", myArray.get(position).motact);
                    i.putExtra("nganhNghe", myArray.get(position).nganhNghe);
                    i.putExtra("chucdanh", myArray.get(position).chucdanh);
                    i.putExtra("soluong", myArray.get(position).soluong);
                    i.putExtra("phucloi", myArray.get(position).idungtuyen);
                    i.putExtra("tencongviec", myArray.get(position).tencongviec);
                    i.putExtra("diadiem", myArray.get(position).diadiem);
                    i.putExtra("mucluong", myArray.get(position).luong);
                    i.putExtra("ngayup", myArray.get(position).dateup);
                    i.putExtra("yeucaubangcap", myArray.get(position).bangcap);
                    i.putExtra("dotuoi", myArray.get(position).dotuoi);
                    i.putExtra("ngoaingu", myArray.get(position).ngoaingu);
                    i.putExtra("gioitinh", myArray.get(position).gioitinh);
                    i.putExtra("khac", myArray.get(position).khac);
                    i.putExtra("motacv", myArray.get(position).motacv);
                    i.putExtra("kn", myArray.get(position).kn);
                    i.putExtra("img", myArray.get(position).url);
                    mcontext.startActivity(i);
            }
        }); */

        return rowView;
    }
    static class ViewHolder{
        TextView tencongty,tencongviec,diadiem,mucluong,ngayup,trangthai;
        LinearLayout lin;
        ImageView thumbNail;
    }

}
