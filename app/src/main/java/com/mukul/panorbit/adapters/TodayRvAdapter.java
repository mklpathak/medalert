package com.mukul.panorbit.adapters;

import android.database.Cursor;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mukul.panorbit.R;
import com.mukul.panorbit.data.Addreminder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mukul on 18-06-2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class TodayRvAdapter extends RecyclerView.Adapter<TodayRvAdapter.MyViewHolder> {
    Cursor cursor;
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
    ArrayList<Addreminder> medicineTablesList;
    public TodayRvAdapter() {
        super();
        medicineTablesList = new ArrayList<>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_tab1, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        Log.e("dsvsf","dva");
        Addreminder medicneTable = medicineTablesList.get(position);
        holder.medicineTitle.setText( medicneTable.getMed_name().substring(0,1).toUpperCase() + medicneTable.getMed_name().substring(1));
       // holder.medicineDos.setText("Dosage : " + medicneTable.getMed_dos() + " mg");
        holder.remtime.setText(String.valueOf( sdf.format(medicneTable.getrem_time())));
        holder.dosText.setBackgroundColor(Color.BLACK);
        holder.dosText.setText(medicneTable.getMed_dos() + "\n mg");


      //  holder.medicineTimes.setText(" ("+ medicneTable.getNo_of_times_MT2() + " times a day)");
    }

    @Override
    public int getItemCount() {
        return medicineTablesList.size();
    }

    public void swapCursor(ArrayList<Addreminder> medicineTablesList) {
        this.medicineTablesList = medicineTablesList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.med_title)
        TextView medicineTitle;

        @BindView(R.id.med_times)
        TextView medicineTimes;

        @BindView(R.id.remtime)
        TextView remtime;
        @BindView(R.id.dostext)
        TextView dosText;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
