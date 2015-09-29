package coniel.sistemas.app.mixture.fotos;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import coniel.sistemas.app.mixture.Contenedor;
import coniel.sistemas.app.mixture.R;
import coniel.sistemas.app.mixture.buscar.ContenedorBusqueda;
import coniel.sistemas.app.mixture.buscar.classes.Abonado;
import coniel.sistemas.app.mixture.buscar.classes.Medidor;
import coniel.sistemas.app.mixture.classes.CoordinateConversion;
import coniel.sistemas.app.mixture.classes.coordUTM;
import coniel.sistemas.app.mixture.fotos.classes.GuardarFotos;
import coniel.sistemas.app.mixture.fotos.classes.MyLocation;


public class CapturarFotos extends Fragment {

    private List<String> fileList = new ArrayList<String>();
    ListView listaGaleriaCuenta;
    Button boton;
    File mi_foto;
    File directorio , directorioc;
    ArrayList<Integer> aEliminar = new ArrayList<Integer>();
    View rootView;
    String dato;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("Info", "Pasando por onCreate de CapturarFotos");

        rootView = inflater.inflate(R.layout.fragment_capturar_fotos, container, false);

        //Captura de datos enviados de la actividad anterior
       final String fecha = getArguments().getString("fecha"); //getIntent().getStringExtra("fecha");


        listaGaleriaCuenta = (ListView)rootView.findViewById(R.id.lvCuentasGaleria);

        boton = (Button) rootView.findViewById(R.id.btnSiguiente);


        directorio = new File(Environment
                .getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM) + "/CONIEL/" +  fecha + "/")
                .getAbsolutePath());

        ListDir(directorio);

        //accion para el boton
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView txtNumCuenta = (TextView) rootView.findViewById(R.id.editTextCuenta);
                dato = txtNumCuenta.getText()+"";
                if(! txtNumCuenta.getText().toString().equals("")) {
                    // Agregue
                    directorioc = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/CONIEL/"+ fecha + "/" + txtNumCuenta.getText() + "/");
                    directorioc.mkdirs();
                    //Si no existe crea la carpeta donde se guardaran las fotos
                    File file = new File(directorioc, getCode() + ".jpg");

                    mi_foto = new File(file + "");

                    Uri uri = Uri.fromFile(mi_foto);

                    Log.e("ERROR ", "Uri:" + uri);

                    //Abre la camara para tomar la foto
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    txtNumCuenta.setText("");

                    //Guarda imagen
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                    //Retorna a la actividad
                    startActivityForResult(cameraIntent, 0);

                }
                else{
                    Toast mensaje =
                            Toast.makeText(getActivity().getApplicationContext(),
                                    " Ingrese una Cuenta Por Favor ", Toast.LENGTH_SHORT);

                    mensaje.show();
                }
            }
        });

        listaGaleriaCuenta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.txtCuentaLista);

                ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();

                /*Intent i = new Intent(CapturarFotos.this , Galeria.class);
                i.putExtra("ruta", lg.itemCuenta.get(position));
                startActivity(i);*/

                Log.i("Seleccion...", "Se seleccionó el elemento " + lg.itemCuenta.get(position) + " en la posicion " + position);
                Bundle parametro = new Bundle();
                parametro.putString("ruta", lg.itemCuenta.get(position));
                parametro.putString("fecha", getArguments().getString("fecha"));
                ((Contenedor) getActivity()).displayView(12, parametro);
              //  finish();

            }
        });

        listaGaleriaCuenta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Button btnBorrar = (Button) rootView.findViewById(R.id.btnBorrar);
                try {
                    Log.i("Child...", "" + listaGaleriaCuenta.getChildAt(position) + "");
                    if (aEliminar.indexOf((Object)position)==-1) {
                        aEliminar.add(position);
                        listaGaleriaCuenta.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                        btnBorrar.setVisibility(View.VISIBLE);
                    }else{
                        aEliminar.remove((Object)position);
                        listaGaleriaCuenta.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                        if (aEliminar.size()==0){
                            btnBorrar.setVisibility(View.INVISIBLE);
                        }
                    }
                }catch (Exception e){
                    Log.e("Error al Seleccionar", "" + e.toString());
                    aEliminar=new ArrayList<Integer>();
                    ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                    listaGaleriaCuenta.setAdapter(lg);
                    btnBorrar.setVisibility(View.INVISIBLE);
                    getToast("Error al Seleccionar...");
                }
                mostrarBotonEliminar();
                return true;
            }
        });

        ((Button) rootView.findViewById(R.id.btnBorrar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarDir();
            }
        });
        return rootView;
    }

    ////////

    private void eliminarDir() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminar");
        builder.setMessage("¿Esta seguro que desea elimiar el directorio y todo su contenido?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                    for (Integer aItemaEliminar : aEliminar) {
                        File f = new File(
                            directorio.getPath()+
                            "/"+
                            ((TextView)(listaGaleriaCuenta.getChildAt(aItemaEliminar)).findViewById(R.id.txtCuentaLista)).getText().toString()
                        );

                        for(File foto:f.listFiles())
                            Log.i("Borro el Archivo: " + foto.getPath() + "?", foto.delete() + "");

                        Log.i("Borro el Directorio: " + f.getPath() + "?", f.delete() + "");
                        lg.remove(f.getPath());
                    }

                    listaGaleriaCuenta.setAdapter(lg);
                    //((ListaGaleriaCuentaAdapter)listaGaleriaCuenta.getAdapter()).notifyDataSetChanged();
                    aEliminar=new ArrayList<Integer>();
                    mostrarBotonEliminar();
                } catch (Exception ex) {
                    Log.e("ERROR ", "catch :" + ex);
                }

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Code that is executed when clicking NO
                ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                listaGaleriaCuenta.setAdapter(lg);
                aEliminar=new ArrayList<Integer>();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Esto tambien agregue
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprobamos que la foto se a realizado
        final String uri = getString(R.string.URL_SERVER)+ "geoandroid/" + dato + "/";

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            Log.e("ERROR ", "Error:" + mi_foto);

            try {
                mi_foto.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                    @Override
                    public void gotLocation(Location location){
                        //Got the location!
                        if(location!=null) {

                            Log.i("Ultima localización conocida:", "Latitud: " + location.getLatitude());
                            Log.i("Ultima localización conocida:", "Longitud: " + location.getLongitude());

                            CoordinateConversion transfor = new CoordinateConversion();
                            coordUTM cUtm = transfor.latLon2UTM(location.getLatitude(), location.getLongitude());

                            GuardarFotos.getManager(directorioc)
                                    .saveKey("Latitud", location.getLatitude() + "")
                                    .saveKey("Longitud", location.getLongitude() + "")
                                    .saveKey("Precision", location.getAccuracy() + "")
                                    .saveKey("LongZone", cUtm.getLongZone())
                                    .saveKey("LatZone", cUtm.getLatZone())
                                    .saveKey("Easting", cUtm.getEasting() + "")
                                    .saveKey("Norting", cUtm.getNorting()+"");
                            String ur = uri.replace(" ", "_");
                            // fotmato de envio :  dato, lat, long, precision,  latzone, longzone, aleste, alnorte
                            ur = ur
                                    + location.getLatitude()
                                    + "/" + location.getLongitude()
                                    + "/" + location.getAccuracy()
                                    + "/" + cUtm.getLatZone()
                                    + "/" + cUtm.getLongZone()
                                    + "/" + cUtm.getEasting()
                                    + "/" + cUtm.getNorting() + "/";
                            Log.e("URL consultada",ur);
                            asyncGuardarPosicion guardarpos = new asyncGuardarPosicion();
                            guardarpos.execute(new String[]{ur});

                        }else{
                            getToast("Error, Ubicación NO detectada");
                            GuardarFotos.getManager(directorioc)
                                    .saveKey("Easting", "Por favor, actualice")
                                    .saveKey("Norting", "datos de ubicación...")
                                    .saveKey("Precision", "-");
                        }

                    }
                };
                MyLocation myLocation = new MyLocation();
                myLocation.getLocation(getActivity().getApplicationContext(), locationResult);
                getToast("Esperando ubicación...");

            }catch (Exception ignored){
                ignored.printStackTrace();
            }

            ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
            lg.add(directorioc.getPath());

            listaGaleriaCuenta.setAdapter(lg);

        }
    }

    private void getToast(String s){
        try {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    s,
                    Toast.LENGTH_SHORT
            ).show();
        }catch (Exception e){}
    }


    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String date = dateFormat.format(new Date());
        Log.i("INFORMACION", "Codigo :" + date);
        return date.replace(":","_");
    }

    /////////////////////////////////

    void ListDir(File directorio){
        File[] files = ordenarPrFecha(directorio.listFiles());
        Log.i("Informacion", "valor files" + files);
        fileList.clear();
        for (File file : files) {
            fileList.add(file.getPath());
        }
        if(fileList != null) {
            listaGaleriaCuenta.setAdapter(new ListaGaleriaCuentaAdapter(getActivity().getApplicationContext(), fileList));
        }
    }

    private File[] ordenarPrFecha(File[] sortedByDate) {
        if (sortedByDate != null && sortedByDate.length > 1) {
            Arrays.sort(sortedByDate, new Comparator() {
                public int compare(final Object o1, final Object o2) {
                    return new Long(((File) o2).lastModified()).compareTo
                            (new Long(((File) o1).lastModified()));
                }
            });
            return sortedByDate;
        }
        return sortedByDate;
    }

    @Override
    public void onDetach() {


        ((Contenedor)getActivity()).setmTitle(getString(R.string.fotos));
        ((Contenedor)getActivity()).setmSubTitle(getString(R.string.fotos_subtitle));

        super.onDetach();
    }


    private void mostrarBotonEliminar() {
        if (aEliminar.size()>0)
            ((Button) rootView.findViewById(R.id.btnBorrar)).setVisibility(View.VISIBLE);
        else
            ((Button) rootView.findViewById(R.id.btnBorrar)).setVisibility(View.INVISIBLE);
    }


    //EN SEGUNDO PLANO
    private class asyncGuardarPosicion extends AsyncTask<String, Float, String> {

        private String toast="null";


        public asyncGuardarPosicion() {}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = "null";

            try {
                response = httpclient.execute(new HttpGet(params[0]));
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


            return responseString;

        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }


    }

}
