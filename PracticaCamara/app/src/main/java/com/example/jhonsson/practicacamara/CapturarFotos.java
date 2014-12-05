package com.example.jhonsson.practicacamara;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
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

import clases.GuardarFotos;
import clases.MyLocation;


public class CapturarFotos extends Activity {

    private List<String> fileList = new ArrayList<String>();
    ListView listaGaleriaCuenta;
    Button boton;
    File mi_foto;
    File directorio , directorioc;
    ArrayList<Integer> aEliminar = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturar_fotos);

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

                Intent i = new Intent(CapturarFotos.this , Galeria.class);
                i.putExtra("ruta", lg.itemCuenta.get(position));
                startActivity(i);
            }
        });

        listaGaleriaCuenta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Button btnBorrar = (Button) findViewById(R.id.btnBorrar);
                try {
                    Log.i("Child...",""+listaGaleriaCuenta.getChildAt(position)+"");
                    if (aEliminar.indexOf((Object)position)==-1) {
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
                }catch (Exception e){
                    Log.e("Error al Seleccionar",""+e.toString());
                    aEliminar=new ArrayList<Integer>();
                    ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                    listaGaleriaCuenta.setAdapter(lg);
                    btnBorrar.setVisibility(View.INVISIBLE);
                    getToast("Error al Seleccionar...");
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
                            if(location!=null) {

                                Log.i("Ultima localización conocida:", "Latitud: " + location.getLatitude());
                                Log.i("Ultima localización conocida:", "Longitud: " + location.getLongitude());
                                GuardarFotos.getManager(directorioc)
                                        .saveKey("Latitud", location.getLatitude() + "")
                                        .saveKey("Longitud", location.getLongitude() + "");

                                getToast("La ubicación de la foto ha sido almacenada");

                            }else{
                                getToast("Error, Ubicación NO detectada");
                                GuardarFotos.getManager(directorioc)
                                        .saveKey("Latitud", "Por favor, actualice")
                                        .saveKey("Longitud", "datos de ubicación...");
                            }

                        }
                    };
                    MyLocation myLocation = new MyLocation();
                    myLocation.getLocation(this, locationResult);
                    getToast("Esperando ubicación...");

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

    private void getToast(String s){
        Toast.makeText(
                getApplicationContext(),
                s,
                Toast.LENGTH_SHORT
        ).show();
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
