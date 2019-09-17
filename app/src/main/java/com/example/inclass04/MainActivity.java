package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView min;
    TextView max;
    TextView avg;
    TextView count;
    SeekBar drag;
    Button generate;
    ProgressDialog progressDialog;
    int step = 1;
    int maxvalue = 10;
    int minValue = 0;
    int complexityValue = 0;
    double minDisplay = 0;
    double maxDisplay = 0;
    double avgDisplay = 0;
    ArrayList<Double> arrList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("InClass04");

        min = findViewById(R.id.textViewMinDisplay);
        max = findViewById(R.id.textViewMaxDisplay);
        avg = findViewById(R.id.textViewAvgDisplay);
        count = findViewById(R.id.textViewCount);
        drag = findViewById(R.id.seekBar);
        drag.setMax( (maxvalue - minValue) / step );
        generate = findViewById(R.id.buttonGenerate);
        drag.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                complexityValue = minValue + (i * 1);
                count.setText(complexityValue + " Times");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("demo",complexityValue+"");
                arrList = HeavyWork.getArrayNumbers(complexityValue);
                if(complexityValue > 0) {
                    new DoworKAsync().execute(arrList);
                } else {
                    Toast.makeText(MainActivity.this, "Please drag seekbar", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    class DoworKAsync extends AsyncTask<ArrayList<Double>, Integer, Double>{

        @Override
        protected Double doInBackground(ArrayList<Double>... arrayLists) {
            minDisplay = Collections.min(arrayLists[0]);
            maxDisplay = Collections.max(arrayLists[0]);
            double sum =0;
            for(int i=0 ;i< arrayLists[0].size();i++) {
                publishProgress(i);
                sum += arrayLists[0].get(i);
            }
            avgDisplay = sum /arrayLists[0].size();
            Log.d("demo",minDisplay+"");
            Log.d("demo",maxDisplay+"");
            Log.d("demo",avgDisplay+"");

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Updating Progress");
            progressDialog.setMax(10);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            progressDialog.dismiss();
            min.setText(minDisplay+"");
            max.setText(maxDisplay+"");
            avg.setText(avgDisplay+"");

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }
    }
}
