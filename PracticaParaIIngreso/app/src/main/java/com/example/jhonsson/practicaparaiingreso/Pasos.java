package com.example.jhonsson.practicaparaiingreso;

import android.graphics.drawable.Drawable;

/**
 * Created by Andreita on 09/12/2014.
 */
public class Pasos {
    Drawable imagen;
    String nombre, descripcion;

    public Pasos(Drawable imagen, String nombre, String descripcion) {
        super();
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
