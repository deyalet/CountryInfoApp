package com.example.countryinfoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import WebService.WebService;

public class MainActivity extends AppCompatActivity {

    Button cargarimagen;
    Button procesar;
    ImageView imagen;
    TextView nombreimagen;
    Map<String, String> datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen = (ImageView) findViewById(R.id.idimagen);
        cargarimagen = (Button) findViewById(R.id.btabrirgaleria);
        procesar = (Button) findViewById(R.id.btnprocesar);
        nombreimagen = (TextView) findViewById(R.id.idnombreimagen);

        datos = new HashMap<String, String>();
        WebService ws= new WebService("http://www.geognos.com/api/en/countries/info/all.json", datos, this, this);
        ws.execute("");



        cargarimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"seleccione la imagen"),121);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 121){
            imagen.setImageURI(data.getData());

            FirebaseVisionImage img;
            try{
                img = FirebaseVisionImage.fromFilePath(getApplicationContext(),data.getData());

                FirebaseVisionTextRecognizer textRecognizer = FirebaseVisionTextRecognizer.getIstance(
                        .getOnDeviceTextRecognizer();

                textRecognizer.processImage(img)
                    .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>()){
                    public void onSuccess(FirebaseVisionText result){
                        nombreimagen.setText(FirebaseVisionText result);
                    }
                })
                .addOnFailureListener{
                    new OnFailureListener(){
                        public void onFailure(@NonNull Exception e){

                        }
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}