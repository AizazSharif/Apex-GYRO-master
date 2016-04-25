package com.example.aizaz.nofragmentmyrun.threads;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.aizaz.nofragmentmyrun.db.ToursDataSource;
import com.example.aizaz.nofragmentmyrun.model.Tour;

import java.util.ArrayList;
import java.util.List;


public class AsyncDb extends AsyncTask<Tour,Void,List<Tour>>  {




        Context contextD;
         Tour tour;
       ToursDataSource datasource;
    public AsyncDb(Context con){
       this.contextD=con;

    }
        @Override
        protected void onPostExecute(List<Tour> aVoid) {
             Toast.makeText(contextD, "db task done", Toast.LENGTH_LONG).show();

         }

        @Override
        protected List<Tour> doInBackground(Tour... params) {

         tour = new Tour();

          datasource = new ToursDataSource(contextD);
           datasource.open();


            if(params[0].getread()){
                List<Tour> toursSend = new ArrayList<Tour>();
              toursSend=  datasource.findAll();
return toursSend;

            }
            if(!params[0].getread()) {

                tour = datasource.create(params[0]);
                Log.d("DBwrite", "Tour created with id " + tour.getId());
                //  sendMessageToUI(1, "thread");
            }
            return null;
        }

    @Override
    protected void onCancelled() {
        datasource.close();
        super.onCancelled();
    }
}
