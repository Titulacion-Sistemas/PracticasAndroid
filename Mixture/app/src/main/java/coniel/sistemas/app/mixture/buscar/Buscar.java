package coniel.sistemas.app.mixture.buscar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import coniel.sistemas.app.mixture.R;
import coniel.sistemas.app.mixture.buscar.classes.Abonado;
import coniel.sistemas.app.mixture.buscar.classes.Medidor;

public class Buscar extends Fragment {

    private int t=0;
    private static String[][] CABECERAS ={
            new String[]{"Cliente", "Nombre del Cliente", "Dirección", "Deuda", "Pendiente"},
            new String[]{"N° de Medidor", "Estado", "Cliente", "Nombre del Cliente", "Direccion"},
            new String[]{"Nombre del Cliente", "Dirección", "Cliente", "Deuda", "Pendiente"},
            new String[]{"Secuencia", "Cliente", "Nombredel Cliente", "Dirección", "Medidor", "Deuda"}
    } ;

    private Spinner spinnerBuscar;
    private ListView listaCont, listaCabecera;
    private EditText txtDato;
    private Button btnBuscar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.fragment_buscar, container, false);



        spinnerBuscar = (Spinner) rootView.findViewById(R.id.spinnerBuscar);
        spinnerBuscar.setOnItemSelectedListener(myItemSelected);
        listaCabecera = (ListView) rootView.findViewById(R.id.listaCabecera);
        listaCont = (ListView) rootView.findViewById(R.id.listaContenido);
        btnBuscar = (Button) rootView.findViewById(R.id.btnBuscarDatos);
        txtDato=(EditText) rootView.findViewById(R.id.datoBuscar);


        txtDato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                cambiarIngreso (spinnerBuscar.getSelectedItemPosition());
            }
        });

        spinnerBuscar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cambiarIngreso(position);
                getTvData().setText("");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Log.e(
                                                     "Iniciando Búsqueda",
                                                     "Dato: " + txtDato.getText() + ", por " + spinnerBuscar.getSelectedItemPosition()
                                             );
                                             int pos = spinnerBuscar.getSelectedItemPosition() + 1;
                                             if (validar(pos - 1, getTvData().getText().toString())) {

                                                 asyncBuscar asb = new asyncBuscar();
                                                 asb.execute(
                                                         pos == 1 ? "porCuenta" :
                                                                 pos == 2 ? "porMedidor" :
                                                                         pos == 3 ? "porNombre" :
                                                                                 pos == 4 ? "porGeocodigo" : "",

                                                         getTvData().getText().toString().trim().replace(" ", "_"),

                                                         (pos) + ""
                                                 );
                                             }
                                         }
                                     }
        );


        for (Fragment f:getActivity().getSupportFragmentManager().getFragments())
            try {
                Abonado[] ab = ((ContenedorBusqueda) f).getClientes();
                if (ab.length>0) rellenar(ab);
                break;
            } catch (Exception ignored) {
            }


        Log.i("Info", "creado fragment 0");
        return rootView;
    }

    private void cambiarIngreso(int position) {
        switch (spinnerBuscar.getSelectedItemPosition()) {
            case 0: {
                txtDato.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            }
            case 1: {
                txtDato.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            }
            case 2: {
                txtDato.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            }
            case 3: {
                txtDato.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            }
        }
    }

    private AdapterView.OnItemSelectedListener myItemSelected = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(parent.getContext(),
            //        "Item Seleccionado: " + parent.getItemAtPosition(position).toString(),
            //        Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };



    public void rellenar(Abonado[] clientes){
        ArrayList<HashMap<String, String>> miLista = new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String, String>> miListaCabecera = new ArrayList<HashMap<String, String>>();
        int[][] ints = new int[][]{
                new int[] {
                        R.id.textViewDato1, R.id.textViewDato2, R.id.textViewDato3, R.id.textViewDato4, R.id.textViewDato5
                },
                new int[] {
                        R.id.textViewDato1, R.id.textViewDato2, R.id.textViewDato3, R.id.textViewDato4, R.id.textViewDato5, R.id.textViewDato6
                }
        };

        /**********Display the headings************/

        HashMap<String, String> map1 = new HashMap<String, String>();

        map1.put(CABECERAS[t - 1][0], CABECERAS[t - 1][0]);
        map1.put(CABECERAS[t - 1][1], CABECERAS[t - 1][1]);
        map1.put(CABECERAS[t - 1][2], CABECERAS[t - 1][2]);
        map1.put(CABECERAS[t - 1][3], CABECERAS[t - 1][3]);
        map1.put(CABECERAS[t - 1][4], CABECERAS[t - 1][4]);
        if (t==4) map1.put(CABECERAS[t - 1][5], CABECERAS[t - 1][5]);

        miListaCabecera.add(map1);
        try {
            ListAdapter adapterTitulo = new SimpleAdapter(
                    getActivity().getApplicationContext(),
                    miListaCabecera,
                    R.layout.rows,
                    CABECERAS[t - 1],
                    t == 4 ? ints[1] : ints[0]
            );
            listaCabecera.setAdapter(adapterTitulo);
        }

        catch (Exception e) {
            Log.e("Error : ", e.toString());
        }

        /********************************************************/
        /**********Display the contents************/

        for (Abonado cliente : clientes) {
            HashMap<String, String> map2 = new HashMap<String, String>();
            switch (t) {
                case 1:
                    map2.put(CABECERAS[t - 1][0], cliente.getCuenta() + "");
                    map2.put(CABECERAS[t - 1][1], cliente.getNombre());
                    map2.put(CABECERAS[t - 1][2], cliente.getDireccion());
                    map2.put(CABECERAS[t - 1][3], cliente.getDeuda());
                    map2.put(CABECERAS[t - 1][4], cliente.getMesesAdeudado() + "");
                    break;
                case 2:
                    map2.put(CABECERAS[t - 1][0], cliente.getMedidores()[0].getNumFabrica() + "");
                    map2.put(CABECERAS[t - 1][1], cliente.getMedidores()[0].getEstado());
                    map2.put(CABECERAS[t - 1][2], cliente.getCuenta() + "");
                    map2.put(CABECERAS[t - 1][3], cliente.getNombre());
                    map2.put(CABECERAS[t - 1][4], cliente.getDireccion());
                    break;
                case 3:
                    map2.put(CABECERAS[t - 1][0], cliente.getNombre());
                    map2.put(CABECERAS[t - 1][1], cliente.getDireccion());
                    map2.put(CABECERAS[t - 1][2], cliente.getCuenta() + "");
                    map2.put(CABECERAS[t - 1][3], cliente.getDeuda());
                    map2.put(CABECERAS[t - 1][4], cliente.getMesesAdeudado() + "");
                    break;
                case 4:
                    map2.put(CABECERAS[t - 1][0], cliente.getGeocodigo());
                    map2.put(CABECERAS[t - 1][1], cliente.getCuenta() + "");
                    map2.put(CABECERAS[t - 1][2], cliente.getNombre());
                    map2.put(CABECERAS[t - 1][3], cliente.getDireccion());
                    map2.put(CABECERAS[t - 1][4], cliente.getMedidores()[0].getNumFabrica() + "");
                    map2.put(CABECERAS[t - 1][5], cliente.getDeuda());
                    break;
                default:
                    break;
            }
            miLista.add(map2);
        }

        try {
            ListAdapter adapter = new SimpleAdapter(
                    getActivity().getApplicationContext(),
                    miLista,
                    R.layout.rows,
                    CABECERAS[t - 1], t == 4 ? ints[1] : ints[0]
            );
            listaCont.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("Error : ", e.toString());
        }
        Log.i("Info", "rellenado fragment 0");
        /********************************************************/
    }

    public EditText getTvData() {
        return txtDato;
    }



    //EN SEGUNDO PLANO
    private class asyncBuscar extends AsyncTask<String, Float, String> {

        private String toast=null;
        private int tipo =0;


        public asyncBuscar() {}

        private Abonado[] clientes=null;

        public Abonado[] getClientes() {
            return clientes;
        }

        public void setClientes(Abonado[] clientes) {
            this.clientes = clientes;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            habilitarComponentes(false);
        }

        @Override
        protected String doInBackground(String... params) {


            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = "null";
            try {
                response = httpclient.execute(new HttpGet(getString(R.string.URL_SERVER)+params[0]+"/"+params[1]+"/"));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (IOException e) {
                Log.e("Error: ", e+"");
            }

            try {
                JSONObject objetos = new JSONObject(responseString);

                Log.e("Coincidencias: ", objetos.getJSONObject("coincidencias")+"");
                JSONArray coincidencias = objetos.getJSONObject("coincidencias").getJSONArray("contenido");
                Log.e("Cliente: ", objetos.getJSONObject("cliente")+"");
                JSONObject cliente = objetos.getJSONObject("cliente").getJSONObject("contenido");
                Log.e("Medidores: ", objetos.getJSONObject("medidores") + "");
                JSONArray medidores = objetos.getJSONObject("medidores").getJSONArray("contenido");


                //Llenando abonados...

                tipo = Integer.parseInt(params[2]);
                int ncoincidencias = coincidencias.length();
                setClientes(new Abonado[ncoincidencias]);
                for (int i=0; i<ncoincidencias; i++) {
                    JSONObject current = coincidencias.getJSONObject(i);
                    switch (tipo) {
                        case 1:
                            getClientes()[i] = new Abonado(
                                    null,
                                    Integer.parseInt(current.getJSONObject("3").getString("Meses").trim()),
                                    Integer.parseInt(current.getJSONObject("0").getString("Cuenta").trim()),
                                    current.getJSONObject("1").getString("Nombre").trim(), null,
                                    current.getJSONObject("2").getString("Direccion").trim(), null, null, null,
                                    current.getJSONObject("4").getString("Deuda").trim(), null
                            );
                            break;
                        case 2:
                            getClientes()[i] = new Abonado(
                                    null, 0,
                                    Integer.parseInt(current.getJSONObject("2").getString("Cuenta").trim()),
                                    current.getJSONObject("3").getString("Nombre").trim(),
                                    null, current.getJSONObject("4").getString("Direccion").trim(), null, null,
                                    null, null,
                                    new Medidor[]{
                                            new Medidor(
                                                    current.getJSONObject("0").getString("Medidor").trim(),
                                                    null, null, null, null, null, null, null, null, null, null, null,
                                                    current.getJSONObject("1").getString("Estado").trim(),
                                                    null, null
                                            )
                                    }
                            );
                            break;
                        case 3:
                            getClientes()[i] = new Abonado(
                                    null,
                                    Integer.parseInt(current.getJSONObject("3").getString("Meses").trim()),
                                    Integer.parseInt(current.getJSONObject("1").getString("Cuenta").trim()),
                                    current.getJSONObject("0").getString("Nombre").trim(),
                                    null,
                                    current.getJSONObject("2").getString("Direccion").trim(), null, null, null,
                                    current.getJSONObject("4").getString("Deuda").trim(), null
                            );
                            break;
                        case 4:
                            getClientes()[i] = new Abonado(
                                    null, 0,
                                    Integer.parseInt(current.getJSONObject("2").getString("Cuenta").trim()),
                                    current.getJSONObject("3").getString("Nombre").trim(),
                                    current.getJSONObject("0").getString("Geocodigo").trim(),
                                    current.getJSONObject("4").getString("Direccion").trim(),
                                    null, null, null,
                                    current.getJSONObject("5").getString("Deuda").trim(),
                                    new Medidor[]{
                                            new Medidor(
                                                    current.getJSONObject("1").getString("Urbanizacion").trim(),
                                                    null, null, null, null, null, null, null, null, null, null, null, null, null, null
                                            )
                                    }
                            );
                            break;
                        default:
                            cancel(true);
                            break;
                    }
                }

                //data=(SoapObject)r.getProperty(1);
                getClientes()[0].setCi(cliente.getJSONObject("0").
                        getString("Ruc/Ci").trim().replace("anyType{}", ""));
                getClientes()[0].setCuenta(Integer.parseInt(cliente.getJSONObject("1").
                        getString("Cuenta").trim().replace("anyType{}", "")));
                getClientes()[0].setNombre(cliente.getJSONObject("2").
                        getString("Nombre").trim().replace("anyType{}", ""));
                getClientes()[0].setDireccion(cliente.getJSONObject("6").
                        getString("Direccion").trim().replace("anyType{}", ""));
                getClientes()[0].setInterseccion(cliente.getJSONObject("7").
                        getString("Interseccion").trim().replace("anyType{}", ""));
                getClientes()[0].setUrbanizacion(cliente.getJSONObject("8").
                        getString("Urbanizacion").trim().replace("anyType{}", ""));
                getClientes()[0].setEstado(cliente.getJSONObject("3").
                        getString("Estado").trim().replace("anyType{}", ""));
                getClientes()[0].setGeocodigo(cliente.getJSONObject("4").
                        getString("Geocodigo").trim().replace("anyType{}", ""));
                getClientes()[0].setMesesAdeudado(Integer.parseInt(cliente.getJSONObject("9").
                        getString("Meses").trim().replace("anyType{}", "")));
                getClientes()[0].setDeuda(cliente.getJSONObject("10").
                        getString("Deuda").trim().replace("anyType{}",""));

                //data = (SoapObject)r.getProperty(2);
                ncoincidencias=medidores.length();
                Medidor[] m = new Medidor[ncoincidencias];
                for(int u=0;u<ncoincidencias;u++){
                    JSONObject current = medidores.getJSONObject(u);
                    m[u]=new Medidor(
                            current.getJSONObject("0").getString("Fabrica").trim(),
                            current.getJSONObject("1").getString("Serie").trim(),
                            current.getJSONObject("14").getString("Fases").trim(),
                            current.getJSONObject("15").getString("Hilos").trim(),
                            current.getJSONObject("13").getString("Digitos").trim(),
                            current.getJSONObject("10").getString("Lect. In.").trim(),
                            current.getJSONObject("11").getString("Lect. Des.").trim(),
                            current.getJSONObject("3").getString("Marca").trim(),
                            current.getJSONObject("12").getString("Tipo").trim(),
                            current.getJSONObject("5").getString("Tecnologia").trim(),
                            current.getJSONObject("6").getString("Tension").trim(),
                            current.getJSONObject("7").getString("Amperaje").trim(),
                            null,
                            current.getJSONObject("8").getString("Fecha In.").trim(),
                            current.getJSONObject("9").getString("Fecha Des.").trim()
                    );
                }
                getClientes()[0].setMedidores(m);
                for (Fragment f:getActivity().getSupportFragmentManager().getFragments())
                    try {
                        ((ContenedorBusqueda) f).setClientes(getClientes());
                        break;
                    } catch (Exception ignored) {
                    }

            }catch (Exception e){
                toast = "Error, No se ha podido Buscar...";
                Log.e("Error : ", e.toString());
                cancel(true);
            }



            return responseString;


        }

            @Override
        protected void onPostExecute(String str) {
            Log.e("Resultado: ", str);
            //Rellenando coincidencias
            t=tipo;
            rellenar(getClientes());
            habilitarComponentes(true);
            super.onPostExecute(str);
        }

        @Override
        protected void onCancelled(String s) {
            habilitarComponentes(true);
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            Toast t = Toast.makeText(
                    getActivity().getApplicationContext(),
                    toast,
                    Toast.LENGTH_SHORT
            );
            t.show();
            habilitarComponentes(true);
        }
    }

    public void cambiarOpcion(int pos){
        if (spinnerBuscar!= null && pos<4)
            spinnerBuscar.setSelection(pos);
    }


    private void habilitarComponentes(Boolean h){
        btnBuscar.setEnabled(h);
        spinnerBuscar.setEnabled(h);
        txtDato.setEnabled(h);
        ((LinearLayout)getActivity().findViewById(R.id.carga)).setVisibility(h?View.INVISIBLE:View.VISIBLE);
    }

    private boolean validar(int tipo, String dato){

        String t = null;
        switch (tipo){
            case 0:
                if(dato.isEmpty() || !isNumeric(dato) || dato.length()>8) {
                    t = "Error, Cuenta ingresada no válida";
                }
                break;

            case 1:
                if (dato.isEmpty() || !isNumeric(dato) || dato.length() > 11) {
                    t="Error, Número de medidor ingresado no válido.";
                }
                break;

            case 2:
                if (isNumeric(dato) || dato.length()>17) {
                    t="Error, Nombre ingresado no válido.";
                }
                break;

            case 3:
                String[] sp = dato.split("\\.");
                if (sp.length != 5
                        || (!isNumeric(sp[0]))
                        || (!isNumeric(sp[1]))
                        || (!isNumeric(sp[2]))
                        || (!isNumeric(sp[3]))
                        || (!isNumeric(sp[4])))

                t="Error, Geocódigo incorrecto.";

                else if(sp[0].length() > 2 || sp[0].length() < 1
                        || sp[1].length() > 2 || sp[1].length() < 1
                        || sp[2].length() > 2 || sp[1].length() < 1
                        || sp[3].length() > 3 || sp[1].length() < 1
                        || sp[4].length() > 7 || sp[1].length() < 1)

                t="Error, Geocódigo no válido.";
                break;

            default:
                return false;

        }
        if(t!=null) {
            (Toast.makeText(
                    getActivity().getApplicationContext(),
                    t,
                    Toast.LENGTH_LONG
            )).show();
            return false;
        }
        return true;
    }

    private boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

}

