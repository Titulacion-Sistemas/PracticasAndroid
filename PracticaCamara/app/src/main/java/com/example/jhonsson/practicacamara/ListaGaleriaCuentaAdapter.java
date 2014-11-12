package com.example.jhonsson.practicacamara;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class ListaGaleriaCuentaAdapter extends ArrayAdapter<String> {
    protected Context context;
    protected List<String> itemCuenta;
    //protected List<String> itemCuenta;

    public ListaGaleriaCuentaAdapter(Context context, List<String> itemCuenta) {
        super(context, R.layout.filacuentasgaleria, itemCuenta);
        this.context = context;
        this.itemCuenta = itemCuenta;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.filacuentasgaleria, parent, false);

        String [] datos = (itemCuenta.get(position)).split("/");
        TextView textView = (TextView) rowView.findViewById(R.id.txtCuentaLista);
        textView.setText(datos[datos.length-1]);

        File mifile = new File(itemCuenta.get(position));
        File[] files = mifile.listFiles();

        /*if(files!=null) {
            //Gallery galeria = (Gallery) rowView.findViewById(R.id.galleryDiaria);
            //ImageAdapter img = new ImageAdapter(context);

            for (File f : files) {
                Bitmap bMap = BitmapFactory.decodeFile(String.valueOf(f));
                img.AddImage(bMap);
            }
            galeria.setAdapter(img);
        }*/
        return rowView;
    }

    public void add(String dato){
        if (itemCuenta.indexOf(dato)==-1)
        itemCuenta.add(0,dato);
    }

}
