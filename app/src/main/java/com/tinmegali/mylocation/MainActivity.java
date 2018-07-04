package com.tinmegali.mylocation;
//Created By Hamza on June 2
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

//Created By Hamza on 29 May
public class MainActivity extends AppCompatActivity
        implements
            GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            LocationListener,
            OnMapReadyCallback,
            GoogleMap.OnMapClickListener,
            GoogleMap.OnMarkerClickListener,
            ResultCallback<Status> {
    SharedPref sharedPref = new SharedPref(this);
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<String> location1;
    TextView getresult;
    Bitmap bitmap;
    LinearLayout linear;
    int position_count;
    int i=3;
    int j=0;
    int posiotn_count_check=0;
    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    MarkerOptions markerOptions;
    private EditText lat, lon;
    Button getcoordinate,getcoordinate2;
    AttDBAdapter attDBAdapter;
    double verypriviouslatitude, verypriviouslongitude, priviouslatitude, priviouslongitude;
    public static final double R1 = 6371e3;
    ArrayList<Double> lats;
    ArrayList<Double> lons;
    Button calculatearea,secondbutton;

    private MapFragment mapFragment;

    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
    // Create a Intent send by the notification
    public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent( context, MainActivity.class );
        intent.putExtra( NOTIFICATION_MSG, msg );
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        attDBAdapter = new AttDBAdapter(this);
        SQLiteDatabase sqLiteDatabase =  attDBAdapter.attDBHelper.getWritableDatabase();


        lat = (EditText) findViewById(R.id.lat);
        lon = (EditText) findViewById(R.id.lon);
        //getresult=(TextView) findViewById(R.id.GETRESULT);
        location1=new ArrayList<String>();
        lats= new ArrayList<Double>();
        lons=new ArrayList<Double>();
        getcoordinate=(Button)findViewById(R.id.getcoordinate);
        getcoordinate2=(Button)findViewById(R.id.getcoordinate2);
        calculatearea=(Button)findViewById(R.id.calculateArea);
        secondbutton=(Button)findViewById(R.id.secondbutton);
        linear = (LinearLayout)findViewById(R.id.linear);
        double pointY[]={30.507379,30.508477,30.508575,30.507308};
        double pointX[]={76.795262,76.795267,76.793937,76.793873};
        ArrayList<LatLng> points=new ArrayList<LatLng>();
        for (int i = 0 ; i < pointX.length; i++){
            points.add(new LatLng(pointX[i],pointY[i]));
        }

        LatLng latLng =new LatLng(30.507953,76.794744);

        boolean point= isPointInPolygon(latLng,points);
        Log.d("point", "onCreate: "+point);
        Toast.makeText(MainActivity.this,"fghf"+point,Toast.LENGTH_LONG).show();
        secondbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                captureMapScreen();
            }
        });

        calculatearea.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public
            void onClick(View v) {
                double Answer = area(lats,lons);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);      //find the linear layout
                // linearLayout.removeAllViews();
                //linearLayout.addView();

                TextView tv = new TextView(MainActivity.this);
                tv.setText("Area:"  + Answer);
                tv.setId( 5);
                tv.setTextSize(18);
                tv.setBackgroundColor(Color.parseColor("#98FB98"));
                linearLayout.addView(tv,i);
                Toast.makeText(MainActivity.this,""+Answer,Toast.LENGTH_LONG).show();
            }
        });
        getcoordinate2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public
            void onClick(View v) {
                if (posiotn_count_check!=position_count) {
                    j++;
                    sharedPref.setverypriviouslatitude(sharedPref.getpriviouslatitude());
                    sharedPref.setverypriviouslongitude(sharedPref.getpriviouslongitude());
                    double latitude = Double.parseDouble(sharedPref.getlatlati());
                    lats.add(latitude);
                    sharedPref.setpriviouslatitude(String.valueOf(latitude));
                    double longitude = Double.parseDouble(sharedPref.getlatlang());
                    lons.add(longitude);
                    sharedPref.setpriviouslongitude(String.valueOf(longitude));
                    LatLng[] point_new = new LatLng[3];
                    point_new[0] = new LatLng(latitude, longitude);
                    markerForGeofence(point_new[0]);
                    verypriviouslatitude = Double.parseDouble(sharedPref.getverypriviouslatitude());
                    verypriviouslongitude = Double.parseDouble(sharedPref.getverypriviouslongitude());
                    PolylineOptions line =
                            new PolylineOptions().add(new LatLng(verypriviouslatitude,
                                            verypriviouslongitude),
                                    new LatLng(Double.parseDouble(sharedPref.getpriviouslatitude()),
                                            Double.parseDouble(sharedPref.getpriviouslongitude())))
                                    .width(10).color(Color.RED);
                   // line.geodesic(Boolean.parseBoolean("tvhnv"));

                    map.addPolyline(line);
                  //  map.setContentDescription(String.valueOf(line.geodesic(Boolean.parseBoolean("tvhnv"))));

                    posiotn_count_check++;

                    double result = haversine(verypriviouslatitude, verypriviouslongitude, Double.parseDouble(sharedPref.getpriviouslatitude()),
                            Double.parseDouble(sharedPref.getpriviouslongitude()));
                   /* LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);      //find the linear layout
                    linearLayout.removeAllViews();                              //add this too
                  //  for (int i = 0; i < 5; i++) {          //looping to create 5 textviews

                        TextView textView = new TextView(MainActivity.this);              //dynamically create textview
                        textView.setLayoutParams(new LinearLayout.LayoutParams(             //select linearlayoutparam- set the width & height
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                       // textView.setGravity(Gravity.CENTER_VERTICAL);                       //set the gravity too
                        textView.setText("Textview: " + result);                                    //adding text
                        linearLayout.addView(textView);*/
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);      //find the linear layout
                   // linearLayout.removeAllViews();
                    //linearLayout.addView();

                    TextView tv = new TextView(MainActivity.this);
                    tv.setText("point "+j+" to point "+(j+1)+" : " + result);
                    tv.setId( 5);
                    tv.setBackgroundColor(Color.parseColor("#303F9F"));
                    tv.setTextColor(Color.parseColor("#FF4081"));
                    tv.setTextSize(18);
                    linearLayout.addView(tv,i);
                   location1.add(String.valueOf(result));
                  // getresult.setText((CharSequence) location1);

                    i++;

                }else{
                    j++;
                    double result = haversine(Double.parseDouble(sharedPref.getfirstpriviouslatitude()),Double.parseDouble(sharedPref.getfirstpriviouslongitude()), Double.parseDouble(sharedPref.getpriviouslatitude()),
                            Double.parseDouble(sharedPref.getpriviouslongitude()));
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);
                    TextView tv = new TextView(MainActivity.this);
                    tv.setText("point "+j+" to point 1 :" + result);
                    tv.setId( 5);
                    tv.setBackgroundColor(Color.parseColor("#303F9F"));
                    tv.setTextColor(Color.parseColor("#FF4081"));
                    tv.setTextSize(18);
                    linearLayout.addView(tv,i);
                    location1.add(String.valueOf(result));
                    i++;
                    PolylineOptions line =
                            new PolylineOptions().add(new LatLng(Double.parseDouble(sharedPref.getfirstpriviouslatitude()),Double.parseDouble(sharedPref.getfirstpriviouslongitude())
                                            ),
                                    new LatLng(Double.parseDouble(sharedPref.getpriviouslatitude()),
                                            Double.parseDouble(sharedPref.getpriviouslongitude())))
                                    .width(10).color(Color.RED);
                    // line.geodesic(Boolean.parseBoolean("tvhnv"));

                    map.addPolyline(line);
                    calculatearea.setVisibility(View.VISIBLE);
                    getcoordinate2.setVisibility(View.GONE);

                }
            }
        });
        getcoordinate.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {

                // sharedPref.setverypriviouslatitude(sharedPref.getpriviouslatitude());
                //   sharedPref.setverypriviouslongitude(sharedPref.getpriviouslongitude());
                if (lon.getText().toString().equals("")) {
                    lon.setError("Please fill this first");
                } else {
                    position_count = Integer.parseInt(lon.getText().toString());
                    double latitude = Double.parseDouble(sharedPref.getlatlati());
                    lats.add(latitude);
                    sharedPref.setfirstpriviouslatitude(String.valueOf(latitude));
                    sharedPref.setpriviouslatitude(String.valueOf(latitude));
                    double longitude = Double.parseDouble(sharedPref.getlatlang());
                    lons.add(longitude);
                    sharedPref.setfirstpriviouslongitude(String.valueOf(longitude));
                    sharedPref.setpriviouslongitude(String.valueOf(longitude));
                    LatLng[] point_new = new LatLng[3];
                    point_new[0] = new LatLng(latitude, longitude);
                    markerForGeofence(point_new[0]);
                    getcoordinate.setVisibility(View.GONE);
                    getcoordinate2.setVisibility(View.VISIBLE);
                    posiotn_count_check++;
                /*verypriviouslatitude= Double.parseDouble(sharedPref.getverypriviouslatitude());
                verypriviouslongitude=Double.parseDouble(sharedPref.getverypriviouslongitude());
                PolylineOptions line=
                        new PolylineOptions().add(new LatLng(verypriviouslatitude,
                                        verypriviouslongitude),
                                new LatLng(Double.parseDouble(sharedPref.getpriviouslatitude()),
                                        Double.parseDouble(sharedPref.getpriviouslongitude())))
                                .width(10).color(Color.RED);

                map.addPolyline(line);*/
                }
            }
        });

        initGMaps();


        createGoogleApi();
        LocationManager mlocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!enabled) {
            showDialogGPS();
        }
        startGeofence();

    }
    private void showDialogGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("GPS disabled");
        builder.setMessage("Please enable GPS");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(
                        new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void createGoogleApi() {
        Log.d(TAG, "createGoogleApi()");
        if ( googleApiClient == null ) {
            googleApiClient = new GoogleApiClient.Builder( this )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .addApi( LocationServices.API )
                    .build();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Disconnect GoogleApiClient when stopping Activity
        googleApiClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.geofence: {
                startGeofence();
                return true;
            }
            case R.id.clear: {
                clearGeofence();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private final int REQ_PERMISSION = 999;

    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        // Created by Hamza on June 2
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }

    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                REQ_PERMISSION
        );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ( requestCode ) {
            case REQ_PERMISSION: {
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    // Permission granted
                    getLastKnownLocation();

                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }


    private void permissionsDenied() {
        Log.w(TAG, "permissionsDenied()");
        // TODO close app and warn user
    }

    // Initialize GoogleMaps
    private void initGMaps(){
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        map = googleMap;


         //   markerForGeofence(point_new[i]);



       map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
      /* LatLng[] point_new = new LatLng[3];
        point_new[0] = new LatLng(30.50054962970654, 76.79775953292847);
        point_new[1] = new LatLng(30.51218799129476, 76.7931159587143);
        point_new[2] = new LatLng(30.506633006653193, 76.79790202528238);
        Log.d(TAG, "onMapClick("+latLng +")");
        for (int i = 0; i < point_new.length; i++){
*/
        //markerForGeofence(latLng);


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("satyamji", "onMarkerClickListener: " + marker.getPosition() );
        return false;
    }

    private LocationRequest locationRequest;

    private final int UPDATE_INTERVAL =  1000;
    private final int FASTEST_INTERVAL = 900;


    private void startLocationUpdates(){
        Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if ( checkPermission() )
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged ["+location+"]");
        lastLocation = location;
        writeActualLocation(location);
        String address = null;
        String knownName = null;
        String city = null;
        String state = null;
        String country = null;
        String postalCode = null;
        Geocoder gcd = new Geocoder(MainActivity.this.getBaseContext(), Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            address = addresses.get(0).getAddressLine(0);
            if (addresses.get(0).getFeatureName() != address) {
                knownName = addresses.get(0).getFeatureName();
            }
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String foundAddress =  city + ", " + state +  "- " + postalCode + "\n";
        //Toast.makeText(MainActivity.this,""+foundAddress,Toast.LENGTH_LONG).show();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected()");
        getLastKnownLocation();
        recoverGeofenceMarker();
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended()");
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed()");
    }

    // Get last known location because if we dont have gps it should atleast show us some location
    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation()");
        if ( checkPermission() ) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if ( lastLocation != null ) {
                Log.i(TAG, "LasKnown location. " +
                        "Long: " + lastLocation.getLongitude() +
                        " | Lat: " + lastLocation.getLatitude());
                writeLastLocation();
                startLocationUpdates();
            } else {
                Log.w(TAG, "No location retrieved yet");
                startLocationUpdates();
            }
        }
        else askPermission();
    }

    private void writeActualLocation(Location location) {
      //  textLat.setText( "Lat: " + location.getLatitude() );
        sharedPref.setlatlati(String.valueOf(location.getLatitude()));
        //textLong.setText( "Long: " + location.getLongitude() );
        sharedPref.setlatlang(String.valueOf(location.getLongitude()));

        markerLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private void writeLastLocation() {
        writeActualLocation(lastLocation);
    }

    private Marker locationMarker;
    private void markerLocation(LatLng latLng) {
        Log.i(TAG, "markerLocation("+latLng+")");
        String title = latLng.latitude + ", " + latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        if ( map!=null ) {
            if ( locationMarker != null )
               locationMarker.remove();
            locationMarker = map.addMarker(markerOptions);
           /* float zoom = 20f;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
            map.animateCamera(cameraUpdate);*/
        }
    }


    private Marker geoFenceMarker;
    private void markerForGeofence(LatLng latLng) {
        Log.i("shubham", "markerForGeofence("+latLng+")");
      /*  location=new ArrayList<>();
        location.add(new LatLng(latLng.latitude,latLng.longitude));
        Log.i("startgeofence", "startGeofence()"+location);*/
       // String title = latLng.latitude + ", " + latLng.longitude;

         markerOptions = new MarkerOptions()
                .position(latLng)
                 .title("point"+(j+1))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));




        if ( map!=null ) {
            // Remove last geoFenceMarker
            /*if (geoFenceMarker != null)
              locationMarker.remove();*/
            geoFenceMarker = map.addMarker(markerOptions);
           // geoFenceMarker  = map.addMarker(markerOptions);
            float zoom = 17f;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
            map.animateCamera(cameraUpdate);
        }
    }


    private void startGeofence() {
        Log.i("startgeofence", "startGeofence()"+geoFenceMarker);
        if( geoFenceMarker != null ) {

            Geofence geofence = createGeofence( geoFenceMarker.getPosition(),GEOFENCE_RADIUS );
            GeofencingRequest geofenceRequest = createGeofenceRequest( geofence );
            addGeofence( geofenceRequest );
        } else {
            Log.e(TAG, "Geofence marker is null");
        }
    }

    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private static final float GEOFENCE_RADIUS = 500.0f; // in meters


    private Geofence createGeofence( LatLng latLng, float radius ) {
        Log.d(TAG, "createGeofence");
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion( latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration( GEO_DURATION )
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();
    }

    // Create a Geofence Request
    private GeofencingRequest createGeofenceRequest( Geofence geofence ) {
        Log.d(TAG, "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER )
                .addGeofence( geofence )
                .build();
    }

    private PendingIntent geoFencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;
    private PendingIntent createGeofencePendingIntent() {
        Log.d(TAG, "createGeofencePendingIntent");
        if ( geoFencePendingIntent != null )
            return geoFencePendingIntent;

        Intent intent = new Intent( this, GeofenceTrasitionService.class);
        return PendingIntent.getService(
                this, GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT );
    }


    private void addGeofence(GeofencingRequest request) {
        Log.d(TAG, "addGeofence");
        if (checkPermission())
            LocationServices.GeofencingApi.addGeofences(
                    googleApiClient,
                    request,
                    createGeofencePendingIntent()
            ).setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.i(TAG, "onResult: " + status);
        if ( status.isSuccess() ) {
            saveGeofence();
           // drawGeofence();
        } else {
            // inform about fail
        }
    }


    private Circle geoFenceLimits;
   /* private void drawGeofence() {
        Log.d(TAG, "drawGeofence()");

       *//* if ( geoFenceLimits != null )
            geoFenceLimits.remove();*//*

        CircleOptions circleOptions = new CircleOptions()
                .center()
                .strokeColor(Color.argb(50, 70,70,70))
                .fillColor( Color.argb(80, 200, 0, 0) )
                .radius( GEOFENCE_RADIUS );
        geoFenceLimits = map.addCircle( circleOptions );
    }*/

    private final String KEY_GEOFENCE_LAT = "GEOFENCE LATITUDE";
    private final String KEY_GEOFENCE_LON = "GEOFENCE LONGITUDE";


    private void saveGeofence() {
        Log.d(TAG, "saveGeofence()");
        SharedPreferences sharedPref = getPreferences( Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putLong( KEY_GEOFENCE_LAT, Double.doubleToRawLongBits( geoFenceMarker.getPosition().latitude ));
        editor.putLong( KEY_GEOFENCE_LON, Double.doubleToRawLongBits( geoFenceMarker.getPosition().longitude ));
        editor.apply();
    }

    private void recoverGeofenceMarker() {
        Log.d(TAG, "recoverGeofenceMarker");
        SharedPreferences sharedPref = getPreferences( Context.MODE_PRIVATE );

        if ( sharedPref.contains( KEY_GEOFENCE_LAT ) && sharedPref.contains( KEY_GEOFENCE_LON )) {
            double lat = Double.longBitsToDouble( sharedPref.getLong( KEY_GEOFENCE_LAT, -1 ));
            double lon = Double.longBitsToDouble( sharedPref.getLong( KEY_GEOFENCE_LON, -1 ));
            LatLng latLng = new LatLng( lat, lon );
            //markerForGeofence(latLng);
            //drawGeofence();
        }
    }


    private void clearGeofence() {
        Log.d(TAG, "clearGeofence()");
        LocationServices.GeofencingApi.removeGeofences(
                googleApiClient,
                createGeofencePendingIntent()
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if ( status.isSuccess() ) {

                    removeGeofenceDraw();
                }
            }
        });
    }

    private void removeGeofenceDraw() {
        Log.d(TAG, "removeGeofenceDraw()");
        if ( geoFenceMarker != null)
            geoFenceMarker.remove();
        if ( geoFenceLimits != null )
            geoFenceLimits.remove();
    }
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R1 * c;
    }
    private double area(ArrayList<Double> lats,ArrayList<Double> lons)
    {
        double sum=0;
        double prevcolat=0;
        double prevaz=0;
        double colat0=0;
        double az0=0;
        for (int i=0;i<lats.size();i++)
        {
            double colat=2*Math.atan2(Math.sqrt(Math.pow(Math.sin(lats.get(i)*Math.PI/180/2), 2)+ Math.cos(lats.get(i)*Math.PI/180)*Math.pow(Math.sin(lons.get(i)*Math.PI/180/2), 2)),Math.sqrt(1-  Math.pow(Math.sin(lats.get(i)*Math.PI/180/2), 2)- Math.cos(lats.get(i)*Math.PI/180)*Math.pow(Math.sin(lons.get(i)*Math.PI/180/2), 2)));
            double az=0;
            if (lats.get(i)>=90)
            {
                az=0;
            }
            else if (lats.get(i)<=-90)
            {
                az=Math.PI;
            }
            else
            {
                az=Math.atan2(Math.cos(lats.get(i)*Math.PI/180) * Math.sin(lons.get(i)*Math.PI/180),Math.sin(lats.get(i)*Math.PI/180))% (2*Math.PI);
            }
            if(i==0)
            {
                colat0=colat;
                az0=az;
            }
            if(i>0 && i<lats.size())
            {
                sum=sum+(1-Math.cos(prevcolat  + (colat-prevcolat)/2))*Math.PI*((Math.abs(az-prevaz)/Math.PI)-2*Math.ceil(((Math.abs(az-prevaz)/Math.PI)-1)/2))* Math.signum(az-prevaz);
            }
            prevcolat=colat;
            prevaz=az;
        }
        sum=sum+(1-Math.cos(prevcolat  + (colat0-prevcolat)/2))*(az0-prevaz);
        return 5.10072E14* Math.min(Math.abs(sum)/4/Math.PI,1-Math.abs(sum)/4/Math.PI);
    }

    public void captureMapScreen() {
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                try {
                    linear.setDrawingCacheEnabled(true);
                    Bitmap backBitmap = linear.getDrawingCache();
                    Bitmap bmOverlay = Bitmap.createBitmap(
                            backBitmap.getWidth(), backBitmap.getHeight(),
                            backBitmap.getConfig());
                    Canvas canvas = new Canvas(bmOverlay);
                    canvas.drawBitmap(snapshot, new Matrix(), null);
                    canvas.drawBitmap(backBitmap, 0, 0, null);
                    FileOutputStream out = new FileOutputStream(
                            Environment.getExternalStorageDirectory()
                                    + "/MapScreenShot"
                                    + System.currentTimeMillis() + ".png");

                    bmOverlay.compress(Bitmap.CompressFormat.PNG, 90, out);
                    Toast.makeText(MainActivity.this,"simpl",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"exception",Toast.LENGTH_LONG).show();
                }
            }
        };

        map.snapshot(callback);
      /*  final ContentValues values = new ContentValues(2);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATA, Environment.getExternalStorageDirectory()
                + "/MapScreenShot"
                + System.currentTimeMillis() + ".png");
        final Uri contentUriFile = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(android.content.Intent.EXTRA_STREAM, contentUriFile);
        startActivity(Intent.createChooser(intent, "Share Image"));*/
    }
    public void captureScreen()
    {
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback()
        {

            @Override
            public void onSnapshotReady(Bitmap snapshot)
            {
                // TODO Auto-generated method stub
                bitmap = snapshot;

                OutputStream fout = null;

                String filePath = System.currentTimeMillis() + ".jpeg";

                try
                {
                    fout = openFileOutput(filePath,
                            MODE_WORLD_READABLE);

                    // Write the string to the file
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
                    fout.flush();
                    fout.close();
                }
                catch (FileNotFoundException e)
                {
                    // TODO Auto-generated catch block
                    Log.d("ImageCapture", "FileNotFoundException");
                    Log.d("ImageCapture", e.getMessage());
                    filePath = "";
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    Log.d("ImageCapture", "IOException");
                    Log.d("ImageCapture", e.getMessage());
                    filePath = "";
                }

                openShareImageDialog(filePath);
            }
        };

        map.snapshot(callback);
    }

    public void openShareImageDialog(String filePath)
    {
        File file = this.getFileStreamPath(filePath);

        if(!filePath.equals(""))
        {
            final ContentValues values = new ContentValues(2);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            final Uri contentUriFile = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(android.content.Intent.EXTRA_STREAM, contentUriFile);
            startActivity(Intent.createChooser(intent, "Share Image"));
        }
        else
        {
            //This is a custom class I use to show dialogs...simply replace this with whatever you want to show an error message, Toast, etc.
            //DialogUtilities.showOkDialogWithText(this, R.string.shareImageFailed);
        }
    }
    private boolean isPointInPolygon(LatLng tap, ArrayList<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    private boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
    }
}
