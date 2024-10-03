package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.models.CultoModel;
import com.mava.ordemCulto.repositories.CultoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/culto")
@RequiredArgsConstructor
@Validated // Aqui o @Validated é aplicado para validar todas as chamadas dentro desta classe
public class CultoController {

    private final CultoRepository cultoRepository;

    //CREATE
    @PostMapping
    public ResponseEntity<CultoModel> createNewCulto(@RequestBody CultoModel culto) {
        if (culto.getId() != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("Error", "Culto já existe")
                    .body(null);
        }

        CultoModel newCulto = cultoRepository.save(culto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Created-Culto", "CultoCriadoComSucesso")
                .body(newCulto);
    }
}
