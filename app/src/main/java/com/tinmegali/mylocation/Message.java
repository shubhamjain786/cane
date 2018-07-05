package com.tinmegali.mylocation;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SG on 2/22/2018.
 */

public class Message {
    String mail_id;
    public static void message(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

}
