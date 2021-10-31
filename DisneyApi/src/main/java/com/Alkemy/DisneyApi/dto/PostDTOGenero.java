package com.Alkemy.DisneyApi.dto;

import java.util.ArrayList;

public class PostDTOGenero {
    private String id;
    private String nombre;
    private String imagen;
    private ArrayList<String> peliculasAsociadasGenero;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ArrayList<String> getPeliculasAsociadasGenero() {
        return peliculasAsociadasGenero;
    }

    public void setPeliculasAsociadasGenero(ArrayList<String> peliculasAsociadasGenero) {
        this.peliculasAsociadasGenero = peliculasAsociadasGenero;
    }
}
