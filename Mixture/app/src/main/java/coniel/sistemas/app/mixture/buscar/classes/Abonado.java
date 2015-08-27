package coniel.sistemas.app.mixture.buscar.classes;

public class Abonado {

    Integer mesesAdeudado, cuenta;
    String nombre, geocodigo, direccion, interseccion, urbanizacion, estado, ci;
    String deuda;
    private Medidor[] medidores = null;

    public Abonado(
            String ci,
            Integer mesesAdeudado,
            Integer cuenta,
            String nombre,
            String geocodigo,
            String direccion,
            String interseccion,
            String urbanizacion,
            String estado,
            String deuda,
            Medidor[] medidores
    ){
        this.ci = ci;
        this.mesesAdeudado=mesesAdeudado;
        this.cuenta=cuenta;
        this.nombre=nombre;
        this.geocodigo=geocodigo;
        this.direccion=direccion;
        this.interseccion=interseccion;
        this.urbanizacion=urbanizacion;
        this.estado=estado;
        this.deuda=deuda;
        this.setMedidores(medidores);
    }


    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public Integer getMesesAdeudado() {
        return mesesAdeudado;
    }

    public void setMesesAdeudado(Integer mesesAdeudado) {
        this.mesesAdeudado = mesesAdeudado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGeocodigo() {
        return geocodigo;
    }

    public void setGeocodigo(String geocodigo) {
        this.geocodigo = geocodigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getInterseccion() {
        return interseccion;
    }

    public void setInterseccion(String interseccion) {
        this.interseccion = interseccion;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDeuda() {
        return deuda;
    }

    public void setDeuda(String deuda) {
        this.deuda = deuda;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Medidor[] getMedidores() {
        return medidores;
    }

    public void setMedidores(Medidor[] medidores) {
        this.medidores = medidores;
    }

    public void limpiarMedidores(){
        this.medidores=null;
    }
}
