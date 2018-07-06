package com.tinmegali.mylocation;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public
class Admin_Main extends AppCompatActivity {
TextView yearsession;
String currentYear;
ImageView addlocation,report,personreport,area,profile,allocatelocation;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin__main);
        yearsession=(TextView)findViewById(R.id.yearsession);
        addlocation=(ImageView)findViewById(R.id.addLocation);
        report=(ImageView)findViewById(R.id.Report);
        personreport=(ImageView)findViewById(R.id.personreport);
        area=(ImageView)findViewById(R.id.TotalArea);
        profile=(ImageView)findViewById(R.id.profile);
        allocatelocation=(ImageView)findViewById(R.id.allocation);
        Calendar instance = Calendar.getInstance();
        currentYear = String.valueOf(instance.get(Calendar.YEAR));
        int nextYear=(Integer.parseInt(currentYear)+1);
        yearsession.setText("March"+currentYear+"-"+"March"+nextYear);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent i = new Intent(Admin_Main.this,Full_Report.class);
                startActivity(i);
            }
        });

        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent i = new Intent(Admin_Main.this,Full_Report.class);
                startActivity(i);
            }
        });

    }
}
