package com.example.countryinfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class InfoWindow extends AppCompatActivity {
    TextView nombrePais;
    ImageView imagenPais;
    TextView informacionPais;
    GoogleMap mapa;
    Pais pais;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_window);

        pais = new Pais();

        nombrePais = (TextView) findViewById(R.id.txtpaisNombre);
        imagenPais = (ImageView) findViewById(R.id.imagenPais);
        informacionPais = (TextView) findViewById(R.id.txtinfoPais);

        Bundle bundle = this.getIntent().getExtras();

        nombrePais.setText(bundle.getString("nombrepais"));

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync((OnMapReadyCallback) this);

    }

    public void onMapReady(GoogleMap googleMap) {

        Glide.with( this.getApplicationContext()).load(getIntent().getStringExtra("url_imagen")).into(imagenPais);
        mapa = googleMap;
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        nombrePais.setText(getIntent().getStringExtra("nombre_pais"));

        double [] coor = getIntent().getDoubleArrayExtra("Coordenadas");

        String info = "Pais: " + getIntent().getStringExtra("nombre_Pais") + "\n" +
                "Url bandera: " + getIntent().getStringExtra("url_imagen") + "\n" +
                "Capital: " + getIntent().getStringExtra("capital_Pais") + "\n" +
                "Coordenadas: " + "\n" +
                "WEST: " +coor[0] + "\n" +
                "EAST: " + coor[1] + "\n" +
                "NORTH: " + coor[2] + "\n" +
                "SOUTH" + coor[3];
        informacionPais.setText(info);

        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(coor[4],coor[5]), 5);
        mapa.moveCamera(camUpd1);
    }

}