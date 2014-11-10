package com.example.jhonsson.practicacamara;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class Principal extends Activity {
    private List<String> fileList = new ArrayList<String>();
    private List<String> date = new ArrayList<String>();
    ListView lista ;
    ListaAdaptadorFechas lfecha;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        /////////////////////////////////////////////////////////////////////////
        try {
            File directorio = new File(Environment
                    .getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM)+ "/CONIEL/")
                    .getAbsolutePath());
            ListDir(directorio);
            Log.e("Informacion ", "Entro por aqui " +directorio );
            }
        catch (Exception ex){
            Log.e("Ficheros", "Error al escribir fichero a tarjeta SD" + ex);}

        ////////////////////////////////////////////////////////////////////////////

    }

    /////////////////////////////////////////////////////////////////////////////
    void ListDir(File directorio){
        File[] files = directorio.listFiles();
        Log.i("Informacion", "valor files" +files);
        fileList.clear();
        for (File file : files){
            fileList.add(file.getPath());
        }
        if(fileList.toString() != ""){
            ListaAdaptadorFechas adapter = new ListaAdaptadorFechas(this, fileList);
            lista = (ListView) findViewById(R.id.listadeFechas);
            lista.setAdapter(adapter);
            lfecha.add(String.valueOf(lista));
           // setListAdapter(adapter);
            Log.e("Informacion SI", "adaptador " + adapter);
        }
        else {

            date.add(getDatePhone());
            ListaAdaptadorFechas adapter = new ListaAdaptadorFechas(this, date);
           // setListAdapter(adapter);
            Log.e("Informacion NO", "adaptador " + adapter);
        }

    }



    private String getDatePhone(){

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        String formato = df.format(date);

        return formato;
    }

    ////////////////////////////////////////////////////////////////////////////

}
