package com.example.jhonsson.practicaparaiingreso;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jhonsson on 09/12/2014.
 */
public class ListaPasosAdapter extends ArrayAdapter<Pasos> {

    Activity context;
    ArrayList<Pasos> listaPasos;

    // Le pasamos al constructor el contecto y la lista de contactos
    public ListaPasosAdapter(Activity context, ArrayList<Pasos> listaPasos) {
        super(context, R.layout.activity_lista_pasos, listaPasos);
        this.context = context;
        this.listaPasos = listaPasos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Rescatamos cada item del listview y lo inflamos con nuestro layout
        View item = convertView;
        item = context.getLayoutInflater().inflate(R.layout.item_lista_pasos, null);

        Pasos p = listaPasos.get(position);

        // Definimos los elementos que tiene nuestro layout
        ImageView img = (ImageView)item.findViewById(R.id.imagen);
        TextView nombre = (TextView) item.findViewById(R.id.nombre);
        TextView descripcion = (TextView) item.findViewById(R.id.descripcion);

        img.setImageDrawable((p.getImagen()));
        //img.setImageResource(p.getImagen());
        nombre.setText(p.getNombre());
        descripcion.setText(p.getDescripcion());


        return (item);
    }


}
