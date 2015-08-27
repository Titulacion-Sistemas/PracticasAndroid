package coniel.sistemas.app.mixture.buscar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import coniel.sistemas.app.mixture.R;
import coniel.sistemas.app.mixture.buscar.classes.Medidor;


public class BusquedaDatosMedidor extends Fragment {

    private static String ARG_SECTION_NUMBER;
    private Medidor[] medidors=null;
    ListView listaContenido, listaCabecera;

    ArrayList<HashMap<String, String>> miLista, miListaCabecera;

    ListAdapter adapterCabecera, adapterContenido;

    HashMap<String, String> map1, map2;

    View rootView;

    public BusquedaDatosMedidor(Medidor[] medidors){
        this.setMedidors(medidors);
    }
    public BusquedaDatosMedidor(){}

    public static String getARG_SECTION_NUMBER() {
        return ARG_SECTION_NUMBER;
    }

    public static void setARG_SECTION_NUMBER(String ARG_SECTION_NUMBER) {
        BusquedaDatosMedidor.ARG_SECTION_NUMBER = ARG_SECTION_NUMBER;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_busqueda_datos_medidor, container, false);


        listaCabecera = (ListView) rootView.findViewById(R.id.listaCabeceraMedidor);
        listaContenido = (ListView) rootView.findViewById(R.id.listaContenidoMedidor);

/*        for (Fragment f:getActivity().getSupportFragmentManager().getFragments())
            try {
                setMedidors(((ContenedorBusqueda) f).getClientes()[0].getMedidores());
                rellenar(getMedidors(), rootView);
                break;
            } catch (Exception ignored) {
            }*/



        listaContenido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getMedidors() != null) {
                    rellenarMedidor(getMedidors()[position], rootView);
                    Log.i("Medidores", getMedidors().length + "");
                }
            }
        });


        Log.i("Info", "creado fragment 2");
        return rootView;
    }


    private void rellenarMedidor(Medidor m, View rootView){
        EditText et = ((EditText)rootView.findViewById(R.id.EditTextnumFabrica));
        et.setText(m.getNumFabrica(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextnumSerie)).setText(m.getNumSerie(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextMarca)).setText(m.getMarca(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextTipoMedidor)).setText(m.getTipoMedidor(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextTecnologia)).setText(m.getTecnologia(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextTension)).setText(m.getTension(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextAmperaje)).setText(m.getAmperaje(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextFases)).setText(m.getFase(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextHilos)).setText(m.getHilos(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextDigitos)).setText(m.getDigitos(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextfechaInst)).setText(m.getFechaDesinst(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextlecturaInst)).setText(m.getLecturaInst(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextfechaDesinst)).setText(m.getFechaDesinst(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextlecturaDesinst)).setText(m.getLecturaDesinst(), TextView.BufferType.EDITABLE);
        Log.i("Info", "rellenado fragment 2(Texts)");
    }

    @Override
    public void onResume() {
        for (Fragment f:getActivity().getSupportFragmentManager().getFragments())
            try {
                setMedidors(((ContenedorBusqueda) f).getClientes()[0].getMedidores());
                rellenar(getMedidors(), rootView);
                break;
            } catch (Exception ignored) {}
        super.onResume();
    }

    public void rellenar(Medidor[] medidors, View rootView) {

        miLista = new ArrayList<HashMap<String, String>>();
        miListaCabecera = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/

        map1 = new HashMap<String, String>();

        map1.put("tipo", "Tipo Med.");
        map1.put("numero", " Nro. Med");
        map1.put("marca", " Marca");
        map1.put("fecha", "Fecha Desins.");

        miListaCabecera.add(map1);

        try {

            adapterCabecera= new SimpleAdapter(getActivity().getApplicationContext(), miListaCabecera, R.layout.rowsmedidores,
                    new String[]{"tipo", "numero", "marca", "fecha"}, new int[]{
                    R.id.textViewDato1, R.id.textViewDato2, R.id.textViewDato3, R.id.textViewDato4});
            listaCabecera.setAdapter(adapterCabecera);
        }
        catch (Exception e) {
            Log.e("Error : ", e.toString());
        }

        /********************************************************/
        /**********Display the contents************/

        for (Medidor medidor : medidors) {
            map2 = new HashMap<String, String>();

            map2.put("tipo", medidor.getTipoMedidor());
            map2.put("numero", medidor.getNumFabrica());
            map2.put("marca", medidor.getMarca());
            map2.put("fecha", medidor.getFechaDesinst());
            miLista.add(map2);
        }

        try {
            adapterContenido = new SimpleAdapter(
                    getActivity().getApplicationContext(),
                    miLista,
                    R.layout.rowsmedidores,
                    new String[] {"tipo", "numero", "marca", "fecha"},
                    new int[] {
                            R.id.textViewDato1,
                            R.id.textViewDato2,
                            R.id.textViewDato3,
                            R.id.textViewDato4
                    }
            );
            listaContenido.setAdapter(adapterContenido);
        } catch (Exception e) {
            Log.e("Error : ", e.toString());
        }
        Log.i("Info", "rellenado fragment 2(Lista)");
        /********************************************************/

        for (Medidor m : medidors){
            if(m.getFechaDesinst().equals("0/00/0000")){
                rellenarMedidor(m, rootView);
                break;
            }
        }
        Float weigth = 0f;
        if (medidors.length > 2 ){
            if((medidors.length % 2) == 0)  weigth = 1.0f;
            else                            weigth = 1.5f;
        }
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, weigth);
        LinearLayout listaM = (LinearLayout)rootView.findViewById(R.id.listamedidores);
        listaM.setLayoutParams(param);
    }


    public Medidor[] getMedidors() {
        return medidors;
    }

    public void setMedidors(Medidor[] medidors) {
        this.medidors = medidors;
    }
}
