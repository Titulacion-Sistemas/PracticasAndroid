package coniel.sistemas.app.mixture.fotos.classes;

import android.media.Image;

/**
 * Created by Jhonsson on 08/11/2014.
 */
public class ItemListaGaleria {

    protected Image imagen;
    protected String numeroCuenta;

    public ItemListaGaleria(String numeroCuenta, Image imagen){
        this.numeroCuenta = numeroCuenta;
        this.imagen = imagen;

    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}
