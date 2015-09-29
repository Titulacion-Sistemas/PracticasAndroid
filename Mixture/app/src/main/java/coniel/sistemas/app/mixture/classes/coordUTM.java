package coniel.sistemas.app.mixture.classes;

/**
 * Created by Jhonsson on 17/09/2015.
 */
public class coordUTM {

    public coordUTM(String latz, String longz, double east, double nort){
        this.latZone=latz;
        this.longZone=longz;
        this.easting=east;
        this.norting=nort;
    }

    public coordUTM() {
        this.latZone="";
        this.longZone="";
        this.easting=0;
        this.norting=0;
    }

    private String latZone;
    private String longZone;
    private double easting;
    private double norting;


    public String getLatZone() {
        return latZone;
    }

    public void setLatZone(String latZone) {
        this.latZone = latZone;
    }

    public String getLongZone() {
        return longZone;
    }

    public void setLongZone(String longZone) {
        this.longZone = longZone;
    }

    public double getEasting() {
        return easting;
    }

    public void setEasting(double easting) {
        this.easting = easting;
    }

    public double getNorting() {
        return norting;
    }

    public void setNorting(double norting) {
        this.norting = norting;
    }
}
