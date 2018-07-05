package com.tinmegali.mylocation;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


public class Login extends ActionBarActivity {
    public static  String URL_NAHAR_GetLoginData="http://122.15.29.230:8080/CANESERVEY/fetch_loginData.php";
    private static EditText username;
    SharedPref sharedPref = new SharedPref(this);
    private static EditText password;
    private static Button login_btn;
    AttDBAdapter attDBAdapter;
    String result;
    String Id,imei_no,name,email,Password,phone,IsActive,User_Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton();
        attDBAdapter = new AttDBAdapter(this);
        SQLiteDatabase sqLiteDatabase =  attDBAdapter.attDBHelper.getWritableDatabase();
        try {
            volley1();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void LoginButton() {
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.Password);
        login_btn = (Button) findViewById(R.id.login);

        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (username.getText().toString().equals("user") &&
                                password.getText().toString().equals("pass")) {
                            Toast.makeText(Login.this, "User and Password is correct",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("com.example.programmingknowledge.simpleloginapp.User");
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "User and Password is not correct",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                }

        );
    }

    private void Getdata() throws JSONException {

        volley1();
    }

    private void volley1() throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_NAHAR_GetLoginData,
                new Response.Listener<String>() {
                    @Override
                    public
                    void onResponse(String response) {
                        Log.d("v1", "onResponse: " + response);

                        try {

  JSONObject jobjet = new JSONObject(response);
  result = String.valueOf(jobjet.get("result"));
                            JSONArray cast = new JSONArray(result);
                            Log.d("v1", "onResponse: " + result);
                            for (int i = 0; i < cast.length(); i++) {
                                JSONObject actor = cast.getJSONObject(i);

                                attDBAdapter.InsertLocalUserMaster(Integer.parseInt(String.valueOf(actor.get("Id"))),String.valueOf(actor.get("imei_no")),String.valueOf(actor.get("name")), String.valueOf(actor.get("email")),
                                        String.valueOf(actor.get("password")), String.valueOf(actor.get("phone")), String.valueOf(actor.get("IsActive")),
                                        String.valueOf(actor.get("User_Type")));
                                Log.d("VINAY", "onCreate: "+String.valueOf(actor.get("name")));
                                String str= attDBAdapter.getLogindata(sharedPref.getImei_No());
                                Log.d("v5", "onCreate: "+str);
                                String strparts[]=str.split(",");
                                if (!strparts[0].equals("")){
                                    for(int j=0;j<strparts.length;j++){
                                        if(j == 0){
                                            Id=strparts[0].toString().trim();
                                            imei_no = strparts[1].toString().trim();
                                            name=strparts[2].toString();
                                            email = strparts[3].toString();
                                            Password=strparts[4].toString();
                                            phone = strparts[5].toString();
                                            IsActive = strparts[6].toString();
                                            User_Type = strparts[7].toString();

                                        }
                                    }



                                }else{

                                }

                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public
            void onErrorResponse(VolleyError error) {

                Log.d("shubham", "onResponse3: "+error);
            }
        })



        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("imei_no",sharedPref.getImei_No());


                return  params;

            }


        };



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}


