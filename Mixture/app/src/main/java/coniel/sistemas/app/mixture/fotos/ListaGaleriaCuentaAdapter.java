package coniel.sistemas.app.mixture.fotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import coniel.sistemas.app.mixture.R;

public class ListaGaleriaCuentaAdapter extends ArrayAdapter<String> {
    protected Context context;
    protected List<String> itemCuenta;
    //protected List<String> itemCuenta;

    public ListaGaleriaCuentaAdapter(Context context, List<String> itemCuenta) {
        super(context, R.layout.fila_cuentas_galeria, itemCuenta);
        this.context = context;
        this.itemCuenta = itemCuenta;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fila_cuentas_galeria, parent, false);

        String[] datos = (itemCuenta.get(position)).split("/");
        TextView textView = (TextView) rowView.findViewById(R.id.txtCuentaLista);
        textView.setText(datos[datos.length-1]);

        return rowView;
    }

    public void add(String dato){
        if (itemCuenta.indexOf(dato)==-1)
        itemCuenta.add(0,dato);
    }

    public void remove(String dato){
        if (itemCuenta.indexOf(dato)!=-1)
            itemCuenta.remove(dato);
    }

}
