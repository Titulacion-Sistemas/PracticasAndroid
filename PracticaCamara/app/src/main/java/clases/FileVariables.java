package clases;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jhonsson on 16/11/2014.
 */
public class FileVariables implements Serializable {

    private static final long serialVersionUID = -29238982928391L;

    private ArrayList<String> keys = new ArrayList<String>();
    private ArrayList<String> values = new ArrayList<String>();
    private int a;

    public void addVariable(String key, String value){

        if(!changeValue(key,value)){
            keys.add(key);
            values.add(value);
        }
    }

    public String getVariable(String key){
        try{
            return values.get(keys.indexOf(key));
        }catch (Exception ignored){}

        return "";
    }

    public Boolean changeValue(String key, String value){
        try{
            values.set(keys.indexOf(key), value);
            return true;
        }catch (IndexOutOfBoundsException ignored){}
        return false;
    }

    public void clearVariables(){
        keys.clear();
        values.clear();
    }

    public int getA(){
        return a;
    }

    public void setA(int newA){
        a = newA;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }
}
