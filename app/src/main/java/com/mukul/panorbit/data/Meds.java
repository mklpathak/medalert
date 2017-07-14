package com.mukul.panorbit.data;

/**
 * Created by Mukul on 14-06-2017.
 */

public class Meds {

    public String  med_name ;
    public String  med_dos_amt;
    public String  med_times;

    public Meds(String name,String dos, String times){

        this.med_name = name;
        this.med_dos_amt = dos;
        this.med_times = times;
    }

   String getMedname(){

        return med_name;

    }
    String getDos(){
        return med_dos_amt;
    }
    String getTimes(){

        return med_times;
    }
}
