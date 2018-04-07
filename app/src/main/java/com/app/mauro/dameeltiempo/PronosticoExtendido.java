package com.app.mauro.dameeltiempo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.app.mauro.dameeltiempo.ModelsPronosticos.Extendido.Forecastday;
import com.app.mauro.dameeltiempo.ModelsPronosticos.Extendido.PronosticoExtendidoModel;
import com.app.mauro.dameeltiempo.ViewHolders.AdapterExtendido;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PronosticoExtendido extends AppCompatActivity implements View.OnClickListener {

    private EditText ciudad;
    private Spinner cantDias;
    private Button boton;
    final String[] datos = new String [] {"1","2","3","4","5","6"};
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronostico_extendido);

        ciudad = (EditText) findViewById(R.id.CiudadPronosExt);
        cantDias = (Spinner) findViewById(R.id.SpinnerPronosExt);
        boton = (Button) findViewById(R.id.BotonPronosticoExt);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, datos);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cantDias.setAdapter(adapter);

        boton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        String city = ciudad.getText().toString().replace(" ", "%20");
        String seleccionSpinner = cantDias.getSelectedItem().toString();

        Call<PronosticoExtendidoModel> call = RetrofitClase.createRetrofit().getPronosticoExtendido(city, seleccionSpinner);

        call.enqueue(new Callback<PronosticoExtendidoModel>() {
            @Override
            public void onResponse(Call<PronosticoExtendidoModel> call, Response<PronosticoExtendidoModel> response) {
                if (response.isSuccessful()) {

                    PronosticoExtendidoModel pronosticoExtendidoModel = response.body();
                    ArrayList<Forecastday> forecastdays = pronosticoExtendidoModel.getForecast().getForecastday();

                    Log.d("sizeArray", "" + forecastdays.size());
                    Log.d("array", ""+ forecastdays.isEmpty());


                    recyclerView.setAdapter(new AdapterExtendido(forecastdays, getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<PronosticoExtendidoModel> call, Throwable t) {
                    Log.d("onFailure", t.getLocalizedMessage());
            }
        });



    }

    public void ponerFoto(ImageView img, String url) {
        String urlhttp = "http:" + url;
        Picasso.with(this).load(urlhttp).error(R.mipmap.ic_launcher).fit().centerInside().into(img);

    }

}
