package com.fist.quickjob.quickjobhire.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.activity.CreateJob_step;


/**
 * Created by VinhNguyen on 1/8/2017.
 */

public class createjob_step_one extends Fragment {
    View view;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_step_one,container,false);
        TextView next =(TextView) view.findViewById(R.id.txtnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateJob_step.setStepper(1);
            }
        });
        return view;
    }
}
