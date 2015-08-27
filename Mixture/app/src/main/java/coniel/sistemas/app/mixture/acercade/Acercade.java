package coniel.sistemas.app.mixture.acercade;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import coniel.sistemas.app.mixture.Contenedor;
import coniel.sistemas.app.mixture.R;


public class Acercade extends Fragment {

    View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_acercade, container, false);
        return rootView;
    }


    @Override
    public void onDetach() {

        ((Contenedor)getActivity()).setmTitle(getString(R.string.app_name));
        ((Contenedor)getActivity()).setmSubTitle(getString(R.string.app_Suntitle));
        ((Contenedor)getActivity()).displayView(0,null);
        super.onDetach();
        Log.i("Información", "Dtach de Fotos");

    }


}
