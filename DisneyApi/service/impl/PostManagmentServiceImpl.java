package com.Alkemy.DisneyApi.service.impl;

import com.Alkemy.DisneyApi.dto.PostDTOGenero;
import com.Alkemy.DisneyApi.dto.PostDTOPelicula;
import com.Alkemy.DisneyApi.dto.PostDTOPersonaje;
import com.Alkemy.DisneyApi.firebase.FirebaseInitializer;
import com.Alkemy.DisneyApi.service.PostManagmentService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class PostManagmentServiceImpl implements PostManagmentService {

    @Autowired
    private FirebaseInitializer firebase;

    //Personajes


    @Override
    public List<? extends Object> listPersonaje(String id, int edad, String nombre) throws ExecutionException, InterruptedException {
        List<PostDTOPersonaje> response = new ArrayList<>();
        PostDTOPersonaje post;
        List<Map> nombresYFotos = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = null;
        if (!id.equals("")){
            CollectionReference personajes = getPersonajes();
            Query query = personajes.whereArrayContains("peliculasAsociadas", id);
            personajes.document(id).get();
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                post = document.toObject(PostDTOPersonaje.class);
                post.setId(document.getId());
                response.add(post);
            }
            return response;
        }
        else if (edad != -1){
            CollectionReference personajes = getPersonajes();
            Query query = personajes.whereEqualTo("edad", edad);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                post = document.toObject(PostDTOPersonaje.class);
                post.setId(document.getId());
                response.add(post);
            }
            return response;
        }
        else if (!nombre.equals("")){
            CollectionReference personajes = getPersonajes();
            Query query = personajes.whereEqualTo("nombre", nombre);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                post = document.toObject(PostDTOPersonaje.class);
                post.setId(document.getId());
                response.add(post);
            }
            return response;
        }
        else{
             querySnapshotApiFuture = getPersonajes().get();
        }

        try {
            for (DocumentSnapshot doc: querySnapshotApiFuture.get().getDocuments()){
                post = doc.toObject(PostDTOPersonaje.class);
                post.setId(doc.getId());
                Map<String, String> a = new HashMap<String, String>();
                a.put("Nombre", post.getNombre());
                a.put("Foto ", post.getImagen());
                nombresYFotos.add(a);
                response.add(post);
            }
        } catch (Exception e) {
            return null;

        }
        return nombresYFotos;
    }

    @Override
    public Object detalleCharacter(String id) throws ExecutionException, InterruptedException {
        PostDTOPersonaje personaje = (PostDTOPersonaje) getPersonajeConId(id);
        personaje.getPeliculasAsociadas();
        List<PostDTOPelicula> peliculas = new ArrayList<>();
        try {
            for (String idPelicula: personaje.getPeliculasAsociadas()){
                peliculas.add((PostDTOPelicula) getPeliculaConId(idPelicula));
            }
        } catch (Exception e) {
            return null;
        }
        return Arrays.asList(personaje, peliculas);
    }

    @Override
    public Boolean addPersonaje(PostDTOPersonaje post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("imagen", post.getImagen());
        docData.put("nombre", post.getNombre());
        docData.put("peso", post.getPeso());
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
    public Boolean editPersonaje(String id, PostDTOPersonaje post) throws ExecutionException, InterruptedException {

        if (!existeONoPersonaje(id)){
            return Boolean.FALSE;
        }

        Map<String, Object> docData = new HashMap<>();
        docData.put("imagen", post.getImagen());
        docData.put("nombre", post.getNombre());
        docData.put("peso", post.getPeso());
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


    public List<? extends Object> listPelicula(String name,String genre,String order) throws ExecutionException, InterruptedException {
        List<PostDTOPelicula> response = new ArrayList<>();
        PostDTOPelicula post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getPeliculas().get();
        if (!genre.equals("")){
            CollectionReference peliculas = getPeliculas();
            Query query = peliculas.whereArrayContains("generosAsociados", genre);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                post = document.toObject(PostDTOPelicula.class);
                post.setId(document.getId());
                response.add(post);
            }
            return response;
        }
        else if (!name.equals("")){
            CollectionReference peliculas = getPeliculas();
            Query query = peliculas.whereEqualTo("titulo", name);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                post = document.toObject(PostDTOPelicula.class);
                post.setId(document.getId());
                response.add(post);
            }
            return response;
        }
        else if (!order.equals("")){
            CollectionReference peliculas = getPeliculas();
            Query query = peliculas.orderBy("fechaDeCreacion", Query.Direction.ASCENDING);
            if (order.equals("DESC")){
                query = peliculas.orderBy("fechaDeCreacion", Query.Direction.DESCENDING);}
            else if (!order.equals("ASC")){
                List<String> temp = new ArrayList<>();
                temp.add("Ingresa un orden valido");
                return temp;
            }
            System.out.println(order);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                post = document.toObject(PostDTOPelicula.class);
                post.setId(document.getId());
                response.add(post);
            }
            return response;
        }
        List<String> imagenTituloyFecha = new ArrayList<>();
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
        docData.put("fechaDeCreacion", post.getFechaDeCreacion());
        docData.put("calificacion", post.getCalificacion());
        docData.put("generosAsociados", post.getGenerosAsociados());
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

    @Override
    public Object editMovie(String id, PostDTOPelicula post) throws ExecutionException, InterruptedException {
        if (post.getCalificacion() > 5){
            return Boolean.FALSE;
        }
        if (post.getCalificacion() < 1){
            return Boolean.FALSE;
        }

        if (!existeONoPelicula(id)){
            return Boolean.FALSE;
        }

        Map<String, Object> docData = new HashMap<>();
        docData.put("imagen", post.getImagen());
        docData.put("titulo", post.getTitulo());
        docData.put("fechaDeCreacion", post.getFechaDeCreacion());
        docData.put("calificacion", post.getCalificacion());
        docData.put("generosAsociados", post.getGenerosAsociados());
        docData.put("personajesAsociados", post.getPersonajesAsociados());
        ApiFuture<WriteResult> writeResultApiFuture = getPeliculas().document(id).set(docData);

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
    public Object deleteMovie(String id) {
        ApiFuture<WriteResult> writeResultApiFuture = getPeliculas().document(id).delete();
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
    public Object detallesMovie(String id) throws ExecutionException, InterruptedException {

        PostDTOPelicula pelicula = (PostDTOPelicula) getPeliculaConId(id);
        List<PostDTOPersonaje> personajes = new ArrayList<>();
        try {
            for (String idPersonaje: pelicula.getPersonajesAsociados()){
                personajes.add((PostDTOPersonaje) getPersonajeConId(idPersonaje));
            }
        } catch (Exception e) {
            return null;
        }
        return Arrays.asList(pelicula, personajes);
    }





    //genero

    @Override
    public Object listGeneros() {
        List<PostDTOGenero> response = new ArrayList<>();
        PostDTOGenero post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getGeneros().get();

        try {
            for (DocumentSnapshot doc: querySnapshotApiFuture.get().getDocuments()){
                post = doc.toObject(PostDTOGenero.class);
                post.setId(doc.getId());
                response.add(post);
            }
        } catch (Exception e) {
            return null;

        }
        return response;
    }




    //FuncionesVarias

    public Boolean existeONoPelicula(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getPeliculas().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public Boolean existeONoPersonaje(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getPersonajes().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public Object getPersonajeConId(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getPersonajes().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            document.toObject(PostDTOPersonaje.class);
        } else {
            System.out.println("No such document!");
        }
        return document.toObject(PostDTOPersonaje.class);
    }

    public Object getPeliculaConId(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getPeliculas().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            document.toObject(PostDTOPelicula.class);
        } else {
            System.out.println("No such document!");
        }
        return document.toObject(PostDTOPelicula.class);
    }

    public CollectionReference getPersonajes() {
        return firebase.getFirestor().collection("Personajes");
    }
    public CollectionReference getGeneros() {
        return firebase.getFirestor().collection("Generos");
    }
    public CollectionReference getPeliculas() {
        return firebase.getFirestor().collection("Peliculas");
    }
}
