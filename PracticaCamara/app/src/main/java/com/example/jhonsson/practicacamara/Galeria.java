package com.example.jhonsson.practicacamara;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Galeria extends Activity {

    GridView gridView;
    ArrayList<File> aEliminar = new ArrayList<File>();
    ArrayList<Integer> ItemaEliminar = new ArrayList<Integer>();
    String ruta;
    File mi_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        ruta = getIntent().getStringExtra("ruta");

        gridView = (GridView) findViewById(R.id.gridViewGaleria);

        //el número de columnas se calculará en función del tamaño de pantalla y la posición
        boolean bigScreen = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            if (bigScreen)  gridView.setNumColumns(4);
            else            gridView.setNumColumns(3);
        else
            if (bigScreen)  gridView.setNumColumns(3);
            else            gridView.setNumColumns(2);

        gridView.setAdapter(new GalleryAdapter(this, (new File(ruta)).listFiles()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                openImg(
                    new  File(
                            ruta+"/"+((TextView) view.findViewById(R.id.txtNombreFoto)).getText().toString()+".jpg"
                    )
                );
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                File f = new  File(
                        ruta+"/"+((TextView) view.findViewById(R.id.txtNombreFoto)).getText().toString()+".jpg"
                );
                if (aEliminar.indexOf(f)==-1) {
                    aEliminar.add(f);
                    ItemaEliminar.add(position);
                    gridView.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                }else{
                    aEliminar.remove(f);
                    ItemaEliminar.remove(position);
                    gridView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                }
                return true;
            }
        });
        String[] r = ruta.split("/");
        setTitle(r[r.length-1]);
        try {
            getActionBar().setHomeButtonEnabled(true);
        }catch (Exception ignored){}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.galeria, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                nuevaFoto();
                return true;
            case R.id.menu_new:
                eliminarFotos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void eliminarFotos() {
        for (File aItemaEliminar : aEliminar) {
            aItemaEliminar.delete();
        }
        try {
            gridView.setAdapter(new GalleryAdapter(this, (new File(ruta)).listFiles()));
            updateView(0);
        } catch (Exception ex) {
            Log.e("ERROR ", "catch :" + ex);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {

            try {
                mi_foto.createNewFile();
                gridView.setAdapter(new GalleryAdapter(this, (new File(ruta)).listFiles()));
                updateView(0);
            } catch (IOException ex) {
                Log.e("ERROR ", "catch :" + ex);
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void reSeleccion() {
        for (Integer aItemaEliminar : ItemaEliminar) {
            gridView.getChildAt(aItemaEliminar).setBackgroundColor(Color.CYAN);
        }
    }

    private void nuevaFoto() {

        File directorioc = new File(ruta);

        //Si no existe crea la carpeta donde se guardaran las fotos
        File file = new File(directorioc, getCode() + ".jpg");

        mi_foto = new File(file + "");

        Log.e("ERROR ", "Error:" + mi_foto);

        Uri uri = Uri.fromFile(mi_foto);

        Log.i("Uri ", "Uri:" + uri);

        //Abre la camara para tomar la foto
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Guarda imagen
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //Intent retornar a otra actividad
        // Intent i = new Intent(this, MyCam.class);

        //Retorna a la actividad
        startActivityForResult(cameraIntent, 0);

    }

    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String date = dateFormat.format(new Date());
        Log.i("INFORMACION", "Codigo :" + date);
        return date;
    }

    void openImg(File file)
    {
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"image/jpeg");
        target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(target);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(
                    Galeria.this,
                    "No se ha podido abrir archivo: "+file.getPath(),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void updateView(int index){
        View v = gridView.getChildAt(index -
                gridView.getFirstVisiblePosition());
    }

}
