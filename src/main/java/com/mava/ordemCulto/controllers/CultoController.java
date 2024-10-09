package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.services.CultoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/culto")
@RequiredArgsConstructor
@Validated // Aqui o @Validated é aplicado para validar todas as chamadas dentro desta classe
public class CultoController {

    private final CultoService cultoService;

    //CREATE
    @PostMapping
    public ResponseEntity<Culto> createNewCulto(@Valid @RequestBody Culto culto) {
        return cultoService.create(culto);
    }

    //GET ALL
    @GetMapping
    public ResponseEntity<List<Culto>> getAllCultos(){
        return cultoService.getAll();
    }

    //GET BY ID
    @GetMapping("{id}")
    public ResponseEntity<Culto> getByIdCulto(@PathVariable("id") Integer id){
        return cultoService.getById(id);
    }

    //UPDATE
    @PutMapping("{id}")
    public ResponseEntity<Culto> updateByIdCulto(@PathVariable("id") Integer id, @Valid @RequestBody Culto cultoAtualizado) {
        return cultoService.update(id, cultoAtualizado);
    }

    //DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteByIdCulto(@PathVariable("id") Integer id){
        return cultoService.delete(id);
    }
}
