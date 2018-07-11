package com.tinmegali.mylocation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfile1 extends AppCompatActivity implements View.OnClickListener {



    public static  String URL_NAHAR_USER_DATA="http://122.15.29.230:8080/SyncClientToServer/userdata.php";
    TextView status, name, imei_no, usertype, email,phone;
    AttDBAdapter attDBAdapter;
    String user_name, user_phone,user_email, user_type, user_imeino;

    SharedPref sharedPref=new SharedPref(this);

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile1);
        attDBAdapter = new AttDBAdapter(this);
        SQLiteDatabase sqLiteDatabase =  attDBAdapter.attDBHelper.getWritableDatabase();
        name=(TextView)findViewById(R.id.Name);
        imei_no=(TextView)findViewById(R.id.imei_no);
        usertype=(TextView)findViewById(R.id.usertype);
        email=(TextView)findViewById(R.id.email);
        phone=(TextView)findViewById(R.id.phonenumber);

        status=(TextView)findViewById(R.id.status);

        String str=attDBAdapter.getLogindata(sharedPref.getImei_No());
        Log.d("v5", "onCreate: "+str);
        String strparts[]=str.split(",");
        if (!strparts[0].equals("")){
            for(int j=0;j<strparts.length;j++){
                if(j == 0){
                    //server get
                    user_name = strparts[1].toString().trim();
                    user_type = strparts[3].toString();
                    user_email=strparts[4].toString();
                    user_imeino = strparts[7].toString();
                    user_phone=strparts[8].toString();

                    name.setText(user_name);
                    usertype.setText(user_type);
                    imei_no.setText(user_imeino);
                    phone.setText(user_phone);
                    email.setText(user_email);
                }
            }
        }
    }

    private void getdata() throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_NAHAR_USER_DATA,
                new Response.Listener<String>() {
                    @Override
                    public
                    void onResponse(String response) {
                        Log.d("adminpanel", "onResponse2: "+response);
                        try {
                            JSONObject jobjet= new JSONObject(response);
                            String result=String.valueOf(jobjet.get("result"));
                            JSONArray cast = new JSONArray(result);
                            for (int i =0; i<cast.length();i++)
                            {
                                JSONObject actor = cast.getJSONObject(i);
                                name.setText(actor.getString("User_Name"));
                                imei_no.setText(actor.getString("Imei_No"));
                                email.setText(actor.getString("Email_Id"));
                                usertype.setText(actor.getString("User_Type"));
                                phone.setText(actor.getString("Phone_No"));

                            }

                        } catch (JSONException e) {
                            status.setVisibility(View.VISIBLE);
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public
            void onErrorResponse(VolleyError error) {
                Log.d("lala", "onResponse3: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Imei_No",sharedPref.getImei_No());
                return  params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are You Sure To Exit?");
        builder.setMessage("Press Ok to Exit ");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public
            void onClick(DialogInterface dialog, int which) {
                finish();
                //finish();
                //moveTaskToBack(true);

                UserProfile1.super.onBackPressed();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public
            void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                UserProfile1.super.onBackPressed();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
