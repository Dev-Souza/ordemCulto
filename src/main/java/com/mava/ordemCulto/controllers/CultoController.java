package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.cultos.CultoDTO;
import com.mava.ordemCulto.domain.cultos.FiltrarCultoPorData;
import com.mava.ordemCulto.services.CultoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/culto")
@RequiredArgsConstructor
@Validated
public class CultoController {

    private final CultoService cultoService;

    // CREATE
    @PostMapping
    public ResponseEntity<Culto> createNewCulto(@Valid @RequestBody CultoDTO cultoDTO) {
        return this.cultoService.create(cultoDTO);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<CultoDTO>> getAllCultos() {
        return cultoService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}") // Use a barra inicial para melhor prática de URL
    public ResponseEntity<CultoDTO> getByIdCulto(@PathVariable("id") Integer id) {
        return cultoService.getByIdCulto(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CultoDTO> updateByIdCulto(
            @PathVariable("id") Integer id,
            @Valid @RequestBody CultoDTO cultoDTOAtualizado) {
        return cultoService.update(id, cultoDTOAtualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIdCulto(@PathVariable("id") Integer id) {
        return cultoService.delete(id);
    }

    // FILTRAGEM POR DATA
    @PostMapping("/filtro")
    public ResponseEntity<List<Culto>> filtrarCultosPorData(@Valid @RequestBody FiltrarCultoPorData filtro) {
        List<Culto> cultos = cultoService.getCultoByData(filtro.getDataInicial(), filtro.getDataFinal());
        return ResponseEntity.ok(cultos);
    }
}