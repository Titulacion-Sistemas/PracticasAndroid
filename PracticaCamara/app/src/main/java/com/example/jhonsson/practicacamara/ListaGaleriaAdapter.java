package com.example.jhonsson.practicacamara;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jhonsson on 09/11/2014.
 */
public class ListaGaleriaAdapter extends ListActivity {
    protected Activity activity;
    protected ArrayList<ItemListaGaleria> items;
    String fechaSelecionada;

    private List<String> fileList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File directorio = new File(Environment
                .getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM) + "/CONIEL/" )
                .getAbsolutePath());
        ListDir(directorio);

        File directorioImagenes = new File(Environment
                .getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM) + "/CONIEL/" )
                .getAbsolutePath());
        ListDir(directorioImagenes);
    }

    void ListDir(File f){
        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files){
            fileList.add(file.getPath());
        }

        ArrayAdapter<String> directoryList
                = new ArrayAdapter<String>(this, R.layout.fila_lista, fileList);
        setListAdapter(directoryList);
    }


}
