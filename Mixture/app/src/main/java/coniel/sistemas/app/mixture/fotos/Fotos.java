package coniel.sistemas.app.mixture.fotos;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import coniel.sistemas.app.mixture.Contenedor;
import coniel.sistemas.app.mixture.R;



public class Fotos extends Fragment {
    private List<String> fileList = new ArrayList<String>();
    private List<String> date = new ArrayList<String>();
    ListView lista;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.fragment_fotos, container, false);

        File hoy = new File(Environment
                .getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM) + "/CONIEL/" + getDatePhone() + "/")
                .getAbsolutePath());
        hoy.mkdirs();

        File directorio = new File(Environment
                .getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM) + "/CONIEL/")
                .getAbsolutePath());
        ListDir(directorio);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.txtListaFechas);
                Log.i("Seleccion...", "Se seleccionó el elemento " + textView.getText() + " en la posicion " + position);
                //Intent i = new Intent(Fotos.this , CapturarFotos.class);
               // i.putExtra("fecha", textView.getText());
                //startActivity(i);
                try {
                   /* Fragment fragment = new Fragment();*/
                    Bundle parametro = new Bundle();
                    parametro.putString("fecha", textView.getText().toString());
                    ((Contenedor) getActivity()).displayView(11, parametro);
                }catch (Exception ignored){}

            }
        });
        //Log.e("Ficheros", "Error al escribir fichero a tarjeta SD" + ex);
        ////////////////////////////////////////////////////////////////////////////
        return rootView;
    }

/////////////////////


    /////////////////////////////////////////////////////////////////////////////
    void ListDir(File directorio){
        File[] files = ordenarPrFecha(directorio.listFiles());
        Log.i("Informacion", "valor files" + files);
        fileList.clear();
        for (File file : files) {
            String[] path = (file.getPath()).split("/");
            fileList.add(path[path.length-1]);
        }
        if(fileList.toString() != null) {
            ListaAdaptadorFechas adapter = new ListaAdaptadorFechas(getActivity().getApplicationContext(), fileList);
            lista = (ListView) rootView.findViewById(R.id.listadeFechas);
            lista.setAdapter(adapter);
            //lfecha.add(String.valueOf(lista));
           // setListAdapter(adapterSellos);
            Log.e("Informacion SI", "adaptador " + adapter);
        }
        else {
            date.add(getDatePhone());
            ListaAdaptadorFechas adapter = new ListaAdaptadorFechas(getActivity().getApplicationContext(), date);
           // setListAdapter(adapterSellos);
            Log.e("Informacion NO", "adaptador " + adapter);
        }
    }

    private File[] ordenarPrFecha(File[] sortedByDate) {
        if (sortedByDate != null && sortedByDate.length > 1) {
            Arrays.sort(sortedByDate, new Comparator<File>() {
                public int compare(final File o1, final File o2) {
                    String path[] = o1.getPath().split("/");
                    path = path[path.length - 1].split("-");
                    int f1 = 0;
                    for (String p : path)
                        f1 = f1 + Integer.parseInt(p);

                    path = o2.getPath().split("/");
                    path = path[path.length - 1].split("-");
                    int f2 = 0;
                    for (String p : path)
                        f2 = f2 + Integer.parseInt(p);
                    return f2 > f1 ? +1 : -1;
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


    @Override
    public void onDetach() {

        ((Contenedor)getActivity()).setmTitle(getString(R.string.app_name));
        ((Contenedor)getActivity()).setmSubTitle(getString(R.string.app_Suntitle));
        ((Contenedor)getActivity()).displayView(0,null);
        super.onDetach();
        Log.i("Información", "Dtach de Fotos");
    }


}
