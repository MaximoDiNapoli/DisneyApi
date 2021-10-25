package com.Alkemy.DisneyApi.controller;


import com.Alkemy.DisneyApi.dto.PostDTOPelicula;
import com.Alkemy.DisneyApi.dto.PostDTOPersonaje;
import com.Alkemy.DisneyApi.service.PostManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")

public class PostController {

    @Autowired
    private PostManagmentService service;

    @GetMapping(value = "/characters")
    public ResponseEntity characters(){
        return new ResponseEntity(service.listPersonaje(), HttpStatus.OK);
    }

    @GetMapping(value = "/fullCharacters")
    public ResponseEntity fullCharacters(){
        return new ResponseEntity(service.listPersonajeFull(), HttpStatus.OK);
    }

    @PostMapping(value = "/addCharacter")
    public ResponseEntity addPersonaje(@RequestBody PostDTOPersonaje post){
        return new ResponseEntity(service.addPersonaje(post), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/updateCharacter")
    public ResponseEntity editCharacter(@PathVariable(value = "id") String id, @RequestBody PostDTOPersonaje post){
        return new ResponseEntity(service.editPersonaje(id, post), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/deleteCharacter")
    public ResponseEntity deleteCharacter(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.deletePersonaje(id), HttpStatus.OK);
    }

    //PELICULAS

    @GetMapping(value = "/movies")
    public ResponseEntity movies(){
        return new ResponseEntity(service.listPelicula(), HttpStatus.OK);
    }

    @PostMapping(value = "/addMovie")
    public ResponseEntity addPelicula(@RequestBody PostDTOPelicula post){
        return new ResponseEntity(service.addPelicula(post), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/updateMovie")
    public ResponseEntity editMovie(@PathVariable(value = "id") String id, @RequestBody PostDTOPelicula post){
        return new ResponseEntity(service.editMovie(id, post), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/deleteMovie")
    public ResponseEntity deleteMovie(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.deleteMovie(id), HttpStatus.OK);
    }

}
