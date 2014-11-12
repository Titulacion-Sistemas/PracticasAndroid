package com.example.jhonsson.practicacamara;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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


public class MyActivity extends Activity {

    private List<String> fileList = new ArrayList<String>();
    ListView listaGaleriaCuenta;
    Button boton;
    File mi_foto;
    File directorio , directorioc;


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

                    //Guarda imagen
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    //Intent retornar a otra actividad
                    // Intent i = new Intent(this, MyCam.class);

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
            //Hasta aqui
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



    }

    //Esto tambien agregue
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprobamos que la foto se a realizado

        if (requestCode == 0 && resultCode == RESULT_OK) {
            Log.e("ERROR ", "Error:" + mi_foto);

            try {
                mi_foto.createNewFile();

                ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                lg.add(directorioc.getPath());

                listaGaleriaCuenta.setAdapter(lg);

                //ListDir(directorio);
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
            ListaGaleriaCuentaAdapter adapter = new ListaGaleriaCuentaAdapter(this, fileList);
            listaGaleriaCuenta.setAdapter(adapter);
            //lfecha.add(String.valueOf(lista));
            // setListAdapter(adapter);
            Log.e("Informacion SI", "adaptador " + adapter);
        }
    }

    private File[] ordenarPrFecha(File[] sortedByDate) {
        if (sortedByDate != null && sortedByDate.length > 1) {
            Arrays.sort(sortedByDate, new Comparator<File>() {
                @Override
                public int compare(File object1, File object2) {
                    return (int) ((object1.lastModified() > object2.lastModified()) ? object1.lastModified() : object2.lastModified());
                }
            });
            return sortedByDate;
        }
        return sortedByDate;
    }
}
