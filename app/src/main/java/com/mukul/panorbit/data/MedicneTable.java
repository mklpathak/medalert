package com.mukul.panorbit.data;

/**
 * Created by Mukul on 16-06-2017.
 */

public class MedicneTable {

    private int ID ;
    private  String Med_name ;
    int dosage_med;
    int no_of_times_MT2;
    private  String med_expiry ;
    private  String course_from  ;
    private  String course_to ;
    MedicneTable(){

    }
 MedicneTable  (int id,String Medname,String no_times_MT,String Dosage_med , String expiry_med,String coursefrom_med, String courseto_med){
     this.ID=id;
     this.Med_name = Medname;
     this.no_of_times_MT2=Integer.parseInt(no_times_MT);
        this.dosage_med=Integer.parseInt( Dosage_med);
        this.med_expiry = expiry_med;
        this.course_from=coursefrom_med;
        this.course_to= courseto_med;

    }
    public MedicneTable(String Medname, String no_times_MT, String Dosage_med, String expiry_med, String coursefrom_med, String courseto_med){

        this.Med_name = Medname;
        this.no_of_times_MT2=Integer.parseInt(no_times_MT);
        this.dosage_med=Integer.parseInt(Dosage_med);
        this.med_expiry = expiry_med;
        this.course_from=coursefrom_med;
        this.course_to= courseto_med;
    }

    public int getID(){
       return this.ID;
    }
    public void setID(int _id){
        this.ID=_id;

    }
    public int getNo_of_times_MT2(){
        return this.no_of_times_MT2;
    }
    public void setNo_of_times_MT2(int _Nooftimes){
        this.no_of_times_MT2=_Nooftimes;

    }

    public String getMed_name(){

        return this.Med_name;

    }
    public int getMed_dos(){

        return this.dosage_med;

    }
    public String getMed_expiry(){

        return this.med_expiry;

    }
    public String getMed_course_from(){

        return this.course_from;

    }
    public String getMed_couse_to(){

        return this.course_to;

    }
    public void setMed_name(String name){

        this.Med_name=name;
    }
    public void setMed_dosage(int dos){

        this.dosage_med=dos;
    }
    public void setMed_expiry(String expiry){

        this.med_expiry=expiry;
    }
    public void setMed_coursefrom(String course_f){

        this.course_from=course_f;
    }
    public void setMed_course_to(String course_to){

        this.course_to=course_to;
    }


}
