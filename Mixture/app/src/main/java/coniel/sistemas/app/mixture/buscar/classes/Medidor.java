package coniel.sistemas.app.mixture.buscar.classes;

public class Medidor {

    String numFabrica;
    String fase, hilos, digitos, lecturaInst, lecturaDesinst;
    String numSerie;
    String marca;
    String tipoMedidor;
    String tecnologia;
    String tension;
    String amperaje;
    String estado;
    String fechaInst,  fechaDesinst;

    public Medidor(){}

    public Medidor(
            String numFabrica,
            String numSerie,
            String fase,
            String hilos,
            String digitos,
            String lecturaInst,
            String lecturaDesinst,
            String marca,
            String tipoMedidor,
            String tecnologia,
            String tension,
            String amperaje,
            String estado,
            String fechaInst,
            String fechaDesinst
    ) {
        this.numFabrica=numFabrica;
        this.numSerie=numSerie;
        this.fase=fase;
        this.hilos=hilos;
        this.digitos=digitos;
        this.lecturaInst=lecturaInst;
        this.lecturaDesinst=lecturaDesinst;
        this.marca=marca;
        this.tipoMedidor=tipoMedidor;
        this.tecnologia=tecnologia;
        this.tension=tension;
        this.amperaje=amperaje;
        this.estado=estado;
        this.fechaInst=fechaInst;
        this.fechaDesinst=fechaDesinst;
    }

    public String getFechaDesinst() {
        return fechaDesinst;
    }

    public void setFechaDesinst(String fechaDesinst) {
        this.fechaDesinst = fechaDesinst;
    }

    public String getFechaInst() {
        return fechaInst;
    }

    public void setFechaInst(String fechaInst) {
        this.fechaInst = fechaInst;
    }

    public String getAmperaje() {
        return amperaje;
    }

    public void setAmperaje(String amperaje) {
        this.amperaje = amperaje;
    }

    public String getTension() {
        return tension;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getTipoMedidor() {
        return tipoMedidor;
    }

    public void setTipoMedidor(String tipoMedidor) {
        this.tipoMedidor = tipoMedidor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLecturaDesinst() {
        return lecturaDesinst;
    }

    public void setLecturaDesinst(String lecturaDesinst) {
        this.lecturaDesinst = lecturaDesinst;
    }

    public String getLecturaInst() {
        return lecturaInst;
    }

    public void setLecturaInst(String lecturaInst) {
        this.lecturaInst = lecturaInst;
    }

    public String getDigitos() {
        return digitos;
    }

    public void setDigitos(String digitos) {
        this.digitos = digitos;
    }

    public String getHilos() {
        return hilos;
    }

    public void setHilos(String hilos) {
        this.hilos = hilos;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getNumFabrica() {
        return numFabrica;
    }

    public void setNumFabrica(String numFabrica) {
        this.numFabrica = numFabrica;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
