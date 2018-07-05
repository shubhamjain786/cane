package com.tinmegali.mylocation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    SharedPref sharedPref=new SharedPref(this);
    EditText imei_no,Name, email_id, pass, repass,Phone;

    public static  String URL_NAHAR_REGISTER="http://122.15.29.230:8080/CANESERVEY/registration.php";

    String name_s, email_id_s, pass_s, repass_s,phone_s;
    Button register,delete;
    private int IMEI_PERMISSION = 1;
    String password, confirmpassword,stringemail,IMEI;
    String current_date,current_time;
    private static final String user_type = "U";

    int phone1;
    String str = null;
    String username,isapprove,isactive;
    private String email="^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"

            +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

            +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."

            +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

            +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"

            +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

    AttDBAdapter attDBAdapter;
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        attDBAdapter = new AttDBAdapter(this);
        IMEI = sharedPref.getImei_No();
        Log.d("imeiji", "onCreate: "+IMEI);

        if(!isConnected(Register.this)) {

            buildDialog(Register.this).show();
        }
        else{
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(Calendar.getInstance().getTimeZone().getID()));

        //DateFormat df = new SimpleDateFormat("HH:mm:ss");
        //df.setTimeZone(android.icu.util.TimeZone.getTimeZone(Calendar.getInstance().getTimeZone().getID()));
        current_time = df.format(today);

        Date today1 = new Date();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        df1.setTimeZone(TimeZone.getTimeZone(Calendar.getInstance().getTimeZone().getID()));
        //DateFormat df1 = new android.icu.text.SimpleDateFormat("dd-MM-yyyy");
        //DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        //df1.setTimeZone(android.icu.util.TimeZone.getTimeZone(Calendar.getInstance().getTimeZone().getID()));
        current_date = df1.format(today1);
        Log.d("Date", "getParams: " + current_date);


        Name = (EditText) findViewById(R.id.Name);

        email_id = (EditText) findViewById(R.id.email_id);
        imei_no = (EditText) findViewById(R.id.imei_no);
        imei_no.setEnabled(false);
        imei_no.setText(IMEI);
        pass = (EditText) findViewById(R.id.pass);
        repass = (EditText) findViewById(R.id.repass);
        Phone = (EditText) findViewById(R.id.Phone);

        register = (Button) findViewById(R.id.register);
        //   delete=(Button)findViewById(R.id.delete1);

         /*delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public
             void onClick(View v) {
                 Delete_User_data();
             }
         });*/

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this,"Register",Toast.LENGTH_LONG).show();

                name_s = Name.getText().toString().toUpperCase().trim();
                pass_s = pass.getText().toString().trim();
                email_id_s = email_id.getText().toString().trim();
                phone_s = Phone.getText().toString().trim();

                //phone.getText().toString().trim();

                stringemail = email_id.getText().toString().trim();
                password = pass.getText().toString().trim();
                confirmpassword = repass.getText().toString().trim();
                //Log.d("password", "onClick: "+password+"123"+confirmpassword);
                phone1 = Phone.getText().toString().trim().length();
                Matcher matcherObj = Pattern.compile(email).matcher(stringemail);
                if (Name.getText().toString().trim().equalsIgnoreCase("") && email_id.getText().toString().trim().equalsIgnoreCase("")
                        && pass.getText().toString().trim().equalsIgnoreCase("") && repass.getText().toString().trim().equalsIgnoreCase("")
                        && Phone.getText().toString().trim().equalsIgnoreCase("")) {
                    Name.setError("Mandotory Field");
                    email_id.setError("Mandotory Field");
                    pass.setError("Mandotory Field");
                    repass.setError("Mandotory Field");
                    Phone.setError("Mandotory Field");
                } else if (!password.equals(confirmpassword)) {
                    repass.setError("Password Not Match");
                } else if (phone1 != 10) {
                    Phone.setError("Mobile No. Not Valid");
                } else if (!matcherObj.matches()) {
                    email_id.setError("Email not Valid");

                } else {
                    try {
                        Senddata();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });


    }
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ActivityCompat.requestPermissions(Register.this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET},IMEI_PERMISSION);
                finish();
            }
        });

        return builder;
    }

    private void Senddata() throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_NAHAR_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public
                    void onResponse(String response) {
                        Log.d("Anil", "onResponse2: "+response);
                        try {
                            JSONObject jobjet= new JSONObject(response);
                            String Status= String.valueOf(jobjet.get("error"));
                            if (Status.equals("1")){
                                Message.message(Register.this,"You Successfully registered");
                                Message.message(Register.this,"You have to wait for the admin Verification Please Wait....");
                                finish();
                                moveTaskToBack(true);

                            }
                            else if (Status.equals("2")){
                                Message.message(Register.this,"Your Device Date not Matched");
                                int res = attDBAdapter.DeleteUserLoginData(IMEI);
                            }
                            else if (Status.equals("3")){
                                Name.setError(" User_name Already Taken By someone");
                            }
                            else{
                                Message.message(Register.this,"You Already register");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Anil", "onResponse: "+e);
                            // Toast.makeText(Register.this, (CharSequence) e,Toast.LENGTH_LONG).show();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public
            void onErrorResponse(VolleyError error) {
                Log.d("shubham", "onResponse3: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("imei_no",sharedPref.getImei_No());
                params.put("name",name_s.toString().toUpperCase());
                params.put("email",stringemail);
                params.put("password",pass_s.toString());
                params.put("phone",phone_s.toString());


                return  params;

            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

}
