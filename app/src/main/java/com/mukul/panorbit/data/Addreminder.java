package com.mukul.panorbit.data;

/**
 * Created by Mukul on 17-06-2017.
 */

public class Addreminder {
    private int sno ;
    private  String Med_name ;
    private long time_rem;

    private int dosage_med;

    Addreminder(){

    }
    Addreminder  (int sno,String Medname,int dos,long time){
        this.sno=sno;
        this.Med_name = Medname;
        this.time_rem=time;
        this.dosage_med=dos;



    }
    public Addreminder(String Medname, int dos, long time){
        this.Med_name = Medname;
        this.time_rem=time;
        this.dosage_med=dos;

    }

    public int getsno(){
        return this.sno;
    }
    public void setsno(int _sno){
        this.sno=_sno;

    }
    public long getrem_time(){
        return this.time_rem;
    }
    public void setTime_rem (long rem_time){
        this.time_rem=rem_time;

    }

    public String getMed_name(){

        return this.Med_name;

    }

    public void setMed_name(String name){

        this.Med_name=name;
    }
    public int getMed_dos(){

        return this.dosage_med;

    }
    public void setMed_dosage(int dos){

        this.dosage_med=dos;
    }


}
