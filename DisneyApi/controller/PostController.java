package com.Alkemy.DisneyApi.controller;


import com.Alkemy.DisneyApi.dto.PostDTOPelicula;
import com.Alkemy.DisneyApi.dto.PostDTOPersonaje;
import com.Alkemy.DisneyApi.service.PostManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/")

public class PostController {

    @Autowired
    private PostManagmentService service;

    @GetMapping(value = "/characters")
    public ResponseEntity characters(@RequestParam (value = "idMovie", defaultValue = "") String id,
                                     @RequestParam (value = "age", defaultValue = "-1") int edad,
                                     @RequestParam (value = "name", defaultValue = "") String nombre) throws ExecutionException, InterruptedException {
        return new ResponseEntity(service.listPersonaje(id,edad,nombre), HttpStatus.OK);
    }

    @GetMapping(value = "/fullCharacters")
    public ResponseEntity fullCharacters(){
        return new ResponseEntity(service.listPersonajeFull(), HttpStatus.OK);
    }

    @GetMapping(value = "/detallesCharacter")
    public ResponseEntity detalleCharacter(@RequestParam (value = "id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(service.detalleCharacter(id), HttpStatus.OK);
    }

    @PostMapping(value = "/addCharacter")
    public ResponseEntity addPersonaje(@RequestBody PostDTOPersonaje post){
        return new ResponseEntity(service.addPersonaje(post), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/updateCharacter")
    public ResponseEntity editCharacter(@PathVariable(value = "id") String id, @RequestBody PostDTOPersonaje post) throws ExecutionException, InterruptedException {
        return new ResponseEntity(service.editPersonaje(id, post), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/deleteCharacter")
    public ResponseEntity deleteCharacter(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.deletePersonaje(id), HttpStatus.OK);
    }

    //PELICULAS

    @GetMapping(value = "/movies")
    public ResponseEntity movies(@RequestParam (value = "name", defaultValue = "") String name,
                                 @RequestParam (value = "genre", defaultValue = "") String genre,
                                 @RequestParam (value = "order", defaultValue = "") String order) throws ExecutionException, InterruptedException{
        return new ResponseEntity(service.listPelicula(name,genre,order), HttpStatus.OK);
    }

    @PostMapping(value = "/addMovie")
    public ResponseEntity addPelicula(@RequestBody PostDTOPelicula post){
        return new ResponseEntity(service.addPelicula(post), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/updateMovie")
    public ResponseEntity editMovie(@PathVariable(value = "id") String id, @RequestBody PostDTOPelicula post) throws ExecutionException, InterruptedException {
        return new ResponseEntity(service.editMovie(id, post), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/deleteMovie")
    public ResponseEntity deleteMovie(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.deleteMovie(id), HttpStatus.OK);
    }

    @GetMapping(value = "/detallesMovie")
    public ResponseEntity detallesMovie(@RequestParam (value = "id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(service.detallesMovie(id), HttpStatus.OK);
    }

    @GetMapping(value = "/generos")
    public ResponseEntity detallesGenero() {
        return new ResponseEntity(service.listGeneros(), HttpStatus.OK);
    }


}
