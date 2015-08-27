package coniel.sistemas.app.mixture.fotos;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Gallery;
import android.widget.ListView;

import coniel.sistemas.app.mixture.R;


public class ListaGaleriaDiaria extends Activity {

    ImageAdapter imagenes;
    private Gallery galeriaLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lista_galeria_diaria);

        ListView lv = (ListView) findViewById(R.id.listGaleriaDiaria);
    }



    /*public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_galeria_diaria, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
