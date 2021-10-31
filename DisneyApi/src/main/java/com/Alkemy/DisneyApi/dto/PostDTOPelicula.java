package com.Alkemy.DisneyApi.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class PostDTOPelicula {
    private String imagen;
    private String titulo;
    private Date fechaDeCreacion;
    private int calificacion;
    private ArrayList<String> personajesAsociados;
    private ArrayList<String> generosAsociados;
    private String id;

    public ArrayList<String> getGenerosAsociados() {
        return generosAsociados;
    }

    public void setGenerosAsociados(ArrayList<String> generosAsociados) {
        this.generosAsociados = generosAsociados;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(Date fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public ArrayList<String> getPersonajesAsociados() {
        return personajesAsociados;
    }

    public void setPersonajesAsociados(ArrayList<String> personajesAsociados) {
        this.personajesAsociados = personajesAsociados;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
