
package coniel.sistemas.app.mixture.fotos.classes;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;


public class SessionManager {

    private static SessionManager INSTANCE = null;
    private static String FILE_OUT = "Coniel-GAO";
    private static String LOG_TAG = "SessionManager";

    // APP STANDARD KEY VALUES
    public static final String LOGIN_KEY = "idUsuario";
    public static final String USER_KEY = "nombreUsuario";
    public static final String SESSION_KEY = "sesionSico";
    public static final String NAME_KEY = "nombreCompleto";
    private Context ctx;


    private SessionManager(Context ctx){

        this.ctx = ctx;

    }

    public static SessionManager getManager(Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = new SessionManager(ctx);
        }
        return INSTANCE;
    }

    public void write(String key, String value){
        FileVariables v = read();
        v.addVariable(key, value);
        ObjectOutput out;

        try {
            out = new ObjectOutputStream(ctx.openFileOutput(FILE_OUT, Context.MODE_PRIVATE));
            out.writeObject(v);
            out.close();
            Log.i("Guardado", "data :" + v);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileVariables read(){
        ObjectInputStream input;
        FileVariables myFileVariablesObject;
        try {
            input = new ObjectInputStream(ctx.openFileInput(FILE_OUT));
            myFileVariablesObject = (FileVariables) input.readObject();
            Log.v("serialization", "FileVariables a=" + myFileVariablesObject);
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


        write(key, value+"");

        return INSTANCE;

    }

    public SessionManager saveKey(String key, boolean value) {


        write(key, value?value + "":null);

        return INSTANCE;

    }

    public SessionManager saveKey(String key, float value) {

        write(key, value + "");

        return INSTANCE;

    }

    public SessionManager saveKey(String key, long value) {

        write(key, value + "");

        return INSTANCE;

    }

    public SessionManager saveKey(String key, String value) {


        write(key, value + "");

        return INSTANCE;

    }

    // getKey methods

    public int getIntKey(String key) {


        try {
            return Integer.parseInt(readVAlue(key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return -1;

    }

    public boolean getBooleanKey(String key) {


        return Boolean.parseBoolean(readVAlue(key));

    }

    public float getFloatKey(String key) {


        try {
            return Float.parseFloat(readVAlue(key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return -1f;
    }

    public long getLongKey(String key) {


        try {
            return Long.parseLong(readVAlue(key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        return -1;

    }

    public String getStringKey(String key) {


        return readVAlue(key);

    }
}
