package com.Alkemy.DisneyApi.service;

import com.Alkemy.DisneyApi.dto.PostDTOPelicula;
import com.Alkemy.DisneyApi.dto.PostDTOPersonaje;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface PostManagmentService {

    List<? extends Object> listPersonaje(String id, int edad, String nombre) throws ExecutionException, InterruptedException;

    Boolean addPersonaje(PostDTOPersonaje post);

    Boolean editPersonaje(String id, PostDTOPersonaje post) throws ExecutionException, InterruptedException;

    Boolean deletePersonaje(String id);

    Object listPersonajeFull();

    Object listPelicula(String name,String genre,String order) throws ExecutionException, InterruptedException;

    Object addPelicula(PostDTOPelicula post);

    Object editMovie(String id, PostDTOPelicula post) throws ExecutionException, InterruptedException;

    Object deleteMovie(String id);

    Object detalleCharacter(String id) throws ExecutionException, InterruptedException;

    Object detallesMovie(String id) throws ExecutionException, InterruptedException;

    Object listGeneros();


}
