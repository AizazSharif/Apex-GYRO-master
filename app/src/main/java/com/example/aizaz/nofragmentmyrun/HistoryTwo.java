package com.example.aizaz.nofragmentmyrun;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.aizaz.nofragmentmyrun.db.ToursDataSource;
import com.example.aizaz.nofragmentmyrun.model.Tour;
import com.example.aizaz.nofragmentmyrun.db.ToursDataSource;
import com.example.aizaz.nofragmentmyrun.model.Tour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mudassir on 3/10/2016.
 */
public class HistoryTwo extends AppCompatActivity {

    List<Tour> payloadSend = new ArrayList<Tour>();

   // public AsyncDbR aDB;
    Tour tour;
    ToursDataSource datasource;
Context mContext;
    private EditText mHistoryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button b1,b2;
        Button Home,History,Cloud,Maps;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud);
        b1= (Button) findViewById(R.id.btnPost);
        b2= (Button) findViewById(R.id.btnRefresh);
        mHistoryText = (EditText) findViewById(R.id.post_text);

        mContext =this;
        Home= (Button) findViewById(R.id.button);
        History= (Button) findViewById(R.id.button2);
        Cloud= (Button) findViewById(R.id.button3);
     //   Tour tourSetter = new Tour();
    //    List<Tour> toursSend = new ArrayList<Tour>();

    //    tourSetter.setread();




        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryTwo.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryTwo.this, History.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });;

        Cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });






        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

Log.d("historytwo","readbtn");
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
                        Log.d("SelfDb "+ toursSend.get(i).getLatitude()," longi "+toursSend.get(i).getLongitude() );

                        toursSend.get(i).getActLabel();
                        mHistoryText.setText("Database size = "+toursSend.size());

                    }

                if (payloadSend != null) {

                       // Toast.makeText(mContext, "db size"+payloadSend.size(), Toast.LENGTH_LONG).show();


                    }


                }



        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource = new ToursDataSource(mContext);

                datasource.open();
                datasource.deleteAll();
                Toast.makeText(mContext, "db deleted", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        if(datasource!=null) {
        datasource.close();
        }
        super.onDestroy();
    }

    public class AsyncDbR extends AsyncTask<Tour,Void,List<Tour>> {




        Context contextD;
        Tour tour;
        ToursDataSource datasource;
        public AsyncDbR(Context con){
            this.contextD=con;

        }
        @Override
        protected void onPostExecute(List<Tour> aVoid) {
            Toast.makeText(contextD, "db read task done", Toast.LENGTH_LONG).show();
            payloadSend=aVoid;

        }

        @Override
        protected List<Tour> doInBackground(Tour... params) {

          /*
                toursSend.get(1).getActLabel();
                Log.d("SelfDb"," "+ toursSend.get(1).getActLabel());
                return toursSend;

            }
            if(!params[0].getread()) {
                Log.d("historytwo","readbtn");

                tour = datasource.create(params[0]);
                Log.d("DBwrite", "Tour created with id " + tour.getId());
                //  sendMessageToUI(1, "thread");
            }  */
            return null;
        }

        @Override
        protected void onCancelled() {
            datasource.close();
            super.onCancelled();
        }
    }



}
