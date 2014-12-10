package com.example.jhonsson.practicaparaiingreso;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class Ingreso extends Activity implements ListaPasos.OnPasoSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        ListaPasos listados = new ListaPasos();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(android.R.id.content, listados)
                .commit();

    }

    public void OnPasoSelected(Fragment id) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, id);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
