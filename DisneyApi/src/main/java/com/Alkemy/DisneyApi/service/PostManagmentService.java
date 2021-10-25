package com.Alkemy.DisneyApi.service;

import com.Alkemy.DisneyApi.dto.PostDTOPelicula;
import com.Alkemy.DisneyApi.dto.PostDTOPersonaje;

import java.util.List;


public interface PostManagmentService {

    List<String> listPersonaje();

    Boolean addPersonaje(PostDTOPersonaje post);

    Boolean editPersonaje(String id, PostDTOPersonaje post);

    Boolean deletePersonaje(String id);

    Object listPersonajeFull();

    Object listPelicula();

    Object addPelicula(PostDTOPelicula post);

    Object editMovie(String id, PostDTOPelicula post);

    Object deleteMovie(String id);
}
