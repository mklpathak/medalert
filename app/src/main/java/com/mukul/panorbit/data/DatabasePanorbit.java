package com.mukul.panorbit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.mukul.panorbit.data.MedicneTable;

import java.text.ParseException;
import java.util.ArrayList;

import static android.R.attr.version;

/**
 * Created by Mukul on 15-06-2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class DatabasePanorbit extends SQLiteOpenHelper {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dateWithTime = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 3;

    // Database Name
    public static final String DATABASE_NAME = "Pan_Medicine";

    // Contacts table name
    public static final String TABLE_med = "Medicine";
    public static final String Reminder = "reminder";

    // Contacts Table Columns names
    public static final String ID = "Id";
    public static final String Med_name = "med_name";
    public static final String Med_nooftime = "No_times";
    public static final String Med_dos = "med_dos";
    public static final String med_expiry = "med_expire";
    public static final String course_from = "course_from";
    public static final String course_to = "course_to";
    public static final String sno = "sno";
    public static final String time = "Reminder_time";



    public DatabasePanorbit(Context context) {
        super(context, DATABASE_NAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_Medicine_TABLE = "CREATE TABLE " + TABLE_med + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Med_name + " TEXT," +
                Med_nooftime + " INTEGER,"
                + Med_dos + " INTEGER," + med_expiry + " TEXT," + course_from + " TEXT," + course_to + " TEXT" + ")";
        db.execSQL(CREATE_Medicine_TABLE);
        String CREATE_Reminder_TABLE = "CREATE TABLE " + Reminder + "("
                + sno + " INTEGER PRIMARY KEY AUTOINCREMENT," + Med_name + " TEXT," + Med_dos + " INTEGER," +
                time + " INTEGER )";
        db.execSQL(CREATE_Reminder_TABLE);

    }
    public String addreminder(Addreminder remind){SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Med_name, remind.getMed_name());
        values.put(Med_dos, remind.getMed_dos());
        values.put(time, remind.getrem_time());

        try {
            db.insert(Reminder, null, values);
        } catch (SQLException ex) {
            return String.valueOf(ex);
        }
        db.close();
        return null;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_med);
        onCreate(db);
    }

    public boolean addMedicine(MedicneTable med) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Med_name, med.getMed_name());
        values.put(Med_nooftime, med.getNo_of_times_MT2());
        values.put(Med_dos, med.getMed_dos());
        values.put(med_expiry, med.getMed_expiry());
        values.put(course_from, med.getMed_course_from());
        values.put(course_to, med.getMed_couse_to());
        if (med.getID() != -1) {
            try {
                db.update(TABLE_med, values, ID + " = "+med.getID(), null);
            }catch (SQLException ex) {
                return false;
            }
        } else {
            try {
                db.insert(TABLE_med, null, values);
            } catch (SQLException ex) {
                return false;
            }
        }
        db.close();
        return true;
    }

    // Getting single contact
    @RequiresApi(api = Build.VERSION_CODES.N)
    public MedicneTable getMedicine(String Med_name_search) {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_med, new String[]{ID,
                        Med_name, Med_dos, Med_nooftime, med_expiry, course_from, course_to}, ID + "=?",
                new String[]{Med_name_search}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        MedicneTable med = new MedicneTable(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return med;

    }

    // Getting All Contacts
    public Cursor getMedicines(String query) {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String selectQuery = "SELECT  * FROM " + TABLE_med + " order by Id DESC;";
        if (query != null && query != "") {
            selectQuery = "SELECT  * FROM " + TABLE_med + " where med_name like '" + query + "%';";
        }
        return this.getReadableDatabase().rawQuery(selectQuery, null);
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Addreminder> getTodaysMedicines()  {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        dateWithTime.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        Long d1 = null;
        try {
             d1 = dateWithTime.parse(String.valueOf(sdf.format(System.currentTimeMillis()))+" 23:59").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("date :",String.valueOf(d1));

        String selectQuery = "SELECT  * FROM " + Reminder+ " where "+time +" BETWEEN "+ System.currentTimeMillis() +" and "+d1 +" order by "+ time+ " asc ";
        Cursor cursor = this.getReadableDatabase().rawQuery(selectQuery, null);
        ArrayList<Addreminder> medicineTablesList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Addreminder medicneTable = new Addreminder();
            medicneTable.setsno(cursor.getInt(cursor.getColumnIndex(DatabasePanorbit.sno)));
            medicneTable.setMed_name(cursor.getString(cursor.getColumnIndex(DatabasePanorbit.Med_name)));
            medicneTable.setMed_dosage(cursor.getInt(cursor.getColumnIndex(DatabasePanorbit.Med_dos)));
            medicneTable.setTime_rem(Long.parseLong(cursor.getString(cursor.getColumnIndex(DatabasePanorbit.time))));

                medicineTablesList.add(medicneTable);

            cursor.moveToNext();
        }
        return  medicineTablesList;
    }

    public MedicneTable getMedicineById(int _id) {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        MedicneTable medicneTable = new MedicneTable();
        String selectQuery = "SELECT  * FROM " + TABLE_med + " where Id = "+_id+";";
        Cursor cursor = this.getReadableDatabase().rawQuery(selectQuery, null);
        cursor.moveToFirst();
        medicneTable.setID(cursor.getInt(cursor.getColumnIndex(DatabasePanorbit.ID)));
        medicneTable.setMed_name(cursor.getString(cursor.getColumnIndex(DatabasePanorbit.Med_name)));
        medicneTable.setNo_of_times_MT2(cursor.getInt(cursor.getColumnIndex(DatabasePanorbit.Med_nooftime)));
        medicneTable.setMed_dosage(cursor.getInt(cursor.getColumnIndex(DatabasePanorbit.Med_dos)));
        medicneTable.setMed_course_to(cursor.getString(cursor.getColumnIndex(DatabasePanorbit.course_to)));
        medicneTable.setMed_coursefrom(cursor.getString(cursor.getColumnIndex(DatabasePanorbit.course_from)));
        medicneTable.setMed_expiry(cursor.getString(cursor.getColumnIndex(DatabasePanorbit.med_expiry)));
        return medicneTable;
    }



    public ArrayList<MedicneTable> getAllCmedicine(String searchQuery) {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        ArrayList<MedicneTable> MedList = new ArrayList<MedicneTable>();
        // Select All Query

        String selectQuery = "SELECT  * FROM " + TABLE_med + " order by Id DESC;";
//        if (searchQuery != null && searchQuery != "") {
//            selectQuery = "SELECT  * FROM " + TABLE_med + " where med_name like %" + searchQuery + "%;";
//        }
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MedicneTable med = new MedicneTable();
                med.setID(Integer.parseInt(cursor.getString(0)));
                med.setMed_name(cursor.getString(1));
                med.setNo_of_times_MT2(Integer.parseInt(cursor.getString(2)));
                med.setMed_dosage(Integer.parseInt(cursor.getString(3)));
                med.setMed_expiry(cursor.getString(4));
                med.setMed_coursefrom(cursor.getString(5));
                med.setMed_course_to(cursor.getString(6));

                // Adding contact to list
                MedList.add(med);
            } while (cursor.moveToNext());
        }

        // return contact list
        return MedList;
    }

    // Getting contacts Count
    public int getMedineCountCount() {
        int count=0;
        String countQuery = "SELECT  * FROM " + TABLE_med;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            count++;
            cursor.moveToNext();
        }

        // return count
        return count;
    }

    public ArrayList<Addreminder> getlatestReminder() {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        ArrayList<Addreminder> RemList = new ArrayList<Addreminder>();
        // Select All Query

        String selectrem = "SELECT  * FROM " + Reminder+ " where "+time +" > "+ System.currentTimeMillis() +" order by "+ time+ " asc limit 1;";
//        if (searchQuery != null && searchQuery != "") {
//            selectQuery = "SELECT  * FROM " + TABLE_med + " where med_name like %" + searchQuery + "%;";
//        }
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectrem, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Addreminder med = new Addreminder();
                med.setsno(Integer.parseInt(cursor.getString(0)));
                med.setMed_name(cursor.getString(1));
                med.setMed_dosage(Integer.parseInt(cursor.getString(2)));
                med.setTime_rem(Long.parseLong(cursor.getString(3)));



                // Adding contact to list
                RemList.add(med);
            } while (cursor.moveToNext());
        }

        // return contact list
        return RemList;
    }

    // Updating single contact
    public int updateMedicine(MedicneTable med) {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Med_name, med.getMed_name());
        values.put(Med_dos, med.getMed_dos());
        values.put(med_expiry, med.getMed_expiry());
        values.put(course_from, med.getMed_course_from());
        values.put(course_to, med.getMed_couse_to());

        // updating row
        return db.update(TABLE_med, values, Med_name + " = ?",
                new String[]{med.getMed_name()});
    }

    // Deleting single contact
    public void deleteMedicine(int id) {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String deleteQuery = "DELETE FROM " + TABLE_med+ " where "+ ID +" = '"+id+"';";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteQuery);
        db.close();
    }
}

