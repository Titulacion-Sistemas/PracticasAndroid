
package classes;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;


public class SessionManager {

    private static SessionManager INSTANCE = null;
    private String fileOut = "geolocalizacion.txt";
    private static String LOG_TAG = "SessionManager";

    // APP STANDARD KEY VALUES
    public static final String LOGIN_KEY = "idUsuario";
    public static final String USER_KEY = "nombreUsuario";
    public static final String SESSION_KEY = "sesionSico";
    public static final String NAME_KEY = "nombreCompleto";


    private SessionManager( File f){

        this.fileOut =f+File.separator+this.fileOut;
    }

    public static SessionManager getManager( File f) {
        INSTANCE = new SessionManager(f);
        return INSTANCE;
    }

    public void write(String key, String value){
        FileVariables v = read();
        v.addVariable(key, value);
        ObjectOutput out;
        File myf =new File(fileOut);
        try {
            out = new ObjectOutputStream(new FileOutputStream((myf).getPath()));
            out.writeObject(v);
            out.close();
            Log.i("Guardado","data :"+ v);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileVariables read(){
        ObjectInputStream input;
        FileVariables myFileVariablesObject;
        try {
            input = new ObjectInputStream(new FileInputStream(new File(fileOut)));
            myFileVariablesObject = (FileVariables) input.readObject();
            Log.v("serialization","FileVariables a="+myFileVariablesObject);
            input.close();
            return myFileVariablesObject;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new FileVariables();
    }

    private String readVAlue(String key) {

        Log.w("Recuperando de archivo", read().getVariable(key));
        return read().getVariable(key);

    }

    public SessionManager saveKey(String key, int value) {

        checkStandarKey(key);

        write(key, value+"");

        return INSTANCE;

    }

    public SessionManager saveKey(String key, boolean value) {

        checkStandarKey(key);

        write(key, value?value + "":null);

        return INSTANCE;

    }

    public SessionManager saveKey(String key, float value) {

        checkStandarKey(key);

        write(key, value + "");

        return INSTANCE;

    }

    public SessionManager saveKey(String key, long value) {

        checkStandarKey(key);

        write(key, value + "");

        return INSTANCE;

    }

    public SessionManager saveKey(String key, String value) {

        checkStandarKey(key);

        write(key, value + "");

        return INSTANCE;

    }

    // getKey methods

    public int getIntKey(String key) {

        checkStandarKey(key);

        try {
            return Integer.parseInt(readVAlue(key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return -1;

    }

    public boolean getBooleanKey(String key) {

        checkStandarKey(key);

        return Boolean.parseBoolean(readVAlue(key));

    }

    public float getFloatKey(String key) {

        checkStandarKey(key);

        try {
            return Float.parseFloat(readVAlue(key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return -1f;
    }

    public long getLongKey(String key) {

        checkStandarKey(key);

        try {
            return Long.parseLong(readVAlue(key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        return -1;

    }

    public String getStringKey(String key) {

        checkStandarKey(key);

        return readVAlue(key);

    }

    private void checkStandarKey(String key) {
        if (!key.equals(LOGIN_KEY) &&
                !key.equals(SESSION_KEY) &&
                !key.equals(USER_KEY) &&
                !key.equals(NAME_KEY)) {
            Log.w(LOG_TAG, "The passed key is not part of the standars key-set. Consider use another one of the standar set");
        }
    }
}
