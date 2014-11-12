package com.example.jhonsson.practicacamara;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListaAdaptadorFechas extends ArrayAdapter<String> {
    protected Context context;
    protected List<String> itemFecha;

    public ListaAdaptadorFechas(Context context, List<String> itemFecha) {
        super(context, R.layout.filafecha, itemFecha);
        this.context = context;
        this.itemFecha = itemFecha;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.filafecha, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.txtListaFechas);
        textView.setText(itemFecha.get(position));
        return rowView;
    }
}
