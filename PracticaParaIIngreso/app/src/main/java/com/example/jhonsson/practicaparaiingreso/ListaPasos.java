package com.example.jhonsson.practicaparaiingreso;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class ListaPasos extends Fragment {

    private Fragment [] fragmentos = new Fragment[2];
    private OnPasoSelectedListener listener;
    String c,n;
    ListView listView;
    ArrayList<Pasos> listaPasos;

    // Creamos un adapter personalizado
    ListaPasosAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_lista_pasos, container, false);
        fragmentos [0] = new PasoDatosAbonado();
        fragmentos [1] = new PasoDetalleInstalacion();

       listView = (ListView) v.findViewById(R.id.lista);
        //setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, valores));
        listaPasos = new ArrayList<Pasos>();
        // Al adapter personalizado le pasamos el contexto y la lista que contiene
        // Añadimos el adapter al listview
        adapter = new ListaPasosAdapter(getActivity(), listaPasos);
        listView.setAdapter(adapter);
        //setListAdapter(adapter);
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_action_person), "Datos de Abonado", "Digitar o Consultar datos requeridos"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_action_settings), "Detalle de Instalación", "Seleccionar o Digitar el detalle de la instalación"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_action_settings), "Digitar Medidores", "Seleccionar o Digitar un medidor"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_action_settings), "Material utilizado", "Digitar el material utilizado"));

        return v;
    }

  /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }*/

    public interface OnPasoSelectedListener {
        public void OnPasoSelected (Fragment id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnPasoSelectedListener) activity;
        } catch (ClassCastException e) {}
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        int ide = (int)id;
        listener.OnPasoSelected(fragmentos[position]);
        Log.e("Item seleccionado", String.valueOf(+ide));
    }

}
