package com.tinmegali.mylocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by SG on 3/20/2018.
 */

public class AttDBAdapter {
    Context context1;
    AttDBHelper attDBHelper;

    public
    AttDBAdapter(Context context){
        attDBHelper = new AttDBHelper(context);
        attDBHelper.getWritableDatabase();
    }









    public int DeleteuSERMaster() {

        SQLiteDatabase db = attDBHelper.getWritableDatabase();
        int count = db.delete(attDBHelper.TABLE_NAME, null, null);
        return count;
    }


















    public long InsertLocalUserMaster( int Id, String imei_no, String name, String email, String Password,
                                       String phone, String IsActive,String User_Type){


        int confuser = DeleteuSERMaster();

        if (confuser != -1)
        {

            SQLiteDatabase db = attDBHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(attDBHelper.COL_1,Id);
            contentValues.put(attDBHelper.COL_2,imei_no);
            contentValues.put(attDBHelper.COL_3,User_Type);
            contentValues.put(attDBHelper.COL_4,name);
            contentValues.put(attDBHelper.COL_5,Password);
            contentValues.put(attDBHelper.COL_6,email);
            contentValues.put(attDBHelper.COL_7,phone);
            contentValues.put(attDBHelper.COL_8, IsActive.toString().toUpperCase());



            long id = db.insert(attDBHelper.TABLE_NAME,null,contentValues);
            return id;
        }
        else{
            return confuser;
        }

    }

    public String getLogindata(String imei_no){
        SQLiteDatabase db = attDBHelper.getWritableDatabase();
        String[] columns={attDBHelper.COL_1,attDBHelper.COL_2,attDBHelper.COL_3,attDBHelper.COL_4,attDBHelper.COL_5,attDBHelper.COL_6,attDBHelper.COL_7,attDBHelper.COL_8};
        StringBuffer buffer = new StringBuffer();
        Cursor cursor = db.query(attDBHelper.TABLE_NAME,columns,attDBHelper.COL_2+" = '"+imei_no+"'",null,null,null,null);
        while(cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(attDBHelper.COL_1);
            int index2 = cursor.getColumnIndex(attDBHelper.COL_2);
            int index3 = cursor.getColumnIndex(attDBHelper.COL_3);
            int index4 = cursor.getColumnIndex(attDBHelper.COL_4);
            int index5 = cursor.getColumnIndex(attDBHelper.COL_5);
            int index6 = cursor.getColumnIndex(attDBHelper.COL_6);
            int index7 = cursor.getColumnIndex(attDBHelper.COL_7);
            int index8 = cursor.getColumnIndex(attDBHelper.COL_8);

            int id = cursor.getInt(index1);
            String Imei_no = cursor.getString(index2);
            String User_Type = cursor.getString(index3);
            String name = cursor.getString(index4);
            String Password = cursor.getString(index5);
            String email = cursor.getString(index6);
            String phone = cursor.getString(index7);
            String IsActive= cursor.getString(index8);


            buffer.append(id+","+Imei_no+","+User_Type+","+name+","+Password+","+email+","+phone+","+IsActive+"");
        }
        return buffer.toString();
    }

   /* public long InsertLocalMonitor(int Intid, String Imei_No,double current_latitude,double current_longitude, String Date,String Time, String Isstatus,
                                   String IGeoId,String Issync, String Gpsstatus, String locationname, String Spoofing){


        *//*int deltag = DeleteGeoMaster();

        if (deltag != -1)
        {*//*

        SQLiteDatabase db = attDBHelper.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        //contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_1 ,Intid);
*//*
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_2 ,Imei_No);
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_3 ,current_latitude);
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_4 ,current_longitude);
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_5 ,Date);
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_6 ,Time);
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_7 ,Isstatus);
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_8 ,IGeoId);
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_9 , Issync.toString().toUpperCase());  //Integer.parseInt(phone.toString()));
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_10 ,Gpsstatus);
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_11 ,locationname);
        contentValues1.put(attDBHelper.TBL_USER_LOGHISTORY_COL_12 ,Spoofing);
        long id = db.insert(attDBHelper.TBL_USER_LOGHISTORY_TABLE_NAME,null,contentValues1);
        return id;
*//*
        *//*}
        else{
            return deltag;
        }*//*

    }

*/

    public long InsertLocalGeoMaster(int geomid, String geolatitude ,String geolongitude,double distance,int pro_id,String locationdes,String entry_date, String entry_time,
                                     String isactive){
/*

          int deltag = DeleteGeoMaster();

        if (deltag != -1)
        {
*/

        SQLiteDatabase db = attDBHelper.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(attDBHelper.GEO_MASTER_COL_1,geomid);
        contentValues1.put(attDBHelper.GEO_MASTER_COL_2,geolatitude);
        contentValues1.put(attDBHelper.GEO_MASTER_COL_3,geolongitude);

        long id = db.insert(attDBHelper.GEO_MASTER_TABLE_NAME,null,contentValues1);
        return id;


/*
}
        else{
            return deltag;
        }
*/


    }

    public long InsertLocalUserGeoInfo(int usergeoid, String Imei_no , String Igeoid,String proid ,String entry_date, String entry_time,
                                       String isactive){

      /*  int deltag = DeleteUserGeoInfo();

        if (deltag != -1)
        {*/


        SQLiteDatabase db = attDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(attDBHelper.TBL_USERGEO_INFO_COL_1,usergeoid);
        contentValues.put(attDBHelper.TBL_USERGEO_INFO_COL_2,Imei_no);
        contentValues.put(attDBHelper.TBL_USERGEO_INFO_COL_3,proid);
        contentValues.put(attDBHelper.TBL_USERGEO_INFO_COL_4,Igeoid);
        contentValues.put(attDBHelper.TBL_USERGEO_INFO_COL_5,entry_date);



        long id = db.insert(attDBHelper.TBL_USERGEO_INFO_TABLE_NAME,null,contentValues);
        return id;
       /* }
        else{
            return deltag;
        }*/

    }

    public long InsertLocalUserAttendance(String Imei_no , String Igeoid, String intime, String inlatitude, String inlongitude, String indate,
                                          String outtime, String outlatitude, String outlongitude, String outdate){
        SQLiteDatabase db = attDBHelper.getWritableDatabase();

        Cursor mCount= db.rawQuery("select count(*) from TBL_USER_ATTENDANCE where InDate='" + indate + "'", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();



        if(count > 0){

            return -3;

        }
        else{
            ContentValues contentValues = new ContentValues();
            contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_2,indate);
            contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_3,Imei_no);
            contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_4,Igeoid);
            contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_5,String.valueOf(inlatitude));
            contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_6,String.valueOf(inlongitude));
            contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_7,intime);
            contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_8,outtime);
            contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_9,outlatitude);
            contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_10,outlongitude);
            contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_11,outdate);
            //Integer.parseInt(phone.toString()));


            long id = db.insert(attDBHelper.TBL_USER_ATTENDANCE_TABLE_NAME,null,contentValues);
            return id;

        }


    }

    public long InsertLocalUserAttendancestart(String Imei_no , String Igeoid, String intime, String inlatitude, String inlongitude, String indate,
                                               String outtime, String outlatitude, String outlongitude, String outdate){
        SQLiteDatabase db = attDBHelper.getWritableDatabase();

        Cursor mCount= db.rawQuery("select count(*) from TBL_USER_ATTENDANCE where InDate='" + indate + "'", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();




        ContentValues contentValues = new ContentValues();
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_2,indate);
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_3,Imei_no);
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_4,Igeoid);
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_5,String.valueOf(inlatitude));
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_6,String.valueOf(inlongitude));
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_7,intime);
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_8,outtime);
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_9,outlatitude);
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_10,outlongitude);
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_11,outdate);
        contentValues.put(attDBHelper.TBL_USER_ATTENDANCE_COL_12,"Y");
        //Integer.parseInt(phone.toString()));


        long id = db.insert(attDBHelper.TBL_USER_ATTENDANCE_TABLE_NAME,null,contentValues);
        return id;




    }


    static class AttDBHelper extends SQLiteOpenHelper{



        private static final String DATABASE_NAME = "CANESERVERY";

        private static final String TABLE_NAME = "TBL_USER_MASTER";
        private static final String COL_1 = "Id";
        private static final String COL_2 = "Imei_No";
        private static final String COL_3 = "User_Type";
        private static final String COL_4 = "User_Name";
        private static final String COL_5 = "Password";
        private static final String COL_6 = "Email_Id";
        private static final String COL_7 = "Phone_No";
        private static final String COL_8 = "IsActive";


        private static final String Create_Table="create table " + TABLE_NAME +" ("+COL_1+" INTEGER NOT NULL PRIMARY KEY,"+COL_2+" VARCHAR(20) NOT NULL UNIQUE,"+COL_3+" INTEGER NOT NULL,"
                +COL_4+" VARCHAR(1) NOT NULL,"+COL_5+" VARCHAR(15) NOT NULL,"+COL_6+" VARCHAR(15) NOT NULL,"+COL_7+" VARCHAR(70),"
               +COL_8+" VARCHAR(1));";
        private static final String Drop_Table="DROP TABLE IF EXISTS " + TABLE_NAME + ";";


        private static final String GEO_MASTER_TABLE_NAME = "TBL_STATE_MASTER";
        private static final String GEO_MASTER_COL_1 = "Id";
        private static final String GEO_MASTER_COL_2 = "State";
        private static final String GEO_MASTER_COL_3 = "IsActive";



        private static final String Create_Table1="create table " + GEO_MASTER_TABLE_NAME +" ("+GEO_MASTER_COL_1+" INTEGER PRIMARY KEY NOT NULL,"+GEO_MASTER_COL_2+" VARCHAR(100) NOT NULL,"
                +GEO_MASTER_COL_3+" REAL NOT NULL);";

        private static final String Drop_Table1="DROP TABLE IF EXISTS " + GEO_MASTER_TABLE_NAME + ";";


        private static final String TBL_USERGEO_INFO_TABLE_NAME = "TBL_CITY_MASTER";
        private static final String TBL_USERGEO_INFO_COL_1 = "Id";
        private static final String TBL_USERGEO_INFO_COL_2 = "Pro_Id";
        private static final String TBL_USERGEO_INFO_COL_3 = "city";
        private static final String TBL_USERGEO_INFO_COL_4 = "Imei_No";
        private static final String TBL_USERGEO_INFO_COL_5 = "IsActive";

        private static final String Create_Table2="create table " + TBL_USERGEO_INFO_TABLE_NAME +" ("+TBL_USERGEO_INFO_COL_1+" INTEGER NOT NULL PRIMARY KEY ,"+TBL_USERGEO_INFO_COL_2+" VARCHAR(20) NOT NULL,"
                +TBL_USERGEO_INFO_COL_3+" VARCHAR(100) NOT NULL,"+TBL_USERGEO_INFO_COL_4+" INTEGER NOT NULL,"+TBL_USERGEO_INFO_COL_5+" NUMERIC NOT NULL);";
        private static final String Drop_Table2="DROP TABLE IF EXISTS " + TBL_USERGEO_INFO_TABLE_NAME + ";";

        private static final String TBL_USER_ATTENDANCE_TABLE_NAME = "TBL_USER_INFO";
        private static final String TBL_USER_ATTENDANCE_COL_1 = "Id";
        private static final String TBL_USER_ATTENDANCE_COL_2 = "State_Id";
        private static final String TBL_USER_ATTENDANCE_COL_3 = "Imei_No";
        private static final String TBL_USER_ATTENDANCE_COL_4 = "City_Id";
        private static final String TBL_USER_ATTENDANCE_COL_5 = "InLatitude";
        private static final String TBL_USER_ATTENDANCE_COL_6 = "InLongitude";
        private static final String TBL_USER_ATTENDANCE_COL_7 = "InTime";
        private static final String TBL_USER_ATTENDANCE_COL_8 = "OutTime";
        private static final String TBL_USER_ATTENDANCE_COL_9 = "OutLatitude";
        private static final String TBL_USER_ATTENDANCE_COL_10 = "OutLongitude";
        private static final String TBL_USER_ATTENDANCE_COL_11 = "OutDate";
        private static final String TBL_USER_ATTENDANCE_COL_12 = "IsActive";

        private static final String Create_Table3="create table " + TBL_USER_ATTENDANCE_TABLE_NAME +" ("+TBL_USER_ATTENDANCE_COL_1+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+TBL_USER_ATTENDANCE_COL_2+" NUMERIC NOT NULL ,"
                +TBL_USER_ATTENDANCE_COL_3+" VARCHAR(20) NOT NULL,"+TBL_USER_ATTENDANCE_COL_4+" INTEGER NOT NULL,"+TBL_USER_ATTENDANCE_COL_5+" REAL NOT NULL,"+TBL_USER_ATTENDANCE_COL_6+" REAL NOT NULL,"+TBL_USER_ATTENDANCE_COL_7+" NUMERIC NOT NULL,"+TBL_USER_ATTENDANCE_COL_8+" NUMERIC,"+TBL_USER_ATTENDANCE_COL_9+" REAL ,"+TBL_USER_ATTENDANCE_COL_10+" REAL,"+TBL_USER_ATTENDANCE_COL_11+" NUMERIC,"+TBL_USER_ATTENDANCE_COL_12+" VARCHAR(1));";
        private static final String Drop_Table3="DROP TABLE IF EXISTS " + TBL_USER_ATTENDANCE_TABLE_NAME + ";";


      /*  private static final String TBL_USER_LOGHISTORY_TABLE_NAME = "TBL_USER_LOGHISTORY";
        private static final String TBL_USER_LOGHISTORY_COL_1 = "IntId";
        private static final String TBL_USER_LOGHISTORY_COL_2 = "Imei_No";
        private static final String TBL_USER_LOGHISTORY_COL_3 = "current_latitude";
        private static final String TBL_USER_LOGHISTORY_COL_4 = "current_longitude";
        private static final String TBL_USER_LOGHISTORY_COL_5 = "Date";
        private static final String TBL_USER_LOGHISTORY_COL_6 = "Time";
        private static final String TBL_USER_LOGHISTORY_COL_7 = "Isstatus";
        private static final String TBL_USER_LOGHISTORY_COL_8 = "IGeoId";
        private static final String TBL_USER_LOGHISTORY_COL_9 = "IsSync";
        private static final String TBL_USER_LOGHISTORY_COL_10 = "GpsStatus";
        private static final String TBL_USER_LOGHISTORY_COL_11 = "LocationName";
        private static final String TBL_USER_LOGHISTORY_COL_12 = "Spoofing";


        private static final String Create_Table4="create table " + TBL_USER_LOGHISTORY_TABLE_NAME +" ("+TBL_USER_LOGHISTORY_COL_1+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+TBL_USER_LOGHISTORY_COL_2+" VARCHAR(20) NOT NULL,"
                +TBL_USER_LOGHISTORY_COL_3+" REAL NOT NULL,"+TBL_USER_LOGHISTORY_COL_4+" REAL NOT NULL," +TBL_USER_LOGHISTORY_COL_5+" NUMERIC NOT NULL,"+TBL_USER_LOGHISTORY_COL_6+" NUMERIC NOT NULL,"+TBL_USER_LOGHISTORY_COL_7+" VARCHAR(3),"+TBL_USER_LOGHISTORY_COL_8+" INTEGER NOT NULL,"+TBL_USER_LOGHISTORY_COL_9+" VARCHAR(1),"+TBL_USER_LOGHISTORY_COL_10+" VARCHAR(3),"+TBL_USER_LOGHISTORY_COL_11+" VARCHAR(200),"+TBL_USER_LOGHISTORY_COL_12+" VARCHAR(10));";
        private static final String
                Drop_Table4="DROP TABLE IF EXISTS " + TBL_USER_LOGHISTORY_TABLE_NAME + ";";*/


        public Context context;
        private static final int DATABASE_VERSION = 1;

        public
        AttDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context=context;

        }

        @Override
        public
        void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(Create_Table);
                db.execSQL(Create_Table1);
                db.execSQL(Create_Table2);
                db.execSQL(Create_Table3);
               // db.execSQL(Create_Table4);

            }catch (SQLException e){

                Toast.makeText(context,""+e,Toast.LENGTH_LONG).show();
                //com.example.sg.empattedance.Message.message(context,""+e);
            }
        }

        @Override
        public
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try{
                db.execSQL(Drop_Table);
                db.execSQL(Create_Table);

                db.execSQL(Drop_Table1);
                db.execSQL(Create_Table1);

                db.execSQL(Drop_Table2);
                db.execSQL(Create_Table2);

                db.execSQL(Drop_Table3);
                db.execSQL(Create_Table3);

               // db.execSQL(Drop_Table4);
               // db.execSQL(Create_Table4);


            }catch (SQLException e){
                Toast.makeText(context,""+e,Toast.LENGTH_LONG).show();
               // com.example.sg.empattedance.Message.message(context,""+e);
            }
        }


    }

}
