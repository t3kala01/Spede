package com.example.spede;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {


    private Button btnHit;
    private ProgressDialog pd;
    private ArrayList<Pelaaja> pelaajalista;
    private ListView listView;
    private PelaajaAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pelaajalista = new ArrayList<>();
        listView = findViewById(R.id.lista);
        pAdapter = new PelaajaAdapter(this,pelaajalista);
        listView.setAdapter(pAdapter);
        final ImageView image = findViewById(R.id.kuva);
        image.setImageResource(R.drawable.spede3);


        btnHit = (Button) findViewById(R.id.button1);


        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pAdapter.clear();
                pelaajalista.clear();
                new JsonTask().execute("http://ec2-18-206-35-254.compute-1.amazonaws.com:4000/Spede");

            }
        });

    }




    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Eh nimittain ladataan pisteita");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            ArrayList<Pelaaja> lista = new ArrayList<>();
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            try {
                JSONArray jArray = new JSONArray(result);

                for(int i = 0 ; i<jArray.length(); i++){
                    String nimi = jArray.getJSONObject(i).get("nimi").toString();
                    String pisteet = jArray.getJSONObject(i).get("pisteet").toString();
                    Pelaaja pelaaja = new Pelaaja(nimi,pisteet);
                    lista.add(pelaaja);
                }
                Collections.sort(lista);

                for(int i = 0 ; i<lista.size();i++) {
                    pAdapter.add(lista.get(i));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}






