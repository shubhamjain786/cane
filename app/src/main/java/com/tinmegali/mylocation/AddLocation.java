package com.tinmegali.mylocation;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class AddLocation extends AppCompatActivity {

    public static  String URL_NAHAR_ADDLOCATION="http://122.15.29.230:8080/CANESERVEY/add_location.php";
    private Button Submit;
    EditText State,city,Pincode;
    String State_Id,City,PIn_Code;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    String State1,city1,Pincode1;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location);

        Submit = (Button)findViewById(R.id.Submit);
        State = (EditText)findViewById(R.id.State);
        city = (EditText)findViewById(R.id.city);
        Pincode = (EditText)findViewById(R.id.Pincode);






        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                State1 = State.getText().toString();
                city1 = city.getText().toString();
                Pincode1 = Pincode.getText().toString();
                if (State1.equals("") || city1.equals("") || Pincode1.equals("") ||State1.equals(null) || city1.equals(null) || Pincode1.equals(null))
                {
                    State.setError("All fields are mandatory");
                    Pincode.setError("All fields are mandatory");
                    city.setError("All fields are mandatory  ");
                }else {

                    try {
                        sendAndRequestResponse();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });




    }

    private void sendAndRequestResponse()
            throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NAHAR_ADDLOCATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("sunita", "onResponse2: " + response);
                        try {
                            JSONObject jobjet = new JSONObject(response);
                            String Status = String.valueOf(jobjet.get("error"));
                            if (Status.equals("1")) {
                                Toast.makeText(AddLocation.this, "Location Added Successfully", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(AddLocation.this, Admin_Main.class);
                                startActivity(i);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddLocation.this);
                                builder.setCancelable(false);
                                builder.setTitle("Error");
                                builder.setMessage("Data already exist");
                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Anil", "onResponse: " + e);
                            // Toast.makeText(Register.this, (CharSequence) e,Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("shubham", "onResponse3: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("State",State1);
                params.put("City",city1);
                params.put("PinCode", Pincode1);


                return params;

            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

