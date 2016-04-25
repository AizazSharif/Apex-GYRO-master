package com.example.aizaz.nofragmentmyrun;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aizaz.nofragmentmyrun.db.ToursDataSource;
import com.example.aizaz.nofragmentmyrun.model.Tour;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aizaz on 3/3/2016.
 */
public class History extends AppCompatActivity {
    static LatLng abc=new LatLng(0,0);
    private ArrayList<LatLng> arrayPoints = null;
    List<Tour> toursSendMaps = new ArrayList<Tour>();
    //  public AsyncDbMaps aDB;
    private GoogleMap googleMap;
    Button Home,History,Cloud,Maps;
    TextView txt;
    GPSTracker gps;
    private Context mContext;
    PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
    double latitude;
    double longitude;
    Tour tour;
    ToursDataSource datasource;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        Home= (Button) findViewById(R.id.button);
        History= (Button) findViewById(R.id.button2);
        Cloud= (Button) findViewById(R.id.button3);
        Maps= (Button) findViewById(R.id.button4);
        mContext = this;
        //aDB=new AsyncDb(this);

        //  arrayPoints = new ArrayList<LatLng>();
      /*  LatLng newPoint = new LatLng(34.014975, 71.580490);//Peshawar
        LatLng newPoint1 = new LatLng(34.015856, 71.975452);//Noshera
        LatLng newPoint2 = new LatLng(34.149433, 71.742781);//Mardan
        LatLng newPoint3 = new LatLng(33.819487, 72.689026);//Abotabad

        LatLng newPoint4 = new LatLng(34.168750, 73.221498);

        arrayPoints.add(newPoint);
        arrayPoints.add(newPoint1);
        arrayPoints.add(newPoint2);
        arrayPoints.add(newPoint3);
        arrayPoints.add(newPoint4);
        //	aDB.execute();*/
        gps = new GPSTracker(this);
        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            //LatLng newPoint = new LatLng(gps.getLatitude(), gps.getLongitude());
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            // Globals.arrayPoints = new ArrayList<LatLng>();;
            Home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(com.example.aizaz.nofragmentmyrun.History.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            History.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            Cloud.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(com.example.aizaz.nofragmentmyrun.History.this,HistoryTwo.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            arrayPoints = new ArrayList<LatLng>();
            Maps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Tour tour = new Tour();
                    List<Tour> toursSend = new ArrayList<Tour>();

                    tour.setread();
                    //   aDB = new AsyncDbR(mContext);

                    //   aDB.execute(tour);


                    tour = new Tour();

                    datasource = new ToursDataSource(mContext);

                    datasource.open();

                    Log.d("historytwo", "in bgthread");


                    toursSend=  datasource.findAll();
                    for(int i=0; i<toursSend.size();i++){
                        Log.d("SelfDb " + toursSend.get(i).getLatitude(), " longi " + toursSend.get(i).getLongitude());

                        toursSend.get(i).getActLabel();
                        LatLng newPoint = new LatLng(toursSend.get(i).getLatitude(), toursSend.get(i).getLongitude());
                        arrayPoints.add(newPoint);
                    }


                   /* Tour tour = new Tour();
                    List<Tour> toursSend = new ArrayList<Tour>();

                    tour.setread();
                    aDB = new AsyncDbCloud(mContext);

                    aDB.execute(tour);



                    if(payloadSend!=null){
                        for(int i=0;i<payloadSend.size();i++){
                            LatLng newPoint = new LatLng(payloadSend.get(i).getLatitude(), payloadSend.get(i).getLongitude());
                            arrayPoints.add(newPoint);
                            Log.d("Lati", " " + payloadSend.get(i).getLatitude());
                            Log.d("arraypts"," "+arrayPoints.size());

                        }}
                        */

                    LatLng newPoint3 = new LatLng(33.6667, 73.1667);//Peshawar
                    LatLng newPoint1 = new LatLng(31.5497, 74.3436);//lahore
                    arrayPoints.add(newPoint1);

                    // LatLng newPoint2 = new LatLng(34.149433, 71.742781);//Mardan
                    // LatLng newPoint3 = new LatLng(33.819487, 72.689026);//Abotabad
                    //arrayPoints.add(newPoint2);



                    Polyline line = googleMap.addPolyline(new PolylineOptions()
                            .addAll(arrayPoints)
                            .width(16)
                            .color(Color.BLUE)
                            .geodesic(true));

                    //  googleMap.moveCamera(point);
// animates camera to coordinates
                    //  googleMap.animateCamera(point,15.0F);

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newPoint3, 7.0f));



                }
            });
            try {
                // Loading map
                initilizeMap();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            googleMap.setMyLocationEnabled(true);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (aDB != null) {
            aDB.cancel(true);
        }


        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (aDB != null) {
            aDB.cancel(true);
        }
        finish();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }




    List<Tour> payloadSend = new ArrayList<Tour>();

    public AsyncDbCloud aDB;
    ////////////
    public class AsyncDbCloud extends AsyncTask<Tour,Void,List<Tour>>  {




        Context contextD;
        Tour tour;
        ToursDataSource datasource;
        public AsyncDbCloud(Context con){
            this.contextD=con;

        }
        @Override
        protected void onPostExecute(List<Tour> aVoid) {
            //	Toast.makeText(contextD, "db task done", Toast.LENGTH_LONG).show();
            if(aVoid!=null){
                Toast.makeText(contextD, "payload here"+aVoid.size(), Toast.LENGTH_LONG).show();
                payloadSend=aVoid;


            }

        }

        @Override
        protected List<Tour> doInBackground(Tour... params) {

            tour = new Tour();

            datasource = new ToursDataSource(contextD);
            datasource.open();


            if(params[0].getread()){
                List<Tour> toursSend = new ArrayList<Tour>();
                toursSend=  datasource.findAll();
                datasource.deleteAll();
                return toursSend;

            }
            if(!params[0].getread()) {

                //	tour = datasource.create(params[0]);
                //Log.d("DBwrite", "Tour created with id " + tour.getId());
                //  sendMessageToUI(1, "thread");
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            if(datasource!=null){
                datasource.close();}
            super.onCancelled();
        }
    }


    //////////







}
