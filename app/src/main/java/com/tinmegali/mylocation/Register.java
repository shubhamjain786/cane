package com.tinmegali.mylocation;

import android.widget.Button;
import android.widget.EditText;

public class Register {
    SharedPref sharedPref=new SharedPref(this);
    Login login = new Login();
    EditText name, email_id, pass, repass,phone,imei;
    public static  String URL_NAHAR_REGISTER="http://122.15.29.230:8080/CANESERVEY/registration.php";
    String name_s, email_id_s, pass_s, repass_s,phone_s;
    Button register,delete;
    private int IMEI_PERMISSION = 1;
    String password, confirmpassword,stringemail,IMEI;
    String current_date,current_time;
    private String email="^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"

            +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

            +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."

            +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

            +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"

            +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
    AttDBAdapter attDBAdapter;

    private class Login {
    }
}
