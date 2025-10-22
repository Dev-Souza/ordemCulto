package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.TipoCulto;
import com.mava.ordemCulto.domain.cultos.dto.CultoRequestDTO;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import com.mava.ordemCulto.domain.cultos.dto.FiltrarCultoPorData;
import com.mava.ordemCulto.domain.cultos.dto.QuantidadeCultoDTO;
import com.mava.ordemCulto.services.CultoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/culto")
@RequiredArgsConstructor
@Validated
public class CultoController {

    private final CultoService cultoService;

    // CREATE
    @PostMapping
    public ResponseEntity<CultoEntity> createNewCulto(@Valid @RequestBody CultoRequestDTO cultoDTO) {return this.cultoService.create(cultoDTO);}

    // GET ALL Pagination
    @GetMapping
    public Page<CultoResponseDTO> getAllCultos(@RequestParam String tituloCulto, @RequestParam TipoCulto tipoCulto, @RequestParam LocalDate dataInicio, @RequestParam LocalDate dataFinal, Pageable pageable) {return cultoService.getAll(tituloCulto, tipoCulto, dataInicio, dataFinal, pageable);}

    // GET BY ID
    @GetMapping("/{id}") // Use a barra inicial para melhor prática de URL
    public ResponseEntity<CultoResponseDTO> getByIdCulto(@PathVariable("id") Long id) {return cultoService.getByIdCulto(id);}

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CultoResponseDTO> updateByIdCulto(@PathVariable("id") Long id, @Valid @RequestBody CultoRequestDTO cultoDTOAtualizado) {return cultoService.update(id, cultoDTOAtualizado);}

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIdCulto(@PathVariable("id") Long id) {
        return cultoService.delete(id);
    }
}