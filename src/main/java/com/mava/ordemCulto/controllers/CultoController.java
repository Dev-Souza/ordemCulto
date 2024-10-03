package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.models.CultoModel;
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
    public ResponseEntity<CultoModel> createNewCulto(@Valid @RequestBody CultoModel cultoModel) {
        return cultoService.create(cultoModel);
    }

    @GetMapping
    public ResponseEntity<List<CultoModel>> getAllCultos(){
        return cultoService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<CultoModel> getByIdCulto(@PathVariable("id") Integer id){
        return cultoService.getById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<CultoModel> updateByIdCulto(@PathVariable("id") Integer id, CultoModel cultoAtualizado){
        return cultoService.update(id, cultoAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteByIdCulto(@PathVariable("id") Integer id){
        return cultoService.delete(id);
    }

}
