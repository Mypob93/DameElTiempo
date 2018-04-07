package com.app.mauro.dameeltiempo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaPrincipal extends AppCompatActivity implements View.OnClickListener{

    private Button pronos, pronosExt, pronosLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        pronos = (Button) findViewById(R.id.pronostico);
        pronosExt = (Button) findViewById(R.id.pronosticoExtendido);
        pronosLoc = (Button) findViewById(R.id.pronosticoLocalizacion);

        pronos.setOnClickListener(this);
        pronosExt.setOnClickListener(this);
        pronosLoc.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent i;

        switch (id) {
            case R.id.pronostico:
                i = new Intent(PantallaPrincipal.this, Pronostico.class);
                startActivity(i);
                break;

            case R.id.pronosticoExtendido:
                i = new Intent(PantallaPrincipal.this, PronosticoExtendido.class);
                startActivity(i);
                break;

            case R.id.pronosticoLocalizacion:
                i = new Intent(PantallaPrincipal.this, ActivityLocalizacion.class);
                startActivity(i);
                break;
        }
    }
}
