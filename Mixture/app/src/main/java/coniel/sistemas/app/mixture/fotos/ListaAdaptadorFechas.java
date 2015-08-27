package coniel.sistemas.app.mixture.fotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import coniel.sistemas.app.mixture.R;

public class ListaAdaptadorFechas extends ArrayAdapter<String> {
    protected Context context;
    protected List<String> itemFecha;

    public ListaAdaptadorFechas(Context context, List<String> itemFecha) {
        super(context, R.layout.fila_fecha, itemFecha);
        this.context = context;
        this.itemFecha = itemFecha;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fila_fecha, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.txtListaFechas);
        textView.setText(itemFecha.get(position));
        return rowView;
    }
}
