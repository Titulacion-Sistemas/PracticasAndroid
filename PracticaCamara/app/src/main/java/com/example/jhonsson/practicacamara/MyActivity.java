package com.example.jhonsson.practicacamara;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MyActivity extends Activity {

    private Button boton, botonAgregar, botonEliminar;
    private EditText numCuenta;
    private Gallery galeria;
    ImageAdapter imagenes;

    //Agregue
    File mi_foto;
    private Button boton1;
    private ImageView img1;
    String cuenta;
    private List<String> fileList = new ArrayList<String>();
    ListView listaFechas ;
    TextView fechasA;
    ListaAdaptadorFechas listFechas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        ListView listaFechas = (ListView) findViewById(R.id.listFechas);
        fechasA = (TextView) findViewById(R.id.txtListaFechas);
        String fechaDia = getDatePhone();
        Log.e("Fecha", "Hoy" + fechaDia);


        try {
            File directorio = new File(Environment
                    .getExternalStoragePublicDirectory((Environment.DIRECTORY_DOWNLOADS))
                    .getAbsolutePath());
            ListDir(directorio);
            /*BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(directorio)));

            String texto = fin.toString();
            Log.e("E", "Texto:" + texto);*/

        }
        catch (Exception ex){
            Log.e("Ficheros", "Error al escribir fichero a tarjeta SD" + ex);}

    }

    void ListDir(File directorio){
        File[] files = directorio.listFiles();
        Log.i("Informacion", "valor files" +files);
        fileList.clear();
        for (File file : files){
            fileList.add(file.getPath());
        }
        //esto vale

        ArrayAdapter<String> directoryList
                    = new ArrayAdapter<String>(this, R.layout.filafecha, fileList);

        /*ArrayAdapter<String> fecha
                = new ArrayAdapter<String>(this, R.layout.filafecha, Integer.parseInt(getDatePhone()));*/

    }

    private String getDatePhone(){

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        String formato = df.format(date);

        return formato;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //======== codigo nuevo ========
        boton = (Button) findViewById(R.id.btnSiguiente);
        botonAgregar = (Button) findViewById(R.id.btnAgregar);
        botonEliminar = (Button) findViewById(R.id.btnEliminar);
        numCuenta = (EditText) findViewById(R.id.editTextCuenta);
        //img = (ImageView) findViewById(R.id.imgFotos);
        imagenes = new ImageAdapter(this);
        galeria = (Gallery) findViewById(R.id.galleryFotos);
        galeria.setAdapter(imagenes);


        //accion para el boton
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                  *//* Intent i = new Intent(MyActivity.this, MyCam.class);
                    i.putExtra("cuenta", numCuenta.getText().toString());
                    startActivity(i);
                    finish();*//*

                //Esto iba en el Onclick
                //Agregue
                cuenta = numCuenta.getText().toString();
                File directorio = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/CONIEL/" + fechaActual.toString()  + cuenta );

                //Si no existe crea la carpeta donde se guardaran las fotos
                File file = new File (directorio, getCode() + ".jpg");

                mi_foto = new File(file +"");

                Log.e("ERROR ", "Error:" + mi_foto);

                try {
                    mi_foto.createNewFile();
                }
                catch (IOException ex) {
                    Log.e("ERROR ", "Error:" + ex);
                }
                //
                Uri uri = Uri.fromFile( mi_foto );

                Log.e("ERROR ", "Error:" + uri);

                //Abre la camara para tomar la foto
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //Guarda imagen
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //Intent retornar a otra actividad
                // Intent i = new Intent(this, MyCam.class);

                //Retorna a la actividad
                startActivityForResult(cameraIntent, 1);
            }
            //Hasta aqui
        });

        //Boton Agregar

        botonAgregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        galeria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(MyActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }


    //Esto tambien agregue
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Comprobamos que la foto se a realizado

        if(requestCode == 1 && resultCode == RESULT_OK){

            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria

            Bitmap bMap = BitmapFactory.decodeFile(String.valueOf(mi_foto));
            //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/CONIEL/"

            Log.i("INFORMACION2", "Paso por aqui " + bMap );
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla

            //img.setImageBitmap(bMap);
            imagenes.AddImage(bMap);
            galeria.setAdapter(imagenes);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getCode()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode = "pic_" + date;
        Log.i("INFORMACION", "Codigo :" + photoCode);
        return photoCode;
    }*/


}
