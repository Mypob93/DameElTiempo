package com.app.mauro.dameeltiempo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.mauro.dameeltiempo.ModelsPronosticos.Normal.PronosticoClase;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pronostico extends AppCompatActivity implements View.OnClickListener{

    private Button pronostico;
    private TextView humedad, fecha, temperatura;
    private ImageView imagen;
    private EditText ciudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pronostico = (Button) findViewById(R.id.pronostico);
        humedad = (TextView) findViewById(R.id.humedad);
        fecha = (TextView) findViewById(R.id.fecha);
        temperatura = (TextView) findViewById(R.id.temperatura);
        imagen = (ImageView) findViewById(R.id.imagen);
        ciudad = (EditText) findViewById(R.id.ciudadIng);

        pronostico.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.pronostico:

                String city = ciudad.getText().toString();

                city.replace(" ", "$20");

                Call<PronosticoClase> call = RetrofitClase.createRetrofit().getPronostico(city);

                Log.i("url formada", call.request().url().toString());

                call.enqueue(new Callback<PronosticoClase>() {
                    @Override
                    public void onResponse(Call<PronosticoClase> call, Response<PronosticoClase> response) {
                        if (response.isSuccessful()) {

                            PronosticoClase pronostico = response.body();

                            mostrarDatos(pronostico);
                        }
                    }

                    @Override
                    public void onFailure(Call<PronosticoClase> call, Throwable t) {

                        Log.i("Problema", t.getLocalizedMessage());

                    }
                });
                break;
        }
    }


    public void mostrarDatos(PronosticoClase pronostico) {
        temperatura.setText(pronostico.getCurrent().getTemp_c() + " C`");
        fecha.setText(pronostico.getLocation().getLocaltime());
        ponerFoto(pronostico.getCurrent().getCondition().getIcon());
        humedad.setText("" + pronostico.getCurrent().getHumidity() + "%");
    }


    public void ponerFoto(String url) {
        String urlhttp = "http:" + url;
        Picasso.with(this).load(urlhttp).error(R.mipmap.ic_launcher).fit().centerInside().into(imagen);

    }

}
