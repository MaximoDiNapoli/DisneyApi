package com.Alkemy.DisneyApi.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PostDTOPersonaje {
    private String id;
    private String imagen;
    private String nombre;
    private int edad;
    private int peso;
    private String historia;
    private ArrayList<String> peliculasAsociadas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public ArrayList<String> getPeliculasAsociadas() {
        return peliculasAsociadas;
    }

    public void setPeliculasAsociadas(ArrayList<String> peliculasAsociadas) {
        this.peliculasAsociadas = peliculasAsociadas;
    }
}
