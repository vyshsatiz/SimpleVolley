package com.example.vyshalis.vol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView mTextView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RequestQueue queue = Volley.newRequestQueue(this);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView = (TextView) findViewById(R.id.textView);
                editText = (EditText) findViewById(R.id.etxt);
                final String test = editText.getText().toString();
                final String url = "http://api.openweathermap.org/data/2.5/weather?zip=" + test + ",us&appid=5ce2dbcff5f7d451769448efe9671b1a&units=imperial";
                Log.i("randomtext", url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                               /* try {
                                    final JSONObject nJson= new JSONObject(url);
                                    final JSONObject iJson = nJson.getJSONObject("main");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/

                                mTextView.setText(parseJSON(response)+"\n"+locationJSON(response)+"\n"+cordJSON(response)+"\n"+windJSON(response));
                                //Toast.makeText(MainActivity.this, "Response"+response, Toast.LENGTH_SHORT).show();
                            }


                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Response" + error, Toast.LENGTH_SHORT).show();
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });

    }
    public String parseJSON(String response){
        String result= "";
        try{
            JSONObject weather = (new JSONObject(response)).getJSONObject("main");
            int Temperture = weather.getInt("temp");
            int humidity = weather.getInt("humidity");
            result ="Temperture: "+Temperture+"\r\nHumidity: "+humidity;

        }catch (Exception e){e.printStackTrace();}
        return result;
    }
    public String locationJSON(String response){
        String result= "";
        try{
            JSONObject weather = (new JSONObject(response)).getJSONObject("sys");
            String LOCATION = (String) weather.get("country");

            result ="Location: "+LOCATION;
        }catch (Exception e){e.printStackTrace();}
        return result;
    }
    private String cordJSON(String response) {
        String result="";
        try{
        JSONObject coord = (new JSONObject(response)).getJSONObject("coord");
        int lon = coord.getInt("lon");
        int lat = coord.getInt("lat");
            result ="lon: "+lon+"\r\nlat: "+lat;

        }catch (Exception e){e.printStackTrace();}
        return result;}

        private String windJSON(String response) {
            String result="";
            try{
                JSONObject wind = (new JSONObject(response)).getJSONObject("wind");
                int speed = wind.getInt("speed");
                int degree = wind.getInt("degree");
                int gust = wind.getInt("gust");

                result ="speed: "+speed+"\r\ndegree: "+degree+"\r\ngust: "+gust;

            }catch (Exception e){e.printStackTrace();}
            return result;

        }

    }
