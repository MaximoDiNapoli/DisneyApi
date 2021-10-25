package com.Alkemy.DisneyApi.service.impl;

import com.Alkemy.DisneyApi.dto.PostDTOPelicula;
import com.Alkemy.DisneyApi.dto.PostDTOPersonaje;
import com.Alkemy.DisneyApi.firebase.FirebaseInitializer;
import com.Alkemy.DisneyApi.service.PostManagmentService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostManagmentServiceImpl implements PostManagmentService {

    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<String> listPersonaje() {
        List<PostDTOPersonaje> response = new ArrayList<>();
        PostDTOPersonaje post;
        List<String> nombresYFotos = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getPersonajes().get();

        try {
            for (DocumentSnapshot doc: querySnapshotApiFuture.get().getDocuments()){
                post = doc.toObject(PostDTOPersonaje.class);
                post.setId(doc.getId());
                nombresYFotos.add("Nombre: " + post.getNombre()+", Foto: " +post.getImagen());
                response.add(post);
            }
        } catch (Exception e) {
            return null;

        }
        return nombresYFotos;
    }

    @Override
    public Boolean addPersonaje(PostDTOPersonaje post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("imagen", post.getImagen());
        docData.put("nombre", post.getNombre());
        docData.put("edad", post.getEdad());
        docData.put("historia", post.getHistoria());
        docData.put("peliculasAsociadas", post.getPeliculasAsociadas());

        CollectionReference Personajes = getPersonajes();
        ApiFuture<WriteResult> writeResultApiFuture = Personajes.document().create(docData);

        try {
            if (null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean editPersonaje(String id, PostDTOPersonaje post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("imagen", post.getImagen());
        docData.put("nombre", post.getNombre());
        docData.put("edad", post.getEdad());
        docData.put("historia", post.getHistoria());
        docData.put("peliculasAsociadas", post.getPeliculasAsociadas());
        ApiFuture<WriteResult> writeResultApiFuture = getPersonajes().document(id).set(docData);

        try {
            if (null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean deletePersonaje(String id) {
        ApiFuture<WriteResult> writeResultApiFuture = getPersonajes().document(id).delete();
        try {
            if (null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Object listPersonajeFull() {
        List<PostDTOPersonaje> response = new ArrayList<>();
        PostDTOPersonaje post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getPersonajes().get();

        try {
            for (DocumentSnapshot doc: querySnapshotApiFuture.get().getDocuments()){
                post = doc.toObject(PostDTOPersonaje.class);
                post.setId(doc.getId());
                response.add(post);
            }
        } catch (Exception e) {
            return null;

        }
        return response;
    }


    //Peliculas


    @Override
    public List<String> listPelicula() {
        List<PostDTOPelicula> response = new ArrayList<>();
        PostDTOPelicula post;
        List<String> imagenTituloyFecha = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getPeliculas().get();

        try {
            for (DocumentSnapshot doc: querySnapshotApiFuture.get().getDocuments()){
                post = doc.toObject(PostDTOPelicula.class);
                post.setId(doc.getId());
                imagenTituloyFecha.add("Imagen: " + post.getImagen()+", Titulo: " +post.getTitulo()+", FechaDeCreacion: " +post.getFechaDeCreacion());
                response.add(post);
            }
        } catch (Exception e) {
            return null;

        }
        return imagenTituloyFecha;
    }

    @Override
    public Object addPelicula(PostDTOPelicula post) {
        if (post.getCalificacion() > 5){
            return Boolean.FALSE;
        }
        if (post.getCalificacion() < 1){
            return Boolean.FALSE;
        }
        Map<String, Object> docData = new HashMap<>();
        docData.put("imagen", post.getImagen());
        docData.put("titulo", post.getTitulo());
        docData.put("getFechaDeCreacion", post.getFechaDeCreacion());
        docData.put("calificacion", post.getCalificacion());
        docData.put("personajesAsociados", post.getPersonajesAsociados());

        CollectionReference Peliculas = getPeliculas();
        ApiFuture<WriteResult> writeResultApiFuture = Peliculas.document().create(docData);

        try {
            if (null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }










    public CollectionReference getPersonajes() {
        return firebase.getFirestor().collection("Personajes");
    }
    public CollectionReference getPeliculas() {
        return firebase.getFirestor().collection("Peliculas");
    }
}
