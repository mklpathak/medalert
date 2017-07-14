package com.mukul.panorbit;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mukul.panorbit.data.Addreminder;
import com.mukul.panorbit.data.DatabasePanorbit;
import com.mukul.panorbit.data.MedicneTable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NewMedicine extends AppCompatActivity {
    Toolbar toolbar;

    @BindView(R.id.etMedicineName)
    TextInputEditText etMedicineName;
    @BindView(R.id.etNoOfDoses)
    TextInputEditText etNoOfDoses;
    @BindView(R.id.etDoses)
    TextInputEditText etDosesSize;
    @BindView(R.id.etExpiry)
    TextInputEditText etExpiry;
    @BindView(R.id.pur)
    TextInputEditText purchased;
    @BindView(R.id.etFromDate)
    TextInputEditText etFromDate;
    @BindView(R.id.etToDate)
    TextInputEditText etToDate;
    @BindView(R.id.buttonReminder)
    TextView custom_reminder;
    @BindView(R.id.buttonSave) Button buttonSave;
    @BindView( R.id.baselayout) LinearLayout baseLayout0;
    String today;
    String reminderday;
    CheckBox custom;
    LinearLayout base0;
    String[] Rem = new String[5];
    int i=0;
    int j=0;
    String notifyName;
    int notifyDos;
    Long notifyTime;
    ArrayList<Addreminder> rem2s;
    private PendingIntent pendingIntent;
    AlarmManager alarmManager;
    DatabasePanorbit dbpan;
    Intent myIntent;
    long demo1 = 5000;
    private int mYear, mMonth, mDay, mHour, mMinute;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat datewithtime = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    boolean is_edit = false;
    int medId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medicine);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Medicine");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


        dbpan = new DatabasePanorbit(this);
        Intent intent = getIntent();
        if (intent.getIntExtra("med_id", -1) != -1) {
            is_edit = true;
            medId = intent.getIntExtra("med_id", -1);
        }

        etExpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewMedicine.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, day);
                        etExpiry.setText(sdf.format(c.getTimeInMillis()));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });

        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewMedicine.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, day);
                        etFromDate.setText(sdf.format(c.getTimeInMillis()));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewMedicine.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, day);
                        etToDate.setText(sdf.format(c.getTimeInMillis()));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });

        if (is_edit) {
            updateViews();
        }
    }
    void updateViews() {
        MedicneTable medicneTable = dbpan.getMedicineById(medId);
        Log.e("Medicine: ", medicneTable.toString());
        etMedicineName.setText(medicneTable.getMed_name());
        etExpiry.setText(medicneTable.getMed_expiry());
        etToDate.setText(medicneTable.getMed_couse_to());
        etFromDate.setText(medicneTable.getMed_course_from());
        etNoOfDoses.setText(medicneTable.getNo_of_times_MT2() + "");
        etDosesSize.setText(medicneTable.getMed_dos() + "");
        buttonSave.setText("Update");
        custom_reminder.setVisibility(View.GONE);
    }
    private void addcheckbox(String s) {
        custom = new CheckBox(this);
        custom.setText(s);
        custom.setClickable(false);
        custom.setChecked(true);
        Rem[i]= (String) custom.getText();
        custom.setId(i++);
        baseLayout0.addView(custom);
        if(i==4){baseLayout0.removeView(custom_reminder);
            Toast.makeText(getApplicationContext(),
                    "Max 4 reminders", Toast.LENGTH_LONG).show();
        };

    }

    @OnClick(R.id.buttonSave)
    void onSubmitClicked(View v) {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        datewithtime.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String medicineName = etMedicineName.getText().toString();
        if (medicineName.length() == 0) {
            etMedicineName.setError("Name cannot be empty!");
            return;
        }
        String noOfDoses = etNoOfDoses.getText().toString();
        if (noOfDoses.length() == 0) {
            etNoOfDoses.setError("Doses cannot be empty!");
            return;
        }
        String dosesSize = etDosesSize.getText().toString();
        if (dosesSize.length() == 0) {
            etDosesSize.setError("Cannot be empty!");
            return;
        }
        String expiryDate = etExpiry.getText().toString();
        if (expiryDate.equals("DD/MM/YYYY")) {
            etExpiry.setError("Set expiry date!");
            return;
        }
        String pur = purchased.getText().toString();
        if (pur.length()==0) {
            purchased.setError("Cannot be Empty");
            return;
        }
        String fromDate = etFromDate.getText().toString();
        if (fromDate.equals("DD/MM/YYYY")) {
            etFromDate.setError("Set Course start date!");
            return;
        }
        String toDate = etToDate.getText().toString();
        if (toDate.equals("DD/MM/YYYY")) {
            etToDate.setError("Set Course end date!");
            return;
        }
        MedicneTable medicneTable = new MedicneTable(medicineName, noOfDoses, dosesSize, expiryDate, fromDate, toDate);
        if (is_edit) {
            medicneTable.setID(medId);
        } else {
            medicneTable.setID(-1);
        }
        if (dbpan.addMedicine(medicneTable)) {
            if (is_edit == false) {

            String s2 = "";
            String Reminderday = null;
            Date date2=null;
            Date date3 = null;
            long date = System.currentTimeMillis();
            today = sdf.format(date);
            Date today_1=null;
            // int dos = Integer.parseInt(med_dosage1);
            Log.d("Insert: ", "Inserting ..");if(!"".equals(medicineName) ){
                while (j<(Rem.length-1))
                {
                    if(!"".equals(Rem[j])||Rem[j] != null){
                        try {
                            date2 = sdf.parse(fromDate);
                            date3 = sdf.parse(toDate);
                            today_1 = sdf.parse(today);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long  from_c = date2.getTime();
                        long from_t = date3.getTime();
                        long tod = today_1.getTime();
                        if(from_c<=tod){
                            from_c=tod;}
                        Date cuntime= null;

                        while(from_c<=from_t){

                            Reminderday = sdf.format(from_c);
                            Reminderday = Reminderday+" "+Rem[j];
                            Log.e("hey",Reminderday);
                            try {
                                cuntime=  datewithtime.parse(Reminderday);
                                dbpan.addreminder(new Addreminder(medicineName,Integer.parseInt(dosesSize),cuntime.getTime()+10000));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.e("hey","insert done");
                            from_c= from_c+86400000;
                        }

                        Log.e("Reminder",medicineName+Rem[j]);



                    }
                    j++;
                }}
            }
            myIntent = new Intent(this, MyReceiver.class);
            rem2s = dbpan.getlatestReminder();
            for (Addreminder cn : rem2s) {
               notifyDos= cn.getMed_dos();
                notifyName=  cn.getMed_name();
              notifyTime=  cn.getrem_time();
            }

            myIntent.putExtra("med_name",notifyName);
            myIntent.putExtra("med_dos",String.valueOf(notifyDos));
            //myIntent.putExtra("med_time",String.valueOf(notifyTime));
            pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, myIntent, 0);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC,notifyTime, pendingIntent);





            AlertDialog.Builder builder = new AlertDialog.Builder(this);



            if (is_edit) {
                builder.setMessage("Medicine updated successfully! ");

            } else {
                builder.setMessage("Medicine added successfully!\n" +
                        "! Please check Expiry data of medicine \n" +"" +
                        "Expiry Date provided by you : " + etExpiry.getText().toString());
            }
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (is_edit) {
                builder.setMessage("Couldn't update, please try again!");
            } else {
                builder.setMessage("Couldn't add, please try again!");
            }
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @OnClick(R.id.buttonCancel)
    void OnCancelClicked(View v) {
        finish();
    }
    @OnClick(R.id.buttonReminder)
    void addNewReminder(View v) {
        final Calendar c = Calendar.getInstance();
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        addcheckbox(hourOfDay + ":" + minute);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        timePickerDialog.show();


    }
}
