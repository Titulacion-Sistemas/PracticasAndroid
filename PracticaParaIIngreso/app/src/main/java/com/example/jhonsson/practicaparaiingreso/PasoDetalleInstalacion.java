package com.example.jhonsson.practicaparaiingreso;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PasoDetalleInstalacion extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_paso_detalle_instalacion, container, false);
        return view;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Se ha ejecutado el ", "  ONATTACH del detalle");
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Se ha ejecutado el ", "  ONDETACH del detalle");
    }

    public void onStart() {
        super.onStart();
        Log.i("Se ha ejecutado el ", "  ONSTART del detalle");
    }

    public void onResume() {
    super.onResume();
    Log.i("Se ha ejecutado el ","  ONRESUME del detalle");
    }

    public void onPause(){
        super.onPause();
        Log.i("Se ha ejecutado el ", "  ONPAUSE del detalle");
    }

    public void onStop(){
    //Fragment.SavedState myFragmentState = getFragmentManager().saveFragmentInstanceState(PasoDetalleInstalacion.this);
    super.onStop();

    Log.i("Se ha ejecutado el ", "  ONSTOP del detalle");
    }

    public void onDestroyView(){
        super.onDestroyView();
        Log.i("Se ha ejecutado el ", "  ONDESTROYVIEW del detalle");
    }

    public void onDestroy (){
        super.onDestroy();
        Log.i("Se ha ejecutado el ", "  ONDESTROY del detalle");
    }

}
