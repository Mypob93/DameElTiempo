package com.app.mauro.dameeltiempo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mauro.dameeltiempo.ModelsPronosticos.Normal.PronosticoClase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLocalizacion extends AppCompatActivity implements View.OnClickListener, LocationListener {


    private Button pronostico, localizado;
    private TextView fecha, temperatura, humedad, ciudadLocacion;
    private ImageView imagen;
    private Toolbar toolbar;
    private String longitud, latitud;
    private LocationManager mlocManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);

        pronostico = (Button) findViewById(R.id.pronosticoLocacion);
        localizado = (Button) findViewById(R.id.obtenerLocalizacion);

        fecha = (TextView) findViewById(R.id.fechaLocacion);
        temperatura = (TextView) findViewById(R.id.temperaturaLocacion);
        humedad = (TextView) findViewById(R.id.humedadLocacion);
        imagen = (ImageView) findViewById(R.id.imagenLocacion);

        toolbar = (Toolbar) findViewById(R.id.toolbarLocacion);
        ciudadLocacion = (TextView) findViewById(R.id.CiudadLocacion);

        longitud = "";
        latitud = "";

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pronostico.setOnClickListener(this);

        pronostico.setClickable(false);

        localizado.setOnClickListener(this);

        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


    }



    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.obtenerLocalizacion:
                locationStart();
                pronostico.setClickable(true);
                break;

                case R.id.pronosticoLocacion:
                String latLong = latitud + "," + longitud;

                obtenerDatos(latLong);

                break;
        }


    }

    private void obtenerDatos(String latLong) {

        retrofit2.Call<PronosticoClase> call = RetrofitClase.createRetrofit().getPronosticoLocalizacion(latLong);
        call.enqueue(new Callback<PronosticoClase>() {
            @Override
            public void onResponse(retrofit2.Call<PronosticoClase> call, Response<PronosticoClase> response) {

                Log.d("onResponse", "Entro a onResponse");

                if (response.isSuccessful()) {
                    PronosticoClase pronosticoClase = response.body();

                    temperatura.setText(pronosticoClase.getCurrent().getTemp_c() + " C");
                    fecha.setText(pronosticoClase.getLocation().getLocaltime());
                    ponerFoto(pronosticoClase.getCurrent().getCondition().getIcon());
                    humedad.setText("" + pronosticoClase.getCurrent().getHumidity() + "%");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<PronosticoClase> call, Throwable t) {
                Log.d("onFailure", t.getLocalizedMessage());
            }
        });

    }

    public void ponerFoto(String url) {
        String httpurl = "http:" + url;
        Picasso.with(this).load(httpurl).error(R.mipmap.ic_launcher).fit().centerInside().into(imagen);

    }

    private void locationStart() {

        Log.i("Data", "Entre a locationStart");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        } else {
            Log.i("Data", "Entre al else de locationStart");

            if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
            } else {
                Toast.makeText(this, "Activa ubicacion!", Toast.LENGTH_SHORT).show();
            }


        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("Data", "Entre a locationChanged");

        if (location != null) {
            Log.i("Data", "Entre al if de locationChanged");
            mlocManager.removeUpdates(this);

            longitud = "" + location.getLongitude();
            latitud = "" + location.getLatitude();

            Geocoder geocoder = new Geocoder(ActivityLocalizacion.this);

            try {
                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                ciudadLocacion.setText("Direccion: " + addressList.get(0).getAddressLine(0));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }



}
