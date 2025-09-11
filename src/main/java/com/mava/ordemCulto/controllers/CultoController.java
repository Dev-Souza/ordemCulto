package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.dto.CultoRequestDTO;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import com.mava.ordemCulto.domain.cultos.dto.FiltrarCultoPorData;
import com.mava.ordemCulto.services.CultoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CultoEntity> createNewCulto(@Valid @RequestBody CultoRequestDTO cultoDTO) {
        return this.cultoService.create(cultoDTO);
    }

    // GET ALL Pagination
    @GetMapping
    public ResponseEntity<List<CultoResponseDTO>> getAllCultos(@RequestParam int pagina, @RequestParam int itens) {
        return cultoService.getAll(pagina, itens);
    }

    // GET COUNT Cultos
    @GetMapping("/qtd")
    public ResponseEntity<Map<String, Long>> countCultos() {
        long total = cultoService.getCount();
        Map<String, Long> resposta = Map.of("quantidade", total);
        return ResponseEntity.ok(resposta);
    }

    // GET BY ID
    @GetMapping("/{id}") // Use a barra inicial para melhor prática de URL
    public ResponseEntity<CultoResponseDTO> getByIdCulto(@PathVariable("id") Long id) {
        return cultoService.getByIdCulto(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CultoResponseDTO> updateByIdCulto(
            @PathVariable("id") Long id,
            @Valid @RequestBody CultoRequestDTO cultoDTOAtualizado) {
        return cultoService.update(id, cultoDTOAtualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIdCulto(@PathVariable("id") Long id) {
        return cultoService.delete(id);
    }

    // FILTRAGEM POR DATA
    @PostMapping("/filtroData")
    public ResponseEntity<List<CultoEntity>> filtrarCultosPorData(@Valid @RequestBody FiltrarCultoPorData filtro) {return cultoService.getCultoByData(filtro.getDataInicial(), filtro.getDataFinal());}

    //BUSCAR CULTOS RECENTES
    @GetMapping("/cultosRecentes")
    public ResponseEntity<List<CultoEntity>> getCultosRecentes() {return cultoService.getAllCultosRecentes();}

    @GetMapping("/filtroTitulo")
    public ResponseEntity<List<CultoResponseDTO>> filtroTitulo(@RequestParam String titulo){return cultoService.filtroTitulo(titulo);}
}