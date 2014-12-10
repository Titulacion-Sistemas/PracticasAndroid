package com.example.jhonsson.practicaparaiingreso;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class PasoDatosAbonado extends Fragment {

    private String [] valores = {"Datos Abonados", "Datos Instalación"};
    private String [] datosAbonados = new String[2];
    EditText Cuenta, Nombre;
    // private OnArticuloSelectedListener listener;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_paso_datos_abonado, container, false);
        Cuenta = (EditText) view.findViewById(R.id.txtCuenta);
        Nombre = (EditText) view.findViewById(R.id.txtNombre);

        //Guardar Sesion para evitar cierre
        SessionManager s = SessionManager.getManager(getActivity().getApplicationContext());
        Cuenta.setText(s.getStringKey("CUENTA"));
        Nombre.setText(s.getStringKey("NOMBRE"));

        return view;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Se ha ejecutado el ", "  ONATTACH");
        }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Se ha ejecutado el ", "  ONDETACH");
    }

    public void onStart() {
        super.onStart();
        Log.i("Se ha ejecutado el ", "  ONSTART ");
    }

    public void onResume() {
        super.onResume();
        Log.i("Se ha ejecutado el ","  ONRESUME ");
    }

    public void onPause(){
        super.onPause();
        Log.i("Se ha ejecutado el ", "  ONPAUSE ");
    }

    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");
        //Guardar Sesion para evitar cierre
        SessionManager.getManager(getActivity().getApplicationContext())
                .saveKey("Coniel-GAO", true)
                .saveKey("CUENTA", Cuenta.getText().toString())
                .saveKey("NOMBRE", Nombre.getText().toString());
                //.saveKey(SessionManager.SESSION_KEY, datos_de_Sesion[3])
               // .saveKey(SessionManager.NAME_KEY, datos_de_Sesion[4]);
    }

    public void onDestroyView(){
        super.onDestroyView();
        Log.i("Se ha ejecutado el ", "  ONDESTROYVIEW");
    }

    public void onDestroy (){
        super.onDestroy();
        Log.i("Se ha ejecutado el ", "  ONDESTROY");
    }

}
