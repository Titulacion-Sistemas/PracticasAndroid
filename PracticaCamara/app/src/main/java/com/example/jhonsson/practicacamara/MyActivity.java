package com.example.jhonsson.practicacamara;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import classes.MyLocation;
import classes.SessionManager;


public class MyActivity extends Activity {

    private List<String> fileList = new ArrayList<String>();
    ListView listaGaleriaCuenta;
    Button boton;
    File mi_foto;
    File directorio , directorioc;
    ArrayList<Integer> aEliminar = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //Captura de datos enviados de la actividad anterior
        final String fecha = getIntent().getStringExtra("fecha");

        listaGaleriaCuenta = (ListView)findViewById(R.id.lvCuentasGaleria);

        boton = (Button) findViewById(R.id.btnSiguiente);

        directorio = new File(Environment
                .getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM) + "/CONIEL/" + fecha + "/")
                .getAbsolutePath());

        ListDir(directorio);

        //accion para el boton
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView txtNumCuenta = (TextView) findViewById(R.id.editTextCuenta);

                if(! txtNumCuenta.getText().toString().equals("")) {
                    // Agregue
                    directorioc = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/CONIEL/" + fecha + "/" + txtNumCuenta.getText() + "/");
                    directorioc.mkdirs();
                    //Si no existe crea la carpeta donde se guardaran las fotos
                    File file = new File(directorioc, getCode() + ".jpg");

                    mi_foto = new File(file + "");

                    Uri uri = Uri.fromFile(mi_foto);

                    Log.e("ERROR ", "Uri:" + uri);

                    //Abre la camara para tomar la foto
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    //txtNumCuenta.setText("");

                    //Guarda imagen
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                    //Retorna a la actividad
                    startActivityForResult(cameraIntent, 0);
                }
                else{
                    Toast mensaje =
                            Toast.makeText(getApplicationContext(),
                                    " Ingrese una Cuenta Por Favor ", Toast.LENGTH_SHORT);

                    mensaje.show();
                }
            }
        });

        listaGaleriaCuenta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.txtCuentaLista);
                Log.i("Seleccion...","Se seleccionó el elemento "+textView.getText()+" en la posicion "+position);
                ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();

                Intent i = new Intent(MyActivity.this , Galeria.class);
                i.putExtra("ruta", lg.itemCuenta.get(position));
                startActivity(i);
            }
        });

        listaGaleriaCuenta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Button btnBorrar = (Button) findViewById(R.id.btnBorrar);
                if (aEliminar.indexOf(position)==-1) {
                    aEliminar.add(position);
                    listaGaleriaCuenta.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                    btnBorrar.setVisibility(View.VISIBLE);
                }else{
                    aEliminar.remove((Object)position);
                    listaGaleriaCuenta.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    if (aEliminar.size()==0){
                        btnBorrar.setVisibility(View.INVISIBLE);
                    }
                }
                return true;
            }
        });

        ((Button) findViewById(R.id.btnBorrar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarDir();
            }
        });

    }

    private void eliminarDir() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Esta seguro que desea elimiar el directorio y todo su contenido?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                    for (Integer aItemaEliminar : aEliminar) {
                        File f = new File(
                            directorio.getPath()+
                            "/"+
                            ((TextView)(listaGaleriaCuenta.getChildAt(aItemaEliminar)).findViewById(R.id.txtCuentaLista)).getText().toString()
                        );

                        for(File foto:f.listFiles())
                            Log.i("Borro el Archivo: "+foto.getPath()+"?",foto.delete()+"");

                        Log.i("Borro el Directorio: "+f.getPath()+"?",f.delete()+"");
                        lg.remove(f.getPath());
                    }

                    listaGaleriaCuenta.setAdapter(lg);
                    //((ListaGaleriaCuentaAdapter)listaGaleriaCuenta.getAdapter()).notifyDataSetChanged();
                    aEliminar=new ArrayList<Integer>();
                } catch (Exception ex) {
                    Log.e("ERROR ", "catch :" + ex);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Code that is executed when clicking NO
                ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                listaGaleriaCuenta.setAdapter(lg);
                aEliminar=new ArrayList<Integer>();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Esto tambien agregue
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprobamos que la foto se a realizado

        if (requestCode == 0 && resultCode == RESULT_OK) {
            Log.e("ERROR ", "Error:" + mi_foto);

            try {
                mi_foto.createNewFile();

                try {

                    MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                        @Override
                        public void gotLocation(Location location){
                            //Got the location!
                            LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
                            Location L= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            Log.i("Ultima localización conocida:","Latitud: "+L.getLatitude());
                            Log.i("Ultima localización conocida:","Longitud: "+L.getLongitude());
                            SessionManager.getManager(directorioc)
                                    .saveKey("Latitud",L.getLatitude()+"")
                                    .saveKey("Longitud", L.getLongitude()+"");

                            Toast.makeText(
                                    getApplicationContext(),
                                    "La ubicación de la foto ha sido almacenada",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }
                    };
                    MyLocation myLocation = new MyLocation();
                    myLocation.getLocation(this, locationResult);

                }catch (Exception ignored){
                    ignored.printStackTrace();
                }

                ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                lg.add(directorioc.getPath());

                listaGaleriaCuenta.setAdapter(lg);

            } catch (IOException ex) {
                Log.e("ERROR ", "catch :" + ex);
            }
        }
    }


    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String date = dateFormat.format(new Date());
        Log.i("INFORMACION", "Codigo :" + date);
        return date;
    }

    /////////////////////////////////

    void ListDir(File directorio){
        File[] files = ordenarPrFecha(directorio.listFiles());
        Log.i("Informacion", "valor files" +files);
        for (File file : files) {
            fileList.add(file.getPath());
        }
        if(fileList != null) {
            listaGaleriaCuenta.setAdapter(new ListaGaleriaCuentaAdapter(this, fileList));
        }
    }

    private File[] ordenarPrFecha(File[] sortedByDate) {
        if (sortedByDate != null && sortedByDate.length > 1) {
            Arrays.sort(sortedByDate, new Comparator()
            {
                public int compare(final Object o1, final Object o2) {
                    return new Long(((File)o2).lastModified()).compareTo
                            (new Long(((File) o1).lastModified()));
                }
            });
            return sortedByDate;
        }
        return sortedByDate;
    }
}
