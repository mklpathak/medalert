package com.mukul.panorbit.fragments;


import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mukul.panorbit.data.DatabasePanorbit;
import com.mukul.panorbit.R;
import com.mukul.panorbit.SessionManager;
import com.mukul.panorbit.adapters.TodayRvAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class Today_intake extends Fragment {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm  a");



    public static Today_intake newInstance() {
        Today_intake fragment = new Today_intake();
        return fragment;
    }

    SessionManager session;
    DatabasePanorbit dbpan;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.status)
    TextView Status;
    TodayRvAdapter rvAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_intake, container, false);
        ButterKnife.bind(this, view);


        dbpan = new DatabasePanorbit(getActivity());


        rvAdapter = new TodayRvAdapter();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rvAdapter);
        getDataFromDatabase();
        if(rvAdapter.getItemCount() == 0  ){
            Status.setText("You don't have to take medicine today");
            Status.setHeight(200);
            Status.setPadding(16,16,16,16);
            Status.setTextSize(20);

        }
        else{
            Status.setVisibility(View.GONE);
        }
        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void getDataFromDatabase() {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            Calendar ca;

        Log.d("Time zone",TimeZone.getDefault().getDisplayName());
        Log.e( "time :" ,String.valueOf( sdf.format(System.currentTimeMillis())));
        rvAdapter.swapCursor(dbpan.getTodaysMedicines());
    }

}
