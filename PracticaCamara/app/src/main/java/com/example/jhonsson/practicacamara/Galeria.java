package com.example.jhonsson.practicacamara;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class Galeria extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        String ruta = getIntent().getStringExtra("ruta");
        File  dir = new File(ruta);
        GridView gridView = (GridView) findViewById(R.id.gridViewGaleria);

        //el número de columnas se calculará en función del tamaño de pantalla y la posición
        boolean bigScreen = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if (bigScreen)
            {
                gridView.setNumColumns(4);
            }
            else
            {
                gridView.setNumColumns(3);
            }
        }
        else
        {
            if (bigScreen)
            {
                gridView.setNumColumns(3);
            }
            else
            {
                gridView.setNumColumns(2);
            }
        }

        gridView.setAdapter(new GalleryAdapter(this, dir.listFiles()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Toast.makeText(Galeria.this, ((TextView) view.findViewById(R.id.txtNombreFoto)).getText() + " seleccionada", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.galeria, menu);
        return true;
    }

    protected void onStop(){
        super.onStop();
        Log.e("Error OnStop", "Paso por aqui");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Error OnPause", "Paso por aqui");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        Log.e("Error OnDestroy", "Paso por aqui");
    }
}
