package coniel.sistemas.app.mixture.buscar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import coniel.sistemas.app.mixture.R;
import coniel.sistemas.app.mixture.buscar.classes.Abonado;


public class BusquedaDatosAbonado extends Fragment {

    private Abonado abonado=null;
    private View rootView;

    public BusquedaDatosAbonado(Abonado abonado) {
        this.abonado=abonado;
    }

    public BusquedaDatosAbonado() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_busqueda_datos_abonado, container, false);

        Log.i("Info", "creado fragment 1");
        return rootView;
    }

    @Override
    public void onResume() {

        for (Fragment f:getActivity().getSupportFragmentManager().getFragments())
            try {
                setAbonado(((ContenedorBusqueda) f).getClientes()[0]);
                rellenar(getAbonado());
                break;
            } catch (Exception ignored) {}

        super.onResume();
    }

    public void rellenar(Abonado abonado) {
        EditText et = ((EditText)rootView.findViewById(R.id.EditTextCuenta));
        et.setText(abonado.getCuenta().toString(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextAbonado)).setText(abonado.getNombre(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextCedulaRuc)).setText(abonado.getCi(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextGeocodigo)).setText(abonado.getGeocodigo(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextDireccion)).setText(abonado.getDireccion(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextInterseccion)).setText(abonado.getInterseccion(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextUrbanizacion)).setText(abonado.getUrbanizacion(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextEstado)).setText(abonado.getEstado(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextDeuda)).setText(abonado.getDeuda(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextMesDeuda)).setText(abonado.getMesesAdeudado().toString(), TextView.BufferType.EDITABLE);
        Log.i("Info", "rellenado fragment 1");
        this.setAbonado(abonado);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public Abonado getAbonado() {
        return abonado;
    }

    public void setAbonado(Abonado abonado) {
        this.abonado = abonado;
    }
}
