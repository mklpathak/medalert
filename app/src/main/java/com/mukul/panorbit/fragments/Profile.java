package com.mukul.panorbit.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mukul.panorbit.R;
import com.mukul.panorbit.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    public static Profile newInstance() {
        Profile fragment = new Profile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    SessionManager session;

    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvAge) TextView tvAge;
    @BindView(R.id.tvSOS) TextView tvSOS;
    @BindView(R.id.sdvImage)
    SimpleDraweeView sdvImage;
    ImageView sos1;

//    @BindView(R.id.tvSOS) TextView tvSOS;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view3 = inflater.inflate(R.layout.fragment_profile2, container, false);
        ButterKnife.bind(this, view3);
        sos1 = (ImageView) view3.findViewById(R.id.sos3);
        sos1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new SessionManager(getActivity());


                String ph= session.getSOS();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+91"+ph+""));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                    return;
                }
                startActivity(callIntent);
                Log.e("call:","9999595332");
            }
        });
        session = new SessionManager(getActivity());

        tvName.setText(session.getName());
        tvAge.setText("Age: " +session.getAge());
        tvSOS.setText("SOS: " +session.getSOSName()+" ( "+session.getSOS()+" )");
        sdvImage.setImageURI(session.getImageUri());

        LinearLayout li = (LinearLayout) view3.findViewById(R.id.addmedicine);
        li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LinearLayout setting = (LinearLayout) view3.findViewById(R.id.settings);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });


        return view3;
    }


    void logOut() {
//        session.clearAll();
//        Intent intent = new Intent(getActivity(), HomeActivity.class);
//        startActivity(intent);
//        getActivity().finish();

    }
}
