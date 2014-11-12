package com.example.jhonsson.practicacamara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class Principal extends Activity {
    private List<String> fileList = new  ArrayList<String>();
    private List<String> date = new ArrayList<String>();
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

       File hoy = new File(Environment
                .getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM)+ "/CONIEL/"+getDatePhone()+"/")
                .getAbsolutePath());
        hoy.mkdirs();

        File directorio = new File(Environment
                .getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM)+ "/CONIEL/")
                .getAbsolutePath());
        ListDir(directorio);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.txtListaFechas);
                Log.i("Seleccion...","Se seleccionó el elemento "+textView.getText()+" en la posicion "+position);
                Intent i = new Intent(Principal.this , MyActivity.class);
                i.putExtra("fecha", textView.getText());
                startActivity(i);

            }
        });
        //Log.e("Ficheros", "Error al escribir fichero a tarjeta SD" + ex);
        ////////////////////////////////////////////////////////////////////////////

    }


    /////////////////////////////////////////////////////////////////////////////
    void ListDir(File directorio){
        File[] files = ordenarPrFecha(directorio.listFiles());
        Log.i("Informacion", "valor files" +files);
        fileList.clear();
        for (File file : files) {
            String[] path = (file.getPath()).split("/");
            fileList.add(path[path.length-1]);
        }
        if(fileList.toString() != null) {
            ListaAdaptadorFechas adapter = new ListaAdaptadorFechas(this, fileList);
            lista = (ListView) findViewById(R.id.listadeFechas);
            lista.setAdapter(adapter);
            //lfecha.add(String.valueOf(lista));
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


    private String getDatePhone(){
        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formato = df.format(date);
        return formato;
    }

    ////////////////////////////////////////////////////////////////////////////

}
