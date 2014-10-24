package com.example.andreita.pruebai;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;




public class MiActividad extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_actividad);
        Button btnConsultar = (Button) findViewById(R.id.button2);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TareaWSConsulta tarea = new TareaWSConsulta();
                tarea.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mi_actividad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class TareaWSConsulta extends AsyncTask<String,Integer,Object> {


        protected Object doInBackground(String... params) {

            boolean resul = true;


            final String NAMESPACE = "main.miServicio";
            final String URL="http://192.168.10.21:8080/miservicio?wsdl";
            final String METHOD_NAME = "hellowithsql";
            final String SOAP_ACTION = "main.miServicio/hellowithsql";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("message",((EditText) findViewById(R.id.editText)).getText().toString());

            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.dotNet = false;

            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            Object resSoap;

            try
            {
                transporte.call(SOAP_ACTION, envelope);

                resSoap =(Object)envelope.getResponse();

                System.out.println("valor asignado a la respuesta");

                return resSoap;

                /*SoapObject ic = (SoapObject)resSoap.getProperty(0);

                System.out.print(ic);
                ((TextView) findViewById(R.id.textView)).setText(ic.toString());*/

               /* for (int i = 0; i < listaClientes.length; i++)
                {
                    SoapObject ic = (SoapObject)resSoap.getProperty(i);

                    Cliente cli = new Cliente();
                    cli.id = Integer.parseInt(ic.getProperty(0).toString());
                    cli.nombre = ic.getProperty(1).toString();
                    cli.telefono =
                            Integer.parseInt(ic.getProperty(2).toString());

                    listaClientes[i] = cli;
                }*/
            }
            catch (Exception e)
            {
                System.out.print("retorno falso");
                resSoap = null;
            }

            return resul;
        }

        protected void onPostExecute(Object result) {

            if (result != null)
            {

                SoapPrimitive resSoap = (SoapPrimitive)result;

//                SoapObject ic = (SoapObject)resSoap.getProperty(0);

                System.out.print(resSoap);
                ((TextView) findViewById(R.id.textView)).setText(resSoap.toString());

               /* //Rellenamos la lista con los nombres de los clientes
                final String[] datos = new String[listaClientes.length];

                for(int i=0; i<listaClientes.length; i++)
                    datos[i] = listaClientes[i].nombre;

                ArrayAdapter<String> adaptador =
                        new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_list_item_1, datos);

                lstClientes.setAdapter(adaptador);*/
            }
            else
            {
                ((TextView) findViewById(R.id.textView)).setText("ERROR");
//                txtResultado.setText("Error!");
            }
        }
    }
}
