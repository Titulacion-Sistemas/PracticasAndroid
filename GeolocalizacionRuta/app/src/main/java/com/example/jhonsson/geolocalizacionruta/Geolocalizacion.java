package com.example.jhonsson.geolocalizacionruta;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Geolocalizacion extends Fragment {

    View rootView;
    ArrayList<LatLng> markerPoints;
    GoogleMap map;
    TextView txtDistancia, txtDuracion;
    Button btnDraw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_geolocalizacion, container, false);
        btnDraw = (Button) rootView.findViewById(R.id.btn_draw);
        txtDistancia = (TextView) rootView.findViewById(R.id.txt_distancia);
        txtDuracion = (TextView) rootView.findViewById(R.id.txt_tiempo);

        // Initializing
        markerPoints = new ArrayList<LatLng>();

        if (savedInstanceState == null) {
            /*getFragmentManager().beginTransaction()
                    .add(R.id.map, new PlaceholderFragment())
                    .commit();*/

            // Get a handle to the Map Fragment
            map = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map)).getMap();

            LatLng machala = new LatLng(-3.2587988, -79.9589356);
            map.setMyLocationEnabled(true);


            //SetOnClickListener Mapa La Mejor Ruta
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {

                    // Already two locations
                    /*if(markerPoints.size()>1){
                        return;
                    }*/
                    if(markerPoints.size()>1){
                    markerPoints.clear();
                    map.clear();
                }

                    // Adding new item to the ArrayList
                    markerPoints.add(point);

                    // Creating MarkerOptions
                    MarkerOptions options = new MarkerOptions();

                    // Setting the position of the marker
                    options.position(point);

                    if(markerPoints.size()==1){
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }else if(markerPoints.size()==2){
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }/*else{
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    }*/

                    // Add new marker to the Google Map Android API V2
                    map.addMarker(options);

                }
            });

            map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng point) {
                    // Removes all the points from Google Map
                    map.clear();

                    // Removes all the points in the ArrayList
                    markerPoints.clear();
                    txtDuracion.setText("");
                    txtDistancia.setText("");

                }
            });

            btnDraw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Checks, whether start and end locations are captured
                    if(markerPoints.size() >= 2){
                        LatLng origin = markerPoints.get(0);
                        LatLng dest = markerPoints.get(1);

                        // Getting URL to the Google Directions API
                        String url = getDirectionsUrl(origin, dest);

                        DownloadTask downloadTask = new DownloadTask();

                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
                }
            });

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(machala)
                    .zoom(13)
                    .bearing(90)
                    .build();

            // Animate the change in camera view over 2 seconds
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                    2000, null);
        }
        return rootView;
    }

////////////////////////////////////////////////////////////
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Waypoints
       /* String waypoints = "";
        for(int i=2;i<markerPoints.size();i++){
            LatLng point  = (LatLng) markerPoints.get(i);
            if(i==2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }*/
    // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distancia = "";
            String duracion = "";
            Log.d("Valor return", String.valueOf(result.size()));

            if(result.size()<1){
                Toast.makeText(getActivity().getApplicationContext(), "No se han seleccionados puntos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if(j==0){// Get distancia from the list
                        distancia = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duracion = (String)point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }
            txtDistancia.setText("Distancia: " +distancia);
            txtDuracion.setText("Duración: " +duracion);

            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

       /* @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_contenedor_mapa, container, false);
            return rootView;
        }*/
    }

}
