package com.mukul.panorbit.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mukul.panorbit.NewMedicine;
import com.mukul.panorbit.R;
import com.mukul.panorbit.SessionManager;
import com.mukul.panorbit.adapters.RvAdapter;
import com.mukul.panorbit.data.DatabasePanorbit;
import com.mukul.panorbit.data.MedicneTable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;




public class List_medicine extends  Fragment {

    Toolbar toolbar;

    TextView text;
    public static List_medicine newInstance() {
        List_medicine fragment = new List_medicine();
        return fragment;
    }
  ImageView sos1;
    MaterialSearchView searchView;

    SessionManager session;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  ArrayList<MedicneTable> med11s = dbpan.getAllCmedicine();

    }



   // SearchView searchView;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    RvAdapter rvAdapter;

    DatabasePanorbit dbpan;
    ArrayList<MedicneTable> med11s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       View view1= inflater.inflate(R.layout.fragment_list_medicine2, container, false);
        ButterKnife.bind(this, view1);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        searchView = (MaterialSearchView)getActivity().findViewById(R.id.search_view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        dbpan = new DatabasePanorbit(getActivity());

        setHasOptionsMenu(true);
        //searchView = (MaterialSearchView) view1.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getDataFromDatabase(newText.toString());
                return false;
            }
        });

//        searchView = (SearchView) view1.findViewById(R.id.searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                getDataFromDatabase(s.toString());
//                return false;
//            }
//        });

        rvAdapter = new RvAdapter(new RvAdapter.OnMedicineItemClicked() {
            @Override
            public void onMedicineLongClicked(final int id) {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Edit");
                arrayAdapter.add("Delete");

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Options for " + id);
                String negativeText = getString(android.R.string.cancel);
                builder.setNegativeButton(negativeText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e("dfsvsv", "facaw "  + i);
                        if (i==1) {
                            dbpan.deleteMedicine(id);
                            getDataFromDatabase(null);
                        } else if (i==0) {
                            Intent intent = new Intent(getActivity(), NewMedicine.class);
                            intent.putExtra("med_id", id);
                            startActivity(intent);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rvAdapter);
        getDataFromDatabase(null);
        return  view1;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater mn = getActivity().getMenuInflater();
        mn.inflate(R.menu.main, menu);

         MenuItem item = menu.findItem(R.id.action_search);
         searchView.setMenuItem(item);


        return true;

    }

    void getDataFromDatabase(String query) {
        rvAdapter.swapCursor(dbpan.getMedicines(query));
    }


}