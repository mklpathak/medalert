package com.mukul.panorbit.adapters;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mukul.panorbit.R;
import com.mukul.panorbit.data.DatabasePanorbit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mukul on 16-06-2017.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    Cursor cursor;

    OnMedicineItemClicked onMedicineItemClicked;

    public RvAdapter(OnMedicineItemClicked onMedicineItemClicked) {
        super();
        cursor = null;
        this.onMedicineItemClicked = onMedicineItemClicked;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_tab1, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e("dsvsf","dva");
        cursor.moveToPosition(position);
        String name = cursor.getString(cursor.getColumnIndex(DatabasePanorbit.Med_name));
        String dosage = cursor.getString(cursor.getColumnIndex(DatabasePanorbit.Med_dos));
        String times = cursor.getString(cursor.getColumnIndex(DatabasePanorbit.Med_nooftime));
        holder.medicineTitle.setText(name.substring(0,1).toUpperCase()+name.substring(1));
        holder.medicineTimes.setText( times+ " times a day");
        holder.dosText.setBackgroundColor(Color.BLACK);
        holder.dosText.setText(dosage+ "\n mg");


    }

    @Override
    public int getItemCount() {
        if (cursor == null)
            return 0;
        else
            return cursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @BindView(R.id.med_title)
        TextView medicineTitle;

        @BindView(R.id.med_times)
        TextView medicineTimes;
        @BindView(R.id.dostext)
        TextView dosText;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            cursor.moveToPosition(getAdapterPosition());
            int name = cursor.getInt(cursor.getColumnIndex(DatabasePanorbit.ID));
            onMedicineItemClicked.onMedicineLongClicked(name);
            return false;
        }
    }

    public interface OnMedicineItemClicked {
        void onMedicineLongClicked(int id);
    }
}
