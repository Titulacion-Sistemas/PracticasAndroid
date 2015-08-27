package coniel.sistemas.app.mixture;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import coniel.sistemas.app.mixture.acercade.Acercade;
import coniel.sistemas.app.mixture.buscar.ContenedorBusqueda;
import coniel.sistemas.app.mixture.fotos.CapturarFotos;
import coniel.sistemas.app.mixture.fotos.Fotos;
import coniel.sistemas.app.mixture.fotos.Galeria;


public class Contenedor extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;
    private CharSequence mSubTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        setmTitle(getString(R.string.app_name));
        setmSubTitle(getString(R.string.app_Suntitle));

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //eventos de botones en principal

        ((Button)findViewById(R.id.fotos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onNavigationDrawerItemSelected(1);

            }
        });

        ((Button)findViewById(R.id.buscarcliente)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onNavigationDrawerItemSelected(2);

            }
        });

        ((Button)findViewById(R.id.salir)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onNavigationDrawerItemSelected(3);

            }
        });


    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Log.i("Seleccion de item lateral", ""+ position);
        Bundle args = new Bundle();
        args.putInt("section_number", position);
        displayView(position, args);
    }

    public void displayView(int position, Bundle arguments) {

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        }
        if (bar != null) {
            bar.removeAllTabs();
        }
        //bar.setTitle(R.string.title_activity_contenedor);
        //bar.setSubtitle(" Gestion de Actividades Operativas ");

        // update the main content by replacing fragments
        Fragment fragment = null;

        switch (position) {

            default:
                setmTitle(getString(R.string.app_name));
                setmSubTitle(getString(R.string.app_Suntitle));
                break;
            case 0:
                setmTitle(getString(R.string.app_name));
                setmSubTitle(getString(R.string.app_Suntitle));
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case 1:
                fragment = new Fotos();
                setmTitle(getString(R.string.fotos));
                setmSubTitle(getString(R.string.fotos_subtitle));
                break;
            case 2:
                fragment = new ContenedorBusqueda();
                setmTitle(getString(R.string.datos_de_abonado));
                setmSubTitle("Buscar datos del cliente");
                break;
            case 3:
                System.exit(0);
                break;

            case 11:
                fragment = new CapturarFotos();
                setmTitle(arguments.getString("fecha"));
                setmSubTitle(getString(R.string.actividad_nueva_subtitle));
                break;

            case 12:
                fragment = new Galeria();
                String[] datos = arguments.getString("ruta").split("/");
                setmTitle(datos[datos.length-1]);
                setmSubTitle("Fotos de actividad");
                break;

            case 40:
                fragment = new Acercade();
                setmTitle(getString(R.string.acercade));
                setmSubTitle(getString(R.string.app_name));
                break;

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(arguments != null)
                Log.e("Parametro", "param:" + arguments);
                fragment.setArguments(arguments);
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            Log.i("Jhonsson", "Contenedor creado");
        }

        else {
            // error in creating fragment
            Log.e("Andrea", "Contenedor - Error cuando se creo el fragment");
        }

        restoreActionBar();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                setmTitle(getString(R.string.inicio));
                break;
            case 2:
                setmTitle(getString(R.string.fotos));
                break;
            case 3:
                setmTitle(getString(R.string.datos_de_abonado));
                break;
            case 4:
                setmTitle(getString(R.string.salir));
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getmTitle());
        actionBar.setSubtitle(getmSubTitle());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.principal, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.acercede:
                onNavigationDrawerItemSelected(40);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    public CharSequence getmTitle() {
        return mTitle;
    }

    public void setmTitle(CharSequence mTitle) {
        this.mTitle = mTitle;
    }

    public CharSequence getmSubTitle() {
        return mSubTitle;
    }

    public void setmSubTitle(CharSequence mSubTitle) {
        this.mSubTitle = mSubTitle;
    }
}
