package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView min;
    TextView max;
    TextView avg;
    SeekBar drag;
    Button generate;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("InClass04");

        min = findViewById(R.id.textViewMin);
        max = findViewById(R.id.textViewMax);
        avg = findViewById(R.id.textViewAvg);
        drag = findViewById(R.id.seekBar);
        generate = findViewById(R.id.buttonGenerate);
        new DoworKAsync().execute(100);
    }

    class DoworKAsync extends AsyncTask<Integer, Integer, Double>{

        @Override
        protected Double doInBackground(Integer... integers) {
            double sum = 0.0;
            double count = 0 , average = 0.0;
            for(int i=0;i<100;i++) {
                for (int j = 0; j < 10000000; j++) {
                    count++;
                    sum += j;
                }
                publishProgress(i);
            }
            average = sum/count;
            return average;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Updating Progress");
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }
    }
}
