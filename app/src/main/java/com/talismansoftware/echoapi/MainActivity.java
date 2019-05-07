package com.talismansoftware.echoapi;

/* ===============================================================================
   Credits: 1. https://github.com/delaroy/LoginApplication/blob/master/app/src/main
   /java/com/delaroystudios/loginapplication/LoginActivity.java
   2. https://medium.com/android-grid/how-to-fetch-json-data-using-
   volley-and-put-it-to-recyclerview-android-studio-383059a12d1e
   3. https://stackoverflow.com/questions/5015844/parsing-json-object-in-java
   ================================================================================ */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EchoAPI";
    private static final String LOGIN_URL = "http://www.talismansoftwaresolutions.com/api/v1/tasks";

    private Button btn;
    private EditText txtEcho;
    private String result;
    private TextView txtResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btn = (Button) findViewById(R.id.btnGetResponse);
        txtEcho = (EditText) findViewById(R.id.txtEcho);
        txtResults = (TextView) findViewById(R.id.lblResults);

        btn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } /* end method */

    private void getData() {
        JsonObjectRequest jr = new JsonObjectRequest(
                Request.Method.GET,
                LOGIN_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String result = "None";
                        try {
                            JSONArray items = response.getJSONArray("items");
                            result = items.getJSONObject(0).getString("message");
                            txtResults.setText(result);
                            // txtEcho.setText(
                            //        items.getJSONObject(0).getString("message"));


                        } catch(JSONException ex) {
                            txtResults.setText(ex.toString());
                        } finally {
                            Log.e(TAG, response.toString());
                            //txtEcho.setText("Response: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      Log.e(TAG, error.toString());
                  }
        }

        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jr);

    } /* end method */

    /* private void getData_NEW() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(LOGIN_URL,
                new Response.Listener<JsonObjectRequest>() {
                    @Override
                    public void onResponse(JsonObjectRequest response) {
                        try {
                            JSONObject jsonObject = response;
                            result = response.getString("message");
                        } catch(JSONException e) {
                            result = e.toString();
                        } finally {
                            Log.e(TAG, result);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){};

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    } */



    private void getData_WORKING() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){};

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void getData_ORIGINAL() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(LOGIN_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            result = jsonObject.getString("message");
                            txtEcho.setText(jsonObject.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void userLogin() {
        Log.e(TAG, "Hello!");
    }

    private void userLogin_ORIGINAL() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            openProfile();
                        }else{
                            Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                });
    }

    private void openProfile() {
        txtEcho.setText("Success!");
    }

    @Override
    public void onClick(View v) {
        //userLogin();
        getData();
    }
}
