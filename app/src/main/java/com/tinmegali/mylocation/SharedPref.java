package com.tinmegali.mylocation;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SG on 3/21/2018.
 */

public
class SharedPref {
    private String priviouslatitude,priviouslongitude;
    private String latlang,verypriviouslatitude,verypriviouslongitude,firstpriviouslatitude,firstpriviouslongitude;
    private String latlati;
    private String Igeo_Id,date;
    private double distance;
    Context context;
    public static final String MY_PREF="MyPrefs";
    public
    SharedPref(Context context){
        this.context=context;
    }
    public void setfirstpriviouslatitude( String firstpriviouslatitude) {
        this.firstpriviouslatitude = firstpriviouslatitude;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstpriviouslatitude", firstpriviouslatitude);
        editor.apply();
    }

    public
    String getfirstpriviouslatitude() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        firstpriviouslatitude = sharedPreferences.getString("firstpriviouslatitude", null);
        if (firstpriviouslatitude != null) {
            return firstpriviouslatitude;
        }
        return null;
    }
    public void setfirstpriviouslongitude( String firstpriviouslongitude) {
        this.firstpriviouslongitude = firstpriviouslongitude;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstpriviouslongitude", firstpriviouslongitude);
        editor.apply();
    }

    public
    String getfirstpriviouslongitude() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        firstpriviouslongitude = sharedPreferences.getString("firstpriviouslongitude", null);
        if (firstpriviouslongitude != null) {
            return firstpriviouslongitude;
        }
        return null;
    }
    public void setverypriviouslatitude( String verypriviouslatitude) {
        this.priviouslongitude = verypriviouslatitude;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("verypriviouslatitude", verypriviouslatitude);
        editor.apply();
    }

    public
    String getverypriviouslatitude() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        verypriviouslatitude = sharedPreferences.getString("verypriviouslatitude", null);
        if (verypriviouslatitude != null) {
            return verypriviouslatitude;
        }
        return null;
    }
    public void setverypriviouslongitude( String verypriviouslongitude) {
        this.verypriviouslongitude = verypriviouslongitude;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("verypriviouslongitude", verypriviouslongitude);
        editor.apply();
    }

    public
    String getverypriviouslongitude() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        verypriviouslongitude = sharedPreferences.getString("verypriviouslongitude", null);
        if (verypriviouslongitude != null) {
            return verypriviouslongitude;
        }
        return null;
    }
    public void setpriviouslongitude( String priviouslongitude) {
        this.priviouslongitude = priviouslongitude;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("priviouslongitude", priviouslongitude);
        editor.apply();
    }

    public
    String getpriviouslongitude() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        priviouslongitude = sharedPreferences.getString("priviouslongitude", null);
        if (priviouslongitude != null) {
            return priviouslongitude;
        }
        return null;
    }
    public void setpriviouslatitude( String priviouslatitude) {
        this.priviouslatitude = priviouslatitude;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("priviouslatitude", priviouslatitude);
        editor.apply();
    }

    public
    String getpriviouslatitude() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        priviouslatitude = sharedPreferences.getString("priviouslatitude", null);
        if (priviouslatitude != null) {
            return priviouslatitude;
        }
        return null;
    }
    public void setlatlang( String latlang) {
        this.latlang = latlang;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("latlang", latlang);
        editor.apply();
    }

    public
    String getlatlang() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        latlang = sharedPreferences.getString("latlang", null);
        if (latlang != null) {
            return latlang;
        }
        return null;
    }
    public void setlatlati( String latlati) {
        this.latlati = latlati;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("latlati",latlati);
        editor.apply();
    }

    public
    String getlatlati() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        latlati = (sharedPreferences.getString("latlati", null));
        if ((latlati != (null))) {
            return latlati;
        }
        return null;
    }
    public void setIgeo_Id( String Igeo_Id) {
        this.Igeo_Id = Igeo_Id;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Igeo_Id", Igeo_Id);
        editor.apply();
    }

    public
    String getIgeo_Id() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        Igeo_Id = sharedPreferences.getString("Igeo_Id", null);
        if (Igeo_Id != null) {
            return Igeo_Id;
        }
        return null;
    }
    public void setDistance( double distance) {
        this.distance = distance;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("distance", String.valueOf(distance));
        editor.apply();
    }

    public
    double getdistance() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        distance = Double.parseDouble(sharedPreferences.getString("distance", null));
        if (distance != 0) {
            return distance;
        }
        return 0;
    }
    public void setdate1( String date) {
        this.date = date;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("date", date);
        editor.apply();
    }

    public
    String getdate1() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        date = sharedPreferences.getString("date", null);
        if (date != null) {
            return date;
        }
        return null;
    }


}

