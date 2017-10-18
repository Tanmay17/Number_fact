package com.example.android.getthenumericfact;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    Button btda,bt;
    String radio_value,entered_value;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        radioGroup = (RadioGroup) findViewById( R.id.rg );
        bt = ( Button ) findViewById( R.id.randomButton );
        btda = ( Button ) findViewById( R.id.noButton );
        textView = (TextView ) findViewById( R.id.fact );
        btda.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_value = getRadioSelection();
                entered_value = getEnteredValue();
                MyTask mt = new MyTask();
                mt.execute( radio_value,entered_value );
            }
        } );
        bt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_value = getRadioSelection();
                entered_value = "random";
                MyTask mt = new MyTask();
                mt.execute( radio_value,entered_value );
            }
        } );
    }
    public String getRadioSelection()
    {
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = ( RadioButton ) findViewById( id );
        String tag = rb.getTag().toString();
        return tag;
    }
    public String getEnteredValue()
    {
        EditText editText = (EditText) findViewById( R.id.data );
        String data = editText.getText().toString();
        return data;
    }
    public void getFact(String f){
        textView.setText( f );
    }
    public class MyTask extends AsyncTask<String,Integer,String>{
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd=new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL( "http://numbersapi.com/"+strings[1]+"/"+strings[0] );
                InputStream in =url.openStream();
                DataInputStream din = new DataInputStream( in );
                String fact = din.readLine();
                getFact(fact);
            }
            catch (Exception e){}
            return "s";
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
        }
    }
}
